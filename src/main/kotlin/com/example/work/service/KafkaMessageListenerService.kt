package com.example.work.service

import com.example.work.config.logger
import com.google.common.util.concurrent.RateLimiter
import com.sun.management.OperatingSystemMXBean
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.config.KafkaListenerEndpointRegistry
import org.springframework.kafka.support.Acknowledgment
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.lang.management.ManagementFactory
import java.lang.management.ThreadMXBean


@Component
@EnableScheduling
class KafkaMessageListenerService(
    private val endpointRegistry: KafkaListenerEndpointRegistry,
) {

    private val rateLimiter = RateLimiter.create(10.0) // 초당 10개의 메시지 처리

    val log = logger()

    private var listenerActive: Boolean = false // 초기값은 true로 설정

    @KafkaListener(
        topics = ["message"],
        groupId = "myGroup",
        id = "yourListenerId",
        containerFactory = "kafkaListenerContainerFactory",
        autoStartup = "false"
    )
    fun listen(messages: ConsumerRecord<String, String>, acknowledgment: Acknowledgment) {
        rateLimiter.acquire()

        if (listenerActive) {
            logSystemThreadUsage()
            acknowledgment.acknowledge()
        } else {

            println("Listener is currently inactive. Skipping message processing.")
        }
    }

    @Scheduled(cron = "0 50 11 * * *") // 매일 11시 50분에 실행
    fun turnOffListener() {
        synchronized(this) {
            stopListener()
            listenerActive = false
        }
    }

    @Scheduled(cron = "0 30 12 * * *") // 매일 12시 30분에 실행
    fun turnOnListener() {
        synchronized(this) {
            startListener()
            listenerActive = true
        }
    }


    fun stopListener() {
        endpointRegistry.getListenerContainer("yourListenerId")?.stop()
    }

    fun startListener() {
        endpointRegistry.getListenerContainer("yourListenerId")?.start()
    }

    fun getSystemUsage(): String {
        val runtime = Runtime.getRuntime()
        val osBean = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean::class.java)

        val usedMemory = (runtime.totalMemory() - runtime.freeMemory()) / (1024 * 1024)
        val maxMemory = runtime.maxMemory() / (1024 * 1024)
        val cpuLoad = osBean.systemCpuLoad * 100

        return "Used Memory: ${usedMemory}MB, Max Memory: ${maxMemory}MB, CPU Load: ${String.format("%.2f", cpuLoad)}%"
    }

    fun logSystemThreadUsage() {
        val threadMXBean: ThreadMXBean = ManagementFactory.getThreadMXBean()
        val threadCount = threadMXBean.threadCount // 현재 스레드 수
        val peakThreadCount = threadMXBean.peakThreadCount // 실행 중 발생한 최대 스레드 수
        val result = getSystemUsage()
        log.info("Current thread count: $threadCount, Peak thread count: $peakThreadCount $result")
    }
}
package com.example.work.service

import com.example.work.config.logger
import com.example.work.event.EmailSendEvent
import com.google.common.util.concurrent.RateLimiter
import com.sun.management.OperatingSystemMXBean
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.context.ApplicationEventPublisher
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
class EventMessageListenerService(
    private val applicationEventPublisher: ApplicationEventPublisher

) {
    val log = logger()

    fun eventTest() {
        log.info("#### event publish ####")
        applicationEventPublisher.publishEvent(EmailSendEvent("momogom", this))
    }

}
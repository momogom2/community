package com.example.work.controller

import com.example.work.service.KafkaMessageListenerService
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RequestMapping("/kafka")
@RestController
class testController (
    private val kafkaMessageListenerService: KafkaMessageListenerService,
) {

    @GetMapping("")
    fun hello() : Mono<String> {
        return Mono.just("hello")
    }

    @GetMapping("/stop")
    fun stop() {
        kafkaMessageListenerService.turnOffListener()
    }

    @GetMapping("/start")
    fun start() {
        kafkaMessageListenerService.turnOnListener()
    }

    @GetMapping("/send")
    fun send() {
        val list = (1..1000).toList()


        val producer = KafkaProducer<String, String>(sendConfig())

        list.map {
            val record = ProducerRecord("message", "key", it.toString())
            producer.send(record)
        }

        producer.flush()
        producer.close()

    }

    private fun sendConfig(): Map<String, Any> =
        mapOf(
            ProducerConfig.BOOTSTRAP_SERVERS_CONFIG to "127.0.0.1:9092, 127.0.0.1:9093, 127.0.0.1:9094",
            ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG to StringSerializer::class.java,
            ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG to StringSerializer::class.java
        )

}
package com.example.work.config

import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.common.serialization.StringDeserializer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.listener.ContainerProperties


@Configuration
@EnableKafka
class KafkaConfig {

    @Bean
    fun kafkaListenerContainerFactory(): ConcurrentKafkaListenerContainerFactory<String, String> {
        val factory = ConcurrentKafkaListenerContainerFactory<String, String>()

        factory.setConcurrency(1) // Consumer Process Thread Count
        factory.consumerFactory = DefaultKafkaConsumerFactory(getConfig())
        factory.containerProperties.pollTimeout = 1000
        factory.containerProperties.ackMode = ContainerProperties.AckMode.MANUAL
        return factory
    }

    private fun getConfig(): Map<String, Any> =
        mapOf(
            ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG to "127.0.0.1:9092, 127.0.0.1:9093, 127.0.0.1:9094",
            ConsumerConfig.AUTO_OFFSET_RESET_CONFIG to "earliest",    // 마지막 읽은 부분부터 Read
            ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG to false,
            ConsumerConfig.MAX_POLL_RECORDS_CONFIG to "1",
            ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG to StringDeserializer::class.java,
            ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG to StringDeserializer::class.java
        )
}
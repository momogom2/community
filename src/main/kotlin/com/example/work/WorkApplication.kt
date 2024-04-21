package com.example.work

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.messaging.Message
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.function.Function

@SpringBootApplication
class WorkApplication

fun main(args: Array<String>) {
    runApplication<WorkApplication>(*args)
}


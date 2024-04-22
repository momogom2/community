package com.example.work.controller

import com.example.work.service.EventMessageListenerService
import com.example.work.service.KafkaMessageListenerService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/event")
@RestController
class eventController (
    private val eventMessageListenerService: EventMessageListenerService,
) {

    @GetMapping("")
    fun eventTest() {
        eventMessageListenerService.eventTest()
    }

}
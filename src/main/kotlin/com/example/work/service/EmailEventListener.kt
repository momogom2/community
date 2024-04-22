package com.example.work.service

import com.example.work.config.logger
import com.example.work.event.EmailSendEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class EmailEventListener(
    private val sesService: SesService
) {

    val log = logger()


    @EventListener
    fun onEmailSendEventHandler(event: EmailSendEvent) {
        // 이메일 전송
        log.info("#### event listen")
        sesService.sendEmail(event.email)
    }
}
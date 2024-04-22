package com.example.work.service

import com.example.work.config.logger
import org.springframework.stereotype.Service

@Service
class SesService {

    val log = logger()

    fun sendEmail(email: String) {
        log.info("#### event execute")
        println("send Email $email")
    }
}
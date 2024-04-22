package com.example.work.event

import org.springframework.context.ApplicationEvent

class EmailSendEvent(
    val email: String,
    source: Any
) : ApplicationEvent(source)
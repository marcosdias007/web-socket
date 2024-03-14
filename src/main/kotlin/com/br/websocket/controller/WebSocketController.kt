package com.br.websocket.controller

import org.springframework.messaging.handler.annotation.DestinationVariable
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.stereotype.Controller

@Controller
class WebSocketController {

    @MessageMapping("/join/{departamentoId}")
    @SendTo("/topic/departamento/{departamentoId}")
    fun joinDepartamento(@DestinationVariable departamentoId: Long, message: String): String {
        return message
    }
}

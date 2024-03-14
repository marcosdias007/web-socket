package com.br.websocket.service

import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class TerminalService(
    val simpMessagingTemplate: SimpMessagingTemplate,
    val
) {
    private fun enviarAtualizacaoParaTerminais() {
        val inicio = LocalDateTime.now().minusHours(1)
        val limite = LocalDateTime.now().plusMinutes(30)
        val senhasAtualizadas = senhaRepository.retornarSenhasEntre(inicio, limite)
        simpMessagingTemplate.convertAndSend("/topic/atualizarSenhas", senhasAtualizadas)
    }
}
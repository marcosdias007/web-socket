package com.br.websocket.task

import com.br.websocket.dto.SenhaInfo
import com.br.websocket.model.Senha
import com.br.websocket.repositoy.DepartamentoRepository
import com.br.websocket.repositoy.SenhaRepository
import com.br.websocket.service.TerminalService
import com.google.gson.Gson
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class AgendamentoTask(
        val senhaRepository: SenhaRepository,
        val departamentoRepository: DepartamentoRepository,
        val terminalService: TerminalService
) {

    @Scheduled(fixedRate = 30000)
    fun gerarSenhas() {
        val numero = senhaRepository.findAll().count()
        Senha().apply {
            nome = "Senha numero $numero"
            departamento = departamentoRepository.findById(1).orElse(null)
            horario = LocalDateTime.now().plusMinutes(2)
            senhaRepository.save(this)
        }.let { senhaRepository.save(it) }
    }

    @Scheduled(fixedRate = 30000)
    fun alimentarTerminais() {
        terminalService.enviarAtualizacoesParaTerminais();
    }
}
package com.br.websocket.task

import com.br.websocket.dto.SenhaInfo
import com.br.websocket.model.Senha
import com.br.websocket.repositoy.DepartamentoRepository
import com.br.websocket.repositoy.SenhaRepository
import com.google.gson.Gson
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class AgendamentoTask(
    val senhaRepository: SenhaRepository,
    val simpMessagingTemplate: SimpMessagingTemplate,
    val gson: Gson,
    val departamentoRepository: DepartamentoRepository
) {

    @Scheduled(fixedRate = 30000)
    fun alimentarTerminais() {
        val inicio = LocalDateTime.now().minusHours(1)
        val limite = LocalDateTime.now().plusMinutes(30)
        val numero = senhaRepository.findAll().count()

        Senha().apply {
            nome = "Senha numero $numero"
            departamento = departamentoRepository.findById(1).orElse(null)
            horario = LocalDateTime.now().plusMinutes(2)
            senhaRepository.save(this)
        }.let { senhaRepository.save(it) }

        val senhasFuturas = senhaRepository.retornarSenhasEntre(inicio, limite)

        senhasFuturas.groupBy { it.departamento!!.id }.forEach { (departamentoId, senhas) ->
            val senhasInfo = senhas.map { SenhaInfo(it.id!!, it.nome!!) }
            val mensagemJson = gson.toJson(senhasInfo)
            simpMessagingTemplate.convertAndSend("/topic/departamento/$departamentoId", mensagemJson)
        }
    }
}
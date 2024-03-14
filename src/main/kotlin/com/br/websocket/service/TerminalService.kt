package com.br.websocket.service

import com.br.websocket.dto.SenhaInfo
import com.br.websocket.model.Senha
import com.google.gson.Gson
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Service

@Service
class TerminalService(val simpMessagingTemplate: SimpMessagingTemplate, val senhaService: SenhaService, val gson: Gson) {
    fun enviarAtualizacoesParaTerminais() {
        senhaService.buscarTodas().groupBy { it.departamento!!.id }.forEach { (departamentoId, senhas) ->
                    enviarMensagem(departamentoId!!, senhas)
                }
    }
    fun enviarAtualizacaoParaTerminal(departamentoId: Long) {
        val fila = senhaService.buscarTodasPorDepartamento(departamentoId)
        enviarMensagem(departamentoId, fila)
    }

    private fun enviarMensagem(departamentoId: Long, fila: List<Senha>) {
        val mensagemJson = gson.toJson(fila.map { SenhaInfo(it.id!!, it.nome!!) })
        simpMessagingTemplate.convertAndSend("/topic/departamento/$departamentoId", mensagemJson)
    }

}
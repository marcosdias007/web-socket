package com.br.websocket.service

import com.br.websocket.model.Senha
import com.br.websocket.repositoy.SenhaRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class SenhaService(
    val senhaRepository: SenhaRepository
) {

    fun buscarPorId(id: Long): Senha? =
        senhaRepository.findById(id).orElse(null)

    fun buscarOuFalharPorId(id: Long): Senha =
        senhaRepository.findById(id).orElseThrow {
            throw IllegalArgumentException("Senha com id $id não encontrada")
        }

    fun buscarListaDeSenhas(): List<Senha> {
        val inicio = LocalDateTime.now().minusHours(1)
        val limite = LocalDateTime.now().plusMinutes(30)

        return senhaRepository.retornarSenhasEntre(inicio, limite)
    }
}
package com.br.websocket.service

import com.br.websocket.model.Senha
import com.br.websocket.repositoy.SenhaRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class SenhaService(val senhaRepository: SenhaRepository) {
    fun buscarPorId(id: Long): Senha? = senhaRepository.findById(id).orElse(null)

    fun buscarOuFalharPorId(id: Long): Senha = senhaRepository.findById(id).orElseThrow {
        throw IllegalArgumentException("Senha com id $id não encontrada")
    }

    fun buscarTodas(): List<Senha> {
        val inicio = LocalDateTime.now().minusHours(1)
        val limite = LocalDateTime.now().plusMinutes(30)

        return senhaRepository.retornarTodasEntre(inicio, limite)
    }

    fun buscarTodasPorDepartamento(departamentoId: Long): List<Senha> {
        val inicio = LocalDateTime.now().minusHours(1)
        val limite = LocalDateTime.now().plusMinutes(30)

        return senhaRepository.retornarPorDepartamentoEntre(inicio, limite, departamentoId)
    }

    fun buscarProximaSenhaPorDepartamento(departamentoId: Long): Senha = buscarTodasPorDepartamento(departamentoId).first()

    @Transactional
    fun save(senha: Senha) {
        senhaRepository.save(senha)
    }
}
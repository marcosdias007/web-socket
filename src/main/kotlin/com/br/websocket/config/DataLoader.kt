package com.br.websocket.config

import com.br.websocket.model.Departamento
import com.br.websocket.model.Usuario
import com.br.websocket.repositoy.DepartamentoRepository
import com.br.websocket.repositoy.UsuarioRepository
import jakarta.annotation.PostConstruct
import org.springframework.stereotype.Component

@Component
class DataLoader(
    val departamentoRepository: DepartamentoRepository, val usuarioRepository: UsuarioRepository
) {
    @PostConstruct
    fun loadData() {
        departamentoRepository.saveAll(
            listOf(
                Departamento().apply {
                    nome = "TI"
                    sigla = "TI"
                },
                Departamento().apply {
                    nome = "Recursos Humanos"
                    sigla = "RH"
                },
                Departamento().apply {
                    nome = "Financeiro"
                    sigla = "FIN"
                },
                Departamento().apply {
                    nome = "Comercial"
                    sigla = "COM"
                },
            )
        )

        usuarioRepository.saveAll(
            listOf(
                Usuario().apply {
                    nome = "João"
                    departamento = departamentoRepository.findById(1).get()
                },

                Usuario().apply {
                    nome = "Maria"
                    departamento = departamentoRepository.findById(2).get()
                },

                Usuario().apply {
                    nome = "Marcos"
                    departamento = departamentoRepository.findById(1).get()
                },
            )
        )


    }
}
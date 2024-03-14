package com.br.websocket.repositoy

import com.br.websocket.model.Senha
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.time.LocalDateTime

interface SenhaRepository : JpaRepository<Senha, Long> {
    @Query("SELECT s FROM Senha s WHERE s.horario BETWEEN :inicio AND :agora and s.chamada = false order by s.id")
    fun retornarTodasEntre(@Param("inicio") inicio: LocalDateTime, @Param("agora") agora: LocalDateTime): List<Senha>


    @Query("SELECT s FROM Senha s WHERE s.horario BETWEEN :inicio AND :agora AND s.chamada = false AND s.departamento.id = :departamentoId order by s.id")
    fun retornarPorDepartamentoEntre(@Param("inicio") inicio: LocalDateTime, @Param("agora") agora: LocalDateTime, @Param("departamentoId") departamentoId: Long): List<Senha>
}


package com.br.websocket.repositoy

import com.br.websocket.model.Departamento
import org.springframework.data.jpa.repository.JpaRepository

interface DepartamentoRepository : JpaRepository<Departamento, Long> {
}
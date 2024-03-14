package com.br.websocket.model

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
class Senha {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    val id: Long? = null

    @ManyToOne
    var departamento: Departamento? = null

    var nome: String? = null

    var chamada = false

    var horario: LocalDateTime = LocalDateTime.now()
}
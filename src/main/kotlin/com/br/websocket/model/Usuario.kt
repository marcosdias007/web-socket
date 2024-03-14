package com.br.websocket.model

import jakarta.persistence.*

@Entity
class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    val id: Long? = null

    var nome: String = ""

    @ManyToOne
    var departamento: Departamento? = null

}
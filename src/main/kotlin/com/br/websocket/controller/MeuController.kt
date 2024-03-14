package com.br.websocket.controller

import com.br.websocket.repositoy.UsuarioRepository
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping

@Controller
class WebSocketStart(
    val usuarioRepository: UsuarioRepository
) {
    @GetMapping("/")
    fun index(model: Model): String {
        val usuarios = usuarioRepository.findAll()
        model.addAttribute("usuarios", usuarios)
        return "index"
    }

    @PostMapping("/terminal/{id}")
    fun terminal(@PathVariable id: Long, model: Model): String {
        val usuario = usuarioRepository.findById(id).orElse(null)
        model.addAttribute("usuario", usuario)
        return "terminal"
    }
}

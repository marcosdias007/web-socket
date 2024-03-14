package com.br.websocket.controller

import com.br.websocket.model.Senha
import com.br.websocket.repositoy.DepartamentoRepository
import com.br.websocket.repositoy.SenhaRepository
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import java.time.LocalDateTime

@Controller
class SenhaController(
    val senhaRepository: SenhaRepository,
    val departamentoRepository: DepartamentoRepository,
    val simpMessagingTemplate: SimpMessagingTemplate
) {

    @GetMapping("/cadastrar-senha")
    fun mostrarFormularioDeSenha(model: Model): String {
        model.addAttribute("senha", Senha())
        model.addAttribute("departamentos", departamentoRepository.findAll())
        return "cadastrar-senha"
    }

    @PostMapping("/cadastrar-senha")
    fun cadastrarSenha(@ModelAttribute senha: Senha): String {
        senhaRepository.save(senha)
        return "redirect:/cadastrar-senha"
    }

    @PostMapping("/chamar-proxima-senha")
    fun chamarProximaSenha(model: Model): String {
        val inicio = LocalDateTime.now().minusHours(1)
        val limite = LocalDateTime.now().plusMinutes(30)

        val proximaSenha = senhaRepository.retornarSenhasEntre(inicio, limite).firstOrNull()
        proximaSenha?.let {
            it.chamada = true
            senhaRepository.save(it)

            model.addAttribute("senha", proximaSenha)
        }
        return "redirect:/atendimento"
    }
}

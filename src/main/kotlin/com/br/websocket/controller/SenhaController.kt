package com.br.websocket.controller

import com.br.websocket.model.Senha
import com.br.websocket.repositoy.DepartamentoRepository
import com.br.websocket.repositoy.SenhaRepository
import com.br.websocket.service.SenhaService
import com.br.websocket.service.TerminalService
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import java.time.LocalDateTime

@Controller
class SenhaController(val senhaService: SenhaService, val departamentoRepository: DepartamentoRepository, val terminalService: TerminalService) {

    @GetMapping("/cadastrar-senha")
    fun mostrarFormularioDeSenha(model: Model): String {
        model.addAttribute("senha", Senha())
        model.addAttribute("departamentos", departamentoRepository.findAll())
        return "cadastrar-senha"
    }

    @PostMapping("/cadastrar-senha")
    fun cadastrarSenha(@ModelAttribute senha: Senha): String {
        senhaService.save(senha)
        return "redirect:/cadastrar-senha"
    }

    @PostMapping("/chamar-proxima-senha/{departamentoId}")
    fun chamarProximaSenha(@PathVariable departamentoId: Long, model: Model): String {
        val proximaSenha = senhaService.buscarProximaSenhaPorDepartamento(departamentoId)
        proximaSenha.let {
            it.chamada = true
            senhaService.save(it)
            terminalService.enviarAtualizacaoParaTerminal(departamentoId)
            model.addAttribute("senha", proximaSenha)
        }
        return "atendimento"
    }

}

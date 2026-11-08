package com.auth.controller;

import com.auth.dtos.request.LoginUsuarioRequest;
import com.auth.dtos.request.UsuarioRequest;
import com.auth.dtos.response.TokenResponse;
import com.auth.dtos.response.UsuarioResponse;
import com.auth.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {
    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/cadastro")
    public UsuarioResponse cadastrarUsuario(@Valid @RequestBody UsuarioRequest usuarioRequest){
        return usuarioService.cadsatrarUsuario(usuarioRequest);
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> loginUsuario(@Valid @RequestBody LoginUsuarioRequest loginUsuarioRequest) {
       String token =  usuarioService.loginUsuario(loginUsuarioRequest);

       return ResponseEntity.ok(new TokenResponse(token));
    }

    @GetMapping("/listar-todos")
    public Page<UsuarioResponse> listarTodosUsuarios(@PageableDefault(size = 10) Pageable pageable) {
        return usuarioService.listarTodosUsuarios(pageable);
    }

    @PutMapping("/atualizar-usuario/{id}")
    public UsuarioResponse atualizarUsuario(@Valid @RequestBody UsuarioRequest usuarioRequest, @PathVariable Long id) {
        return usuarioService.atualizarUsuario(usuarioRequest, id);
    }

    @DeleteMapping("/deletar-usuario/{id}")
    public void deletarUsuario(@PathVariable Long id) {
        usuarioService.deletarUsuario(id);
    }

    @GetMapping("/usuarios-cadastrados")
    public Integer usuariosCadastrados() {
        return usuarioService.contarUsuariosCadastrados();
    }
}

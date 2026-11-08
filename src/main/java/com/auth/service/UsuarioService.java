package com.auth.service;

import com.auth.dtos.request.LoginUsuarioRequest;
import com.auth.dtos.request.UsuarioRequest;
import com.auth.dtos.response.UsuarioResponse;
import com.auth.entity.UsuarioEntity;
import com.auth.enums.RoleEnum;
import com.auth.exceptions.UsuarioException;
import com.auth.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public UsuarioService(UsuarioRepository usuarioRepository,
                          PasswordEncoder passwordEncoder,
                         @Qualifier("usuarioAuthenticationManager") AuthenticationManager authenticationManager,
                          JwtService jwtService) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    private UsuarioResponse transformResponse(UsuarioEntity usuarioEntity) {

        return new UsuarioResponse(
                usuarioEntity.getId(),
                usuarioEntity.getNome(),
                usuarioEntity.getEmail(),
                usuarioEntity.getRole()
        );
    }

    @Transactional
    public UsuarioResponse cadsatrarUsuario(UsuarioRequest usuarioRequest) {
        boolean usuarioEncontrado = usuarioRepository.existsByEmail(usuarioRequest.email());

        if (usuarioEncontrado) {
            throw new UsuarioException("Usuario já cadastrado no banco de dados");
        }

        UsuarioEntity usuario = new UsuarioEntity();

        usuario.setNome(usuarioRequest.nome());
        usuario.setSenha(passwordEncoder.encode(usuarioRequest.senha()));
        usuario.setEmail(usuarioRequest.email());
        usuario.setRole(RoleEnum.USUARIO);

        UsuarioEntity usuarioCadastrado = usuarioRepository.save(usuario);
        return transformResponse(usuarioCadastrado);
    }

    public String loginUsuario(LoginUsuarioRequest loginUsuarioRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginUsuarioRequest.email(),
                loginUsuarioRequest.senha()
        ));

        UsuarioEntity usuario = (UsuarioEntity) authentication.getPrincipal();
        
        return jwtService.gerarToken(usuario);

    }

    public Page<UsuarioResponse> listarTodosUsuarios(Pageable pageable){
        Page<UsuarioEntity> listaUsuario = usuarioRepository.listarUsuarios(pageable);

        if (listaUsuario.isEmpty()) {
            throw new UsuarioException("lista de usuarios está vazia");
        }

        return listaUsuario.map(this::transformResponse);
    }

    @Transactional
    public UsuarioResponse atualizarUsuario(UsuarioRequest usuarioRequest, Long id) {
        UsuarioEntity usuarioEncontrado = usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioException("Usuario não encontrado"));

        usuarioEncontrado.setNome(usuarioRequest.nome());
        usuarioEncontrado.setEmail(usuarioRequest.email());
        usuarioEncontrado.setSenha(passwordEncoder.encode(usuarioRequest.senha()));

        UsuarioEntity usuarioAtualizado = usuarioRepository.save(usuarioEncontrado);
        return transformResponse(usuarioAtualizado);
    }

    @Transactional
    public void deletarUsuario(Long id) {
        UsuarioEntity usuarioEcontrado = usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioException("Usuario não encontrado"));

        usuarioRepository.delete(usuarioEcontrado);
    }

    public Integer contarUsuariosCadastrados() {
       Integer usuariosCadastrados = usuarioRepository.contarUsuario();

       if (usuariosCadastrados <= 0) {
           throw new UsuarioException("Nenhum usuario cadastrado");
       }

       return usuariosCadastrados;
    }
}

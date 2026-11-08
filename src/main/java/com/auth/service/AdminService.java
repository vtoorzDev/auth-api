package com.auth.service;

import com.auth.dtos.request.AdminRequest;
import com.auth.dtos.request.LoginAdminRequest;
import com.auth.dtos.response.AdminResponse;
import com.auth.entity.AdminEntity;
import com.auth.enums.RoleEnum;
import com.auth.exceptions.AdminException;
import com.auth.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdminService {
    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AdminService(AdminRepository adminRepository,
                        PasswordEncoder passwordEncoder,
                        @Qualifier("adminAuthenticationManager") AuthenticationManager authenticationManager,
                        JwtService jwtService) {
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;

    }

    private AdminResponse transformResponse(AdminEntity adminEntity) {
        return new AdminResponse(
                adminEntity.getId(),
                adminEntity.getNome(),
                adminEntity.getEmail(),
                adminEntity.getRole()
        );
    }

    public AdminResponse registrarAdmin(AdminRequest adminRequest) {
        boolean adminEcontrado = adminRepository.existsByEmail(adminRequest.email());

        if (adminEcontrado) {
            throw new AdminException("Admin já registrado no sistema");
        }

        AdminEntity admin = new AdminEntity();

        admin.setNome(adminRequest.nome());
        admin.setEmail(adminRequest.email());
        admin.setSenha(passwordEncoder.encode(adminRequest.senha()));
        admin.setRole(RoleEnum.ADMIN);

        AdminEntity adminRegistrado = adminRepository.save(admin);
        return transformResponse(adminRegistrado);
    }

    public String loginAdmin(LoginAdminRequest loginAdminRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginAdminRequest.email(),
                loginAdminRequest.senha()
        ));

        AdminEntity admin = (AdminEntity) authentication.getPrincipal();

        return jwtService.gerarTokenAdmin(admin);
    }
}

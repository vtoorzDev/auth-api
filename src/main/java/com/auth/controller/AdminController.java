package com.auth.controller;

import com.auth.dtos.request.AdminRequest;
import com.auth.dtos.request.LoginAdminRequest;
import com.auth.dtos.response.AdminResponse;
import com.auth.dtos.response.TokenResponse;
import com.auth.service.AdminService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/cadastro")
    public AdminResponse registrar(@Valid @RequestBody AdminRequest adminRequest) {
        return adminService.registrarAdmin(adminRequest);
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> loginAdmin(@Valid @RequestBody LoginAdminRequest loginAdminRequest) {
        String token = adminService.loginAdmin(loginAdminRequest);

        return ResponseEntity.ok(new TokenResponse(token));
    }
}

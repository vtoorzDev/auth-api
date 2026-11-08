package com.auth.service;

import com.auth.entity.AdminEntity;
import com.auth.entity.UsuarioEntity;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;

import java.time.Instant;

@Service
public class JwtService {
    private final JwtEncoder jwtEncoder;

    public JwtService(JwtEncoder jwtEncoder) {
        this.jwtEncoder = jwtEncoder;
    }

    public String gerarToken(UsuarioEntity usuario) {
        Instant agora = Instant.now();

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("auth")
                .subject(usuario.getEmail())
                .claim("role", usuario.getRole().name())
                .issuedAt(agora)
                .expiresAt(agora.plusSeconds(7200))
                .build();

        return jwtEncoder.encode(
                JwtEncoderParameters.from(JwsHeader.with(MacAlgorithm.HS256).build(), claims
                )
        ).getTokenValue();
    }

    public String gerarTokenAdmin(AdminEntity admin) {
        Instant agora = Instant.now();

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("auth")
                .subject(admin.getEmail())
                .claim("role", admin.getRole().name())
                .issuedAt(agora)
                .expiresAt(agora.plusSeconds(7200))
                .build();

        return jwtEncoder.encode(
                JwtEncoderParameters.from(JwsHeader.with(MacAlgorithm.HS256).build(), claims)
        ).getTokenValue();
    }
}

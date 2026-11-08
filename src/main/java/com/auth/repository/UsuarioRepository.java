package com.auth.repository;

import com.auth.entity.UsuarioEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long> {

    // query para verificar se existe o email
    @Query(value = "SELECT EXISTS(SELECT 1 FROM usuario WHERE email = :email)", nativeQuery = true)
    boolean existsByEmail(@Param("email")String email);

    // query para listar usuarios por ordem alfabetica
    @Query(value = "SELECT * FROM usuario ORDER BY nome ASC", nativeQuery = true)
    Page<UsuarioEntity> listarUsuarios(Pageable pageable);

    @Query(value = "SELECT COUNT(*) FROM usuario", nativeQuery = true)
    Integer contarUsuario();

    Optional<UsuarioEntity> findByEmail(String email);
}

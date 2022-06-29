package com.cvg.cya.postulacion.models.repository;

import com.cvg.cya.postulacion.models.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    boolean existsByRolName(String rolName);
    Optional<Role> findByRolName(String rolName);
}

package com.cvg.cya.postulacion.models.repository;

import com.cvg.cya.postulacion.models.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    boolean existsByRolName(String rolName);
}

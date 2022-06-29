package com.cvg.cya.postulacion.service;

import com.cvg.cya.postulacion.models.entity.Role;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RoleService {
    Role save(Role save);
    Role update(Long id, Role save);
    Optional<Role> findById(Long id);
    List<Role> findAll();
    void deleteById(Long id);
    boolean existsByRolName(String rolName);
    Optional<Role> findByRolName(String rolName);


    void deleteAllReferences(Long rolId);
}

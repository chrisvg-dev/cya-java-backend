package com.cvg.cya.postulacion.service;

import com.cvg.cya.postulacion.models.entity.Role;
import com.cvg.cya.postulacion.models.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository repository;

    public RoleServiceImpl(RoleRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public Role save(Role role) {
        return this.repository.save(role);
    }

    @Override
    @Transactional
    public Role update(Long id, Role role) {
        return null;
    }

    @Override
    @Transactional
    public Optional<Role> findById(Long id) {
        return this.repository.findById(id);
    }

    @Override
    @Transactional
    public List<Role> findAll() {
        return this.repository.findAll();
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        this.repository.deleteById(id);
    }

    @Override
    public boolean existsByRolName(String rolName) {
        return this.repository.existsByRolName(rolName);
    }
}

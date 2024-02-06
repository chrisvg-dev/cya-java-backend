package com.cvg.cya.postulacion.service;

import com.cvg.cya.postulacion.models.entity.UserMenu;
import com.cvg.cya.postulacion.models.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class MenuServiceImpl implements MenuService {

    private final MenuRepository repository;

    @Override
    public UserMenu save(UserMenu menu) {
        return this.repository.save(menu);
    }

    @Override
    public UserMenu update(Long id, UserMenu menu) {
        return null;
    }

    @Override
    public Optional<UserMenu> findById(Long id) {
        return this.repository.findById(id);
    }

    @Override
    public List<UserMenu> findAll() {
        return this.repository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        this.deleteById(id);
    }

    @Override
    public boolean existsByNameOrPath(String name, String path) {
        return this.repository.existsByNameOrPath(name, path);
    }
}

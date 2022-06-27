package com.cvg.cya.postulacion.service;

import com.cvg.cya.postulacion.models.entity.UserMenu;
import com.cvg.cya.postulacion.models.repository.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class MenuServiceImpl implements MenuService {

    private final MenuRepository repository;

    public MenuServiceImpl(MenuRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public UserMenu save(UserMenu menu) {
        return this.repository.save(menu);
    }

    @Override
    @Transactional
    public UserMenu update(Long id, UserMenu menu) {
        return null;
    }

    @Override
    @Transactional
    public Optional<UserMenu> findById(Long id) {
        return this.repository.findById(id);
    }

    @Override
    @Transactional
    public List<UserMenu> findAll() {
        return this.repository.findAll();
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        this.deleteById(id);
    }
}

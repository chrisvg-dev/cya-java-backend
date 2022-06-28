package com.cvg.cya.postulacion.service;

import com.cvg.cya.postulacion.models.entity.Role;
import com.cvg.cya.postulacion.models.entity.UserMenu;

import java.util.List;
import java.util.Optional;

public interface MenuService {
    UserMenu save(UserMenu  menu);
    UserMenu update(Long id, UserMenu menu);
    Optional<UserMenu> findById(Long id);
    List<UserMenu> findAll();
    void deleteById(Long id);




    boolean existsByNameOrPath(String name, String path);
}

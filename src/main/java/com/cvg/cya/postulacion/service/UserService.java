package com.cvg.cya.postulacion.service;

import com.cvg.cya.postulacion.models.dto.UserDto;
import com.cvg.cya.postulacion.models.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User save(User save);
    User update(Long id, UserDto save);
    Optional<User> findById(Long id);
    Optional<User> findByEmail(String email);
    List<User> findAll();
    void deleteById(Long id);

    /**
     *  VALIDATIONS
     */
    boolean existsById(Long id);
    boolean existsByEmail(String email);
}

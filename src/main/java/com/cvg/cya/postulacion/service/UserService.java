package com.cvg.cya.postulacion.service;

import com.cvg.cya.postulacion.models.dto.UserDto;
import com.cvg.cya.postulacion.models.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;

public interface UserService extends UserDetailsService {
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


    boolean existsByEmailAndPassword(String email, String password);
    Optional<User> findByEmailAndPassword(String email, String password);
}

package com.cvg.cya.postulacion.service;

import com.cvg.cya.postulacion.models.dto.UserDto;
import com.cvg.cya.postulacion.models.entity.Users;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;

public interface UserService extends UserDetailsService {
    Users save(Users save);
    Users update(Long id, UserDto save);
    Optional<Users> findById(Long id);
    Optional<Users> findByEmail(String email);
    List<Users> findAll();
    void deleteById(Long id);

    /**
     *  VALIDATIONS
     */
    boolean existsById(Long id);
    boolean existsByEmail(String email);


    boolean existsByEmailAndPassword(String email, String password);
    Optional<Users> findByEmailAndPassword(String email, String password);
}

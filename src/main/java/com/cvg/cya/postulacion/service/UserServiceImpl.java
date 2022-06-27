package com.cvg.cya.postulacion.service;

import com.cvg.cya.postulacion.models.dto.UserDto;
import com.cvg.cya.postulacion.models.entity.User;
import com.cvg.cya.postulacion.models.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    /**
     * DEPENDENCY INYECTION
     */
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<User> findById(Long id) {
        return this.repository.findById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<User> findByEmail(String email) {
        return this.repository.findByEmail(email);
    }

    @Transactional(readOnly = true)
    @Override
    public List<User> findAll() {
        return this.repository.findAll();
    }
    @Transactional
    @Override
    public User save(User user) {
        return this.repository.save(user);
    }
    @Transactional
    @Override
    public User update(Long id, UserDto save) {
        return null;
    }
    @Transactional
    @Override
    public void deleteById(Long id) {
        this.repository.deleteById(id);
    }
    @Transactional(readOnly = true)
    @Override
    public boolean existsById(Long id) {
        return this.repository.existsById(id);
    }
    @Transactional(readOnly = true)
    @Override
    public boolean existsByEmail(String email) {
        return this.repository.existsByEmail(email);
    }
}

package com.cvg.cya.postulacion.service;

import com.cvg.cya.postulacion.models.dto.UserDto;
import com.cvg.cya.postulacion.models.entity.Role;
import com.cvg.cya.postulacion.models.entity.Users;
import com.cvg.cya.postulacion.models.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public Optional<Users> findById(Long id) {
        return this.repository.findById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Users> findByEmail(String email) {
        return this.repository.findByEmail(email);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Users> findAll() {
        return this.repository.findAll();
    }
    @Transactional
    @Override
    public Users save(Users user) {
        return this.repository.save(user);
    }
    @Transactional
    @Override
    public Users update(Long id, UserDto save) {
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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Users> userOptional = this.repository.findByEmail(username);
        if (!userOptional.isPresent()) throw new UsernameNotFoundException("No se encontro al usuario solicitado");

        Users users = userOptional.orElseThrow();

        return new User( users.getName(), users.getPassword(), getRolesFromObject(users.getRoles()) );
    }

    private Collection<? extends GrantedAuthority> getRolesFromObject(Collection<Role> roles){
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getRolName())).collect(Collectors.toList());
    }


    /**
     * VALIDACION DE LOGIN
     */
    @Override
    public boolean existsByEmailAndPassword(String email, String password) {
        return this.repository.existsByEmailAndPassword(email, password);
    }
    @Override
    public Optional<Users> findByEmailAndPassword(String email, String password) {
        return this.repository.findByEmailAndPassword(email, password);
    }
}

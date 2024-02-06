package com.cvg.cya.postulacion.service;

import com.cvg.cya.postulacion.models.UserPrincipal;
import com.cvg.cya.postulacion.models.dto.UserDto;
import com.cvg.cya.postulacion.models.entity.Role;
import com.cvg.cya.postulacion.models.entity.User;
import com.cvg.cya.postulacion.models.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {
    private static final Logger LOG = LoggerFactory.getLogger( UserServiceImpl.class );
    /**
     * DEPENDENCY INJECTION
     */
    private final UserRepository repository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional = this.repository.findByEmail(username);
        if (!userOptional.isPresent()) throw new UsernameNotFoundException("No se encontro al usuario solicitado");

        User user = userOptional.orElseThrow();
        Long roleId = user.getRoles().stream().findFirst().orElseThrow().getId();
        Optional<Role> roleOptional = this.roleService.findById( roleId );

        if (!roleOptional.isPresent()) throw new UsernameNotFoundException("No se encontro el rol");
        Role role = roleOptional.orElseThrow();

        LOG.info( user.getRoles().toString() );
        LOG.info( getRolesFromObject( user.getRoles() ).toString() );

        UserPrincipal userPrincipal = new UserPrincipal(
                getRolesFromObject(user.getRoles()),
                role.getMenu(),
                user.getName(),
                user.getLastName(),
                user.getEmail(),
                user.getPassword(),
                user.getSafetyWord());
        LOG.info( userPrincipal.getAuthorities().toString() );
        return userPrincipal;
    }

    private Collection<? extends GrantedAuthority> getRolesFromObject(Collection<Role> roles){
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_".concat(role.getRolName())))
                .collect(Collectors.toSet());
    }


    /**
     * VALIDACION DE LOGIN
     */
    @Override
    public boolean existsByEmailAndPassword(String email, String password) {
        return this.repository.existsByEmailAndPassword(email, password);
    }
    @Override
    public Optional<User> findByEmailAndPassword(String email, String password) {
        return this.repository.findByEmailAndPassword(email, password);
    }
}

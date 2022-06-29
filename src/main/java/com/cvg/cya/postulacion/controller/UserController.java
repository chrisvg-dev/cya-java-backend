package com.cvg.cya.postulacion.controller;

import com.cvg.cya.postulacion.models.dto.UserDto;
import com.cvg.cya.postulacion.models.entity.Role;
import com.cvg.cya.postulacion.models.entity.User;
import com.cvg.cya.postulacion.service.RoleService;
import com.cvg.cya.postulacion.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {
    private static final Logger LOG = LoggerFactory.getLogger( UserController.class );
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final RoleService roleService;

    public UserController(UserService userService, RoleService roleService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     *
     * GET
     */
    @GetMapping
    public ResponseEntity<?> findAll(){
        return ResponseEntity.ok( this.userService.findAll() );
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        Optional<User> userOptional = this.userService.findById(id);
        try {
            if (userOptional.isPresent()) {
                User user = userOptional.orElseThrow();

                if (user.getId() == 1L) return ResponseEntity.badRequest().body(
                        Collections.singletonMap("message", "No puede eliminar a este usuario.")
                );

                this.userService.deleteById(user.getId());
                return ResponseEntity.ok().body(
                        Collections.singletonMap("message", "Usuario eliminado...")
                );
            }
            return ResponseEntity.badRequest().body(
                    Collections.singletonMap("message", "El usuario no ha sido encontrado.")
            );
        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest().body(
                    Collections.singletonMap("message", "El usuario no ha sido encontrado.")
            );
        }
    }
    /**
     *
     * POST
     *
     */
    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody UserDto dto, BindingResult result) {
        LOG.info( dto.toString() );

        if (result.hasErrors()) return Resources.validate(result);

        boolean existsByEmail = this.userService.existsByEmail( dto.getEmail() );
        if ( existsByEmail ) return ResponseEntity.badRequest().body(
                Collections.singletonMap("message", "El email ya esta registrado...")
        );
        if ( dto.getRoles().isEmpty() || dto.getRoles().stream().anyMatch( item -> item == 0 )) return ResponseEntity.badRequest().body(
                Collections.singletonMap("message", "Debe agregar roles al usuario...")
        );

        Set<Role> roles = dto.getRoles().stream()
                .map( item -> this.roleService.findById(item).orElseThrow() )
                .collect(Collectors.toSet());

        return getPreparedUser(dto, roles);
    }
    @PostMapping("/basic")
    public ResponseEntity<?> saveBasic(@Valid @RequestBody UserDto dto, BindingResult result) {
        LOG.info( dto.toString() );

        if (result.hasErrors()) return Resources.validate(result);

        boolean existsByEmail = this.userService.existsByEmail( dto.getEmail() );
        if ( existsByEmail ) return ResponseEntity.badRequest().body(
                Collections.singletonMap("message", "El email ya esta registrado...")
        );

        boolean existsByRolName = this.roleService.existsByRolName("BASIC");
        LOG.info( existsByRolName + "" );
        if ( !existsByRolName ) return ResponseEntity.badRequest().body(
                Collections.singletonMap("message", "Ha ocurrido un problema al intentar registrar. Intenta nuevamente.")
        );

        Optional<Role> roleOptional = this.roleService.findByRolName("BASIC");
        Set<Role> roles = new HashSet<>();
        roles.add( roleOptional.orElseThrow() );

        return getPreparedUser(dto, roles);
    }

    private ResponseEntity<?> getPreparedUser(@RequestBody @Valid UserDto dto, Set<Role> roles) {
        User user = new User();
        user.setRoles( roles );
        user.setName( dto.getName() );
        user.setLastName( dto.getLastName() );
        user.setEmail( dto.getEmail() );
        user.setPassword( this.passwordEncoder.encode( dto.getPassword() ) );
        user.setSafetyWord( dto.getSafetyWord() );
        user.setCreatedAt( LocalDateTime.now() );

        return ResponseEntity.status(HttpStatus.CREATED).body( this.userService.save(user) );
    }

}

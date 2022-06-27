package com.cvg.cya.postulacion.controller;

import com.cvg.cya.postulacion.models.dto.ResponseDto;
import com.cvg.cya.postulacion.models.dto.UserDto;
import com.cvg.cya.postulacion.models.entity.Role;
import com.cvg.cya.postulacion.models.entity.User;
import com.cvg.cya.postulacion.models.entity.UserMenu;
import com.cvg.cya.postulacion.service.MenuService;
import com.cvg.cya.postulacion.service.RoleService;
import com.cvg.cya.postulacion.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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
    /**
     *
     * POST
     *
     */
    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody UserDto dto, BindingResult result) {

        if (result.hasErrors()) return Resources.validate(result);

        boolean existsByEmail = this.userService.existsByEmail( dto.getEmail() );
        if ( existsByEmail ) return ResponseEntity.badRequest().body(
                Collections.singletonMap("message", "El email ya esta registrado...")
        );
        Optional<Role> roleOptional = this.roleService.findById(dto.getRol());
        if (!roleOptional.isPresent()) return ResponseEntity.badRequest().body(
                Collections.singletonMap("message", "El rol no existe, intenta con otro")
        );

        User user = new User();
        user.setRoles( roleOptional.orElseThrow() );
        user.setName( dto.getName() );
        user.setLastName( dto.getLastName() );
        user.setEmail( dto.getEmail() );
        user.setPassword( this.passwordEncoder.encode( dto.getPassword() ) );
        user.setCreatedAt( LocalDateTime.now() );

        return ResponseEntity.status(HttpStatus.CREATED).body( this.userService.save(user) );
    }
}

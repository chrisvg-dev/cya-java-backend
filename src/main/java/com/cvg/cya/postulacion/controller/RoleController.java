package com.cvg.cya.postulacion.controller;

import com.cvg.cya.postulacion.models.dto.RoleDto;
import com.cvg.cya.postulacion.models.entity.Role;
import com.cvg.cya.postulacion.models.entity.User;
import com.cvg.cya.postulacion.models.entity.UserMenu;
import com.cvg.cya.postulacion.models.repository.UserRepository;
import com.cvg.cya.postulacion.service.MenuService;
import com.cvg.cya.postulacion.service.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/roles")
@CrossOrigin(origins = "*")
public class RoleController {
    private static final Logger LOG = LoggerFactory.getLogger( RoleController.class );
    public static final String MESSAGE = "message";
    private final RoleService roleService;
    private final MenuService menuService;
    private final UserRepository userRepository;

    public RoleController(RoleService roleService, MenuService menuService, UserRepository userRepository) {
        this.roleService = roleService;
        this.menuService = menuService;
        this.userRepository = userRepository;
    }

    @GetMapping
    public ResponseEntity<List<Role>> findAll(){
        return ResponseEntity.ok(this.roleService.findAll());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        LOG.info( "Id recibido: " + id);
        Optional<Role> roleOptional = this.roleService.findById(id);
        try {
            if (roleOptional.isPresent()) {
                Role role = roleOptional.orElseThrow();

                if (role.getId() == 1L) return ResponseEntity.badRequest().body(
                        Collections.singletonMap("message", "No puede eliminar a este rol.")
                );

                /** PROCEDIMIENTO EXTRA DEBIDO A QUE NO RECONOCIA LA APLICACION EL ELIMINADO EN CASCADA */
                Long roleId = role.getId();
                role.setMenu(null);
                this.roleService.save(role);
                this.roleService.deleteById(roleId);


                /** PROCEDIMIENTO EXTRA DEBIDO A QUE NO RECONOCIA LA APLICACION EL ELIMINADO EN CASCADA */

                return ResponseEntity.ok().body(
                        Collections.singletonMap("message", "Rol eliminado...")
                );
            }
            return ResponseEntity.badRequest().body(
                    Collections.singletonMap("message", "El rol no ha sido encontrado.")
            );
        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest().body(
                    Collections.singletonMap("message", "El rol no ha sido encontrado.")
            );
        }
    }

    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody RoleDto role, BindingResult result) {
        try {
            if (result.hasErrors()) return Resources.validate(result);
            boolean existsByRolName = this.roleService.existsByRolName(role.getRoleName() );
            if ( existsByRolName )
                return ResponseEntity.badRequest().body(
                        Collections.singletonMap(MESSAGE, "El rol ya está registrado"));

            if ( role.getMenu().isEmpty() )
                return ResponseEntity.badRequest().body(
                        Collections.singletonMap(MESSAGE, "Debes asignar por lo menos un menu para cada rol..."));

            Set<UserMenu> menu = role.getMenu().stream()
                    .map( menuId -> this.menuService.findById( menuId ).orElseThrow() )
                    .collect(Collectors.toSet());

            Role newRol = new Role();
            newRol.setRolName( role.getRoleName() );
            newRol.setMenu( menu );
            return ResponseEntity.status(HttpStatus.CREATED).body(this.roleService.save(newRol));
        } catch (NoSuchElementException e){
            return ResponseEntity.badRequest().body(
                    Collections.singletonMap(MESSAGE, "Debes registrar un rol válido -> " + e.getMessage())
            );
        }
    }
}

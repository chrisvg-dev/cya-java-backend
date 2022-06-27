package com.cvg.cya.postulacion.controller;

import com.cvg.cya.postulacion.models.dto.RoleDto;
import com.cvg.cya.postulacion.models.entity.Role;
import com.cvg.cya.postulacion.models.entity.UserMenu;
import com.cvg.cya.postulacion.service.MenuService;
import com.cvg.cya.postulacion.service.RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/roles")
@CrossOrigin(origins = "*")
public class RoleController {
    public static final String MESSAGE = "message";
    private final RoleService roleService;
    private final MenuService menuService;

    public RoleController(RoleService roleService, MenuService menuService) {
        this.roleService = roleService;
        this.menuService = menuService;
    }

    @GetMapping
    public ResponseEntity<List<Role>> findAll(){
        return ResponseEntity.ok(this.roleService.findAll());
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
                    Collections.singletonMap(MESSAGE, "Debes registrar un rol válido...")
            );
        }
    }
}

package com.cvg.cya.postulacion.controller;

import com.cvg.cya.postulacion.models.dto.MenuDto;
import com.cvg.cya.postulacion.models.entity.UserMenu;
import com.cvg.cya.postulacion.service.MenuService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/menu")
@CrossOrigin(origins = "*")
public class MenuController {
    private final MenuService menuService;

    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @GetMapping
    public ResponseEntity<List<UserMenu>> findAll(){
        return ResponseEntity.ok(this.menuService.findAll());
    }

    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody MenuDto menuDto, BindingResult result) {
        if (result.hasErrors()) return Resources.validate(result);

        boolean exists = this.menuService.existsByNameOrPath( menuDto.getName(), menuDto.getPath() );
        if (exists) return ResponseEntity.badRequest().body(
                Collections.singletonMap("message", "El menú ya está registrado")
        );

        UserMenu menu = new UserMenu();
        menu.setName( menuDto.getName() );
        menu.setPath( menuDto.getPath() );

        return ResponseEntity.status(HttpStatus.CREATED).body(this.menuService.save(menu));
    }
}

package com.cvg.cya.postulacion.controller;

import com.cvg.cya.postulacion.models.dto.SessionDto;
import com.cvg.cya.postulacion.models.entity.Session;
import com.cvg.cya.postulacion.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("/api/session")
@CrossOrigin(origins = "*")
public class SessionController {
    private final SessionService service;

    public SessionController(SessionService service) {
        this.service = service;
    }

    @GetMapping("currentDuration")
    public ResponseEntity<?> getCurrentDuration() {
        return ResponseEntity.ok(this.service.consultarTiempoSesion());
    }

    @PostMapping
    public ResponseEntity<?> saveDuration(@RequestBody SessionDto dto) {
       if (dto.getDuration() == null || dto.getDuration() < 30) {
           return ResponseEntity.badRequest().body(
                   Collections.singletonMap("message", "La duración debe ser igual o mayor a 30 segundos"));
       }
        Session session = new Session(0L, dto.getDuration());
        return ResponseEntity.ok(this.service.save(session));
    }
}

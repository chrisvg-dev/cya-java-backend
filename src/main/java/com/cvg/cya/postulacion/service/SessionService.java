package com.cvg.cya.postulacion.service;

import com.cvg.cya.postulacion.models.entity.Session;

public interface SessionService {
    Session consultarTiempoSesion();
    Session save(Session session);
}

package com.cvg.cya.postulacion.service;

import com.cvg.cya.postulacion.models.entity.Session;
import com.cvg.cya.postulacion.models.repository.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SessionServiceImpl implements SessionService{

    private final SessionRepository sessionRepository;

    public SessionServiceImpl(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    @Override
    public Session consultarTiempoSesion() {
        return this.sessionRepository.consultarTiempoSesion();
    }

    @Override
    public Session save(Session session) {
        return this.sessionRepository.save(session);
    }
}

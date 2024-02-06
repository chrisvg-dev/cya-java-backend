package com.cvg.cya.postulacion.service;

import com.cvg.cya.postulacion.models.entity.Session;
import com.cvg.cya.postulacion.models.repository.SessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class SessionServiceImpl implements SessionService{

    private final SessionRepository sessionRepository;

    @Override
    public Session consultarTiempoSesion() {
        return this.sessionRepository.consultarTiempoSesion();
    }

    @Override
    public Session save(Session session) {
        return this.sessionRepository.save(session);
    }
}

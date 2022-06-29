package com.cvg.cya.postulacion.config;

import com.cvg.cya.postulacion.models.UserPrincipal;
import com.cvg.cya.postulacion.models.entity.Session;
import com.cvg.cya.postulacion.models.repository.SessionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@Configuration
public class MyHttpSessionListener implements HttpSessionListener {
    private static final Logger LOG = LoggerFactory.getLogger( MyHttpSessionListener.class );

    private final SessionRepository sessionConfig;

    public MyHttpSessionListener(SessionRepository sessionConfig) {
        this.sessionConfig = sessionConfig;
    }

    @Override
    public void sessionCreated(HttpSessionEvent event) {
        Session config = sessionConfig.consultarTiempoSesion();
        String message = String.format("La sesión durará %s segundos", config.getDuration());
        LOG.info( message );
        event.getSession().setMaxInactiveInterval(config.getDuration());
    }
}

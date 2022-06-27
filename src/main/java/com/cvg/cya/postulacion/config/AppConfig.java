package com.cvg.cya.postulacion.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AppConfig {
    /**
     * DEFINIMOS EL BEAN PARA PODER INYECTAR PASSWORD ENCODER CON EL HASH BCRYPT Y ASIGNARSELO A LA CLAVE DE AUTENTICACION
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

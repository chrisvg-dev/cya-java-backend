package com.cvg.cya.postulacion.models.repository;

import com.cvg.cya.postulacion.models.entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {
    @Query(value = "SELECT s FROM Session s WHERE s.id = (SELECT max(s2.id) FROM Session s2)")
    Session consultarTiempoSesion();
}

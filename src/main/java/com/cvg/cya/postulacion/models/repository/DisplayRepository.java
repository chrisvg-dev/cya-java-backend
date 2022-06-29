package com.cvg.cya.postulacion.models.repository;

import com.cvg.cya.postulacion.models.entity.Display;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DisplayRepository extends JpaRepository<Display, Long> {
}

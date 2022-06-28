package com.cvg.cya.postulacion.models.repository;

import com.cvg.cya.postulacion.models.entity.UserMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuRepository extends JpaRepository<UserMenu, Long> {
}

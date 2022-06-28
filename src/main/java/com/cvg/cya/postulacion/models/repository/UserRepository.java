package com.cvg.cya.postulacion.models.repository;

import com.cvg.cya.postulacion.models.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByEmail(String email);

    boolean existsById(Long id);
    boolean existsByEmail(String email);


    /**
     * VALIDACION FEA
     * @param email
     * @param password
     * @return
     */
    boolean existsByEmailAndPassword(String email, String password);
    Optional<Users> findByEmailAndPassword(String email, String password);
}

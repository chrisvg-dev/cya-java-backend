package com.cvg.cya.postulacion.models.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_rol",
            joinColumns = @JoinColumn(name = "fk_rol", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "fk_user", referencedColumnName = "id"),
            uniqueConstraints = { @UniqueConstraint(columnNames = {"fk_rol", "fk_user"}) }
    )
    private Set<Role> roles;

    @NotBlank
    private String name;
    @NotBlank
    private String lastName;
    @NotBlank
    @Email
    @Column(unique = true)
    private String email;
    @NotBlank
    private String password;
    @NotBlank
    private String safetyWord;
    @NotNull
    private LocalDateTime createdAt;
}

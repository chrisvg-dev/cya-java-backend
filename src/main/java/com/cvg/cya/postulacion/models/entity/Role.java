package com.cvg.cya.postulacion.models.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Entity
@Table(name = "roles")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String rolName;

    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinTable(
            name = "menu_rol",
            joinColumns = @JoinColumn(name = "fk_rol", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "fk_menu", referencedColumnName = "id"),
            uniqueConstraints = { @UniqueConstraint(columnNames = {"fk_rol", "fk_menu"}) }
    )
    private Set<UserMenu> menu;
}

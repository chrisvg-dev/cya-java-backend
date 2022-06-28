package com.cvg.cya.postulacion.models.dto;


import com.cvg.cya.postulacion.models.entity.Role;
import com.cvg.cya.postulacion.models.entity.UserMenu;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Long id;
    @NotNull
    @NotEmpty
    private Set<Long> roles;
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
}

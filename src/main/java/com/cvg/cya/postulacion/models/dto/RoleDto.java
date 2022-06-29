package com.cvg.cya.postulacion.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleDto {
    @NotBlank
    private String roleName;
    @NotNull(message = "Debe seleccionar un menu para este usuario")
    private Set<Long> menu;
}

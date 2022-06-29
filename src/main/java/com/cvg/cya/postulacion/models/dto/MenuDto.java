package com.cvg.cya.postulacion.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuDto {
    private Long id;
    @NotBlank(message = "El nombre del menu no debe estar en blanco")
    private String name;
    @NotBlank(message = "El path no debe estar en blanco")
    private String path;
}

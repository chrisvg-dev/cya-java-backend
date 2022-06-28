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
    @NotBlank
    private String name;
    @NotBlank
    private String path;
}

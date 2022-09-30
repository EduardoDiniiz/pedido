package com.desafio.rest.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResumoDTO {
    private LocalDateTime calculadoEm;
    private List<ContribuinteDTO> amigos;
}

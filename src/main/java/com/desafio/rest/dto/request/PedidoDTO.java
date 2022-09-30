package com.desafio.rest.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PedidoDTO implements Serializable {
    @NotNull
    private BigDecimal desconto;
    @NotNull
    private Boolean descontoEhPorcentagem;
    @NotNull
    private BigDecimal entrega;
    @NotNull
    private List<AmigoDTO> amigos;
}

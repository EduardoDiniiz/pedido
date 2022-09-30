package com.desafio.util;

import com.desafio.rest.dto.request.AmigoDTO;
import com.desafio.rest.dto.request.ItemDTO;
import com.desafio.rest.dto.request.PedidoDTO;

import java.math.BigDecimal;
import java.util.Arrays;

public final class PedidoTestUtil {

    public static PedidoDTO obterPedido() {
        return PedidoDTO.builder().desconto(BigDecimal.valueOf(20)).entrega(BigDecimal.valueOf(8)).descontoEhPorcentagem(false)
                .amigos(Arrays.asList(AmigoDTO.builder().nome("Maria").itens(Arrays.asList(ItemDTO.builder().nome("Hamburguer").quantidade(BigDecimal.ONE)
                        .valor(BigDecimal.valueOf(40)).build(), ItemDTO.builder().nome("Sobremesa").quantidade(BigDecimal.ONE)
                        .valor(BigDecimal.valueOf(2)).build())).build(), AmigoDTO.builder().nome("João")
                .itens(Arrays.asList(ItemDTO.builder().nome("Sanduíche").quantidade(BigDecimal.ONE)
                        .valor(BigDecimal.valueOf(8)).build())).build())).build();
    }
}

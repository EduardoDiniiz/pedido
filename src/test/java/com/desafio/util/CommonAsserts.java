package com.desafio.util;

import com.desafio.rest.dto.request.PedidoDTO;
import com.desafio.rest.dto.response.ContribuinteDTO;
import com.desafio.rest.dto.response.ResumoDTO;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public abstract class CommonAsserts {

    public void assertResumo(final ResumoDTO resumo, final PedidoDTO pedido, final BigDecimal valorEsperado01, final BigDecimal valorEsperado02) {
        assertThat(resumo).isNotNull();
        assertThat(resumo.getAmigos()).isNotNull().hasSize(pedido.getAmigos().size());

        int index = 0;
        for (final ContribuinteDTO amigo : resumo.getAmigos()) {
            BigDecimal valorContribuicao = index == 0 ? valorEsperado01 : valorEsperado02;
            assertThat(amigo).isNotNull();
            assertThat(resumo.getAmigos().get(index).getContribuicao()).isEqualTo(valorContribuicao);
            assertThat(amigo.getNome()).isNotNull().isEqualTo(pedido.getAmigos().get(index).getNome());
            index++;
        }
    }
}

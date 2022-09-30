package com.desafio.service;

import com.desafio.rest.dto.request.PedidoDTO;
import com.desafio.rest.dto.response.ResumoDTO;
import com.desafio.util.CommonAsserts;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static com.desafio.util.PedidoTestUtil.obterPedido;

@ExtendWith(MockitoExtension.class)
public class PedidoServiceTest extends CommonAsserts {

    @InjectMocks
    private PedidoServiceImpl pedidoService;

    @Mock
    private PagamentoServiceImpl pagamentoService;


    @Test
    public void realizarCalculoComValoresValidos() {
        PedidoDTO pedido = obterPedido();
        final ResumoDTO resumo = pedidoService.dividirPedido(pedido);
        assertResumo(resumo, pedido, BigDecimal.valueOf(31.92), BigDecimal.valueOf(6.08));
    }

    @Test
    public void realizarCalculoEmPorcentagemComValoresValidos() {
        PedidoDTO pedido = obterPedido();
        pedido.setDescontoEhPorcentagem(true);
        final ResumoDTO resumo = pedidoService.dividirPedido(pedido);
        assertResumo(resumo, pedido, BigDecimal.valueOf(38.976), BigDecimal.valueOf(7.424));
    }

}

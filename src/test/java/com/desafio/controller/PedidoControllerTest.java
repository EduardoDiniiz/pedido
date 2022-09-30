package com.desafio.controller;

import com.desafio.config.WebMvcConfiguration;
import com.desafio.rest.controller.PedidoController;
import com.desafio.rest.dto.request.PedidoDTO;
import com.desafio.service.PedidoServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.stream.Stream;

import static com.desafio.util.PedidoTestUtil.obterPedido;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(PedidoController.class)
public class PedidoControllerTest {

    @Autowired
    private MockMvc mvc;
    @MockBean
    private PedidoServiceImpl service;
    @MockBean
    private WebMvcConfiguration mvcConfig;

    @Test
    public void postSucesso() throws Exception {
        final HttpHeaders httpHeaders = new HttpHeaders();
        final PedidoDTO pedido = obterPedido();

        enviarRequest(httpHeaders, pedido, HttpStatus.OK);
    }

    @Test
    @ParameterizedTest
    @MethodSource("pedidosComValoresInvalidos")
    public void postError(final PedidoDTO pedido) throws Exception {
        final HttpHeaders httpHeaders = new HttpHeaders();
        enviarRequest(httpHeaders, pedido, HttpStatus.BAD_REQUEST);
    }

    private void enviarRequest(HttpHeaders httpHeaders, PedidoDTO pedido, HttpStatus badRequest) throws Exception {
        mvc.perform(post("/pedido")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .headers(httpHeaders)
                        .content(new ObjectMapper().writeValueAsString(pedido)))
                .andExpect(status().is(badRequest.value()));
    }

    private static Stream<PedidoDTO> pedidosComValoresInvalidos() {
        PedidoDTO pedido01 = obterPedido();
        pedido01.setDesconto(null);
        PedidoDTO pedido02 = obterPedido();
        pedido02.setAmigos(null);
        PedidoDTO pedido03 = obterPedido();
        pedido03.setEntrega(null);
        PedidoDTO pedido04 = obterPedido();
        pedido04.setDescontoEhPorcentagem(null);
        return Stream.of(pedido01, pedido02, pedido03, pedido04);
    }
}

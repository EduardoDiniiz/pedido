package com.desafio.rest.controller;

import com.desafio.rest.dto.request.PedidoDTO;
import com.desafio.rest.dto.response.ResumoDTO;
import com.desafio.service.PedidoServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/pedido")
public class PedidoController {

    private final PedidoServiceImpl service;

    @PostMapping
    public ResponseEntity<ResumoDTO>dividirPedido(final @RequestBody @Valid PedidoDTO pedido) {
        return ResponseEntity.ok(service.dividirPedido(pedido));
    }
}

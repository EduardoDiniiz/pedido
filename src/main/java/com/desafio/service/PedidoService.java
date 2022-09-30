package com.desafio.service;

import com.desafio.rest.dto.request.PedidoDTO;
import com.desafio.rest.dto.response.ResumoDTO;

public interface PedidoService {

    ResumoDTO dividirPedido(PedidoDTO pedido);
}

package com.desafio.service;

import com.desafio.rest.dto.request.AmigoDTO;
import com.desafio.rest.dto.request.PedidoDTO;
import com.desafio.rest.dto.response.ContribuinteDTO;
import com.desafio.rest.dto.response.ResumoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PedidoServiceImpl implements PedidoService {

    private static final BigDecimal BIG_DECIMAL_VALOR_100 = BigDecimal.valueOf(100);
    private final PagamentoServiceImpl pagamentoService;

    @Override
    public ResumoDTO dividirPedido(final PedidoDTO pedido) {
        BigDecimal valorTotalDoPedido = obterValorTotalDoPedido(pedido.getAmigos());
        return obterResumoDoPedido(pedido, valorTotalDoPedido);
    }

    private ResumoDTO obterResumoDoPedido(final PedidoDTO pedido, final BigDecimal valorTotalDoPedido) {
        List<ContribuinteDTO> contribuintes = pedido.getAmigos().stream().map(amigo -> calcularValorFinalDoAmigo(amigo, valorTotalDoPedido, pedido)).collect(Collectors.toList());
        return ResumoDTO.builder().calculadoEm(LocalDateTime.now()).amigos(contribuintes).build();
    }

    private ContribuinteDTO calcularValorFinalDoAmigo(final AmigoDTO amigo, final BigDecimal valorTotalDoPedido, final PedidoDTO pedido) {
        BigDecimal valorTotalDoAmigo = calcularItensDoAmigo(amigo);
        BigDecimal porcentagemValorDoAmigoNoPedido = (valorTotalDoAmigo.multiply(BIG_DECIMAL_VALOR_100)).divide(valorTotalDoPedido);
        BigDecimal valorTotalComEntrega = incluirValorDeEntrega(valorTotalDoAmigo, porcentagemValorDoAmigoNoPedido, pedido);
        BigDecimal valorDoAmigoComDesconto = obterValorComDesconto(valorTotalComEntrega, porcentagemValorDoAmigoNoPedido, pedido, valorTotalDoPedido);

        return ContribuinteDTO.builder().contribuicao(valorDoAmigoComDesconto).linkPagamento(pagamentoService.gerarLinkPagamento()).nome(amigo.getNome()).build();
    }

    private BigDecimal incluirValorDeEntrega(final BigDecimal valorDoAmigoComDesconto, final BigDecimal porcentagemValorDoAmigoNoPedido, final PedidoDTO pedido) {
        BigDecimal valorDeEntrega = porcentagemValorDoAmigoNoPedido.divide(BIG_DECIMAL_VALOR_100).multiply(pedido.getEntrega());
        return valorDoAmigoComDesconto.add(valorDeEntrega);
    }

    private BigDecimal obterValorComDesconto(final BigDecimal valorTotalDoAmigo, final BigDecimal porcentagemValorDoAmigoNoPedido, final PedidoDTO pedido, final BigDecimal valorTotalDoPedido) {

        if (pedido.getDescontoEhPorcentagem()) {
            BigDecimal descontoNumerico = (pedido.getDesconto().divide(BIG_DECIMAL_VALOR_100)).multiply(valorTotalDoAmigo);
            return valorTotalDoAmigo.subtract(descontoNumerico);
        }
        BigDecimal valorDesconto = porcentagemValorDoAmigoNoPedido.divide(BIG_DECIMAL_VALOR_100).multiply(pedido.getDesconto());
        return valorTotalDoAmigo.subtract(valorDesconto);
    }

    private BigDecimal obterValorTotalDoPedido(final List<AmigoDTO> amigos) {
        return amigos.stream().map(this::calcularItensDoAmigo).reduce(BigDecimal::add)
                .orElseThrow(() -> new RuntimeException("Erro no calculo total do pedido."));
    }

    private BigDecimal calcularItensDoAmigo(final AmigoDTO amigo) {
        return amigo.getItens().stream().map(item -> item.getValor().multiply(item.getQuantidade()))
                .reduce(BigDecimal::add).orElseThrow(() -> new RuntimeException("Erro no calculo dos itens do amigo."));
    }
}

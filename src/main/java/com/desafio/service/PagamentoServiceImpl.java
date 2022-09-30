package com.desafio.service;

import org.springframework.stereotype.Service;

@Service
public class PagamentoServiceImpl implements PagamentoService{
    @Override
    public String gerarLinkPagamento() {
        return "https://picpay.com/invoice?invoice=122131&value=12345";
    }
}

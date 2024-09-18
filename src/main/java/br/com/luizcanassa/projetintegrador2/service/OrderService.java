package br.com.luizcanassa.projetintegrador2.service;

import br.com.luizcanassa.projetintegrador2.domain.dto.order.local.CreateOrderDTO;

public interface OrderService {

    void create(CreateOrderDTO createOrderDTO);

}

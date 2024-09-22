package br.com.luizcanassa.projetintegrador2.service;

import br.com.luizcanassa.projetintegrador2.domain.dto.order.CreateOrderDeliveryDTO;

public interface OrderDeliveryService {

    void create(CreateOrderDeliveryDTO createOrderDeliveryDTO);
}

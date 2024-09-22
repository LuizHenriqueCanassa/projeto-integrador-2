package br.com.luizcanassa.projetintegrador2.service;

import br.com.luizcanassa.projetintegrador2.domain.dto.order.delivery.CreateOrderDeliveryDTO;
import br.com.luizcanassa.projetintegrador2.domain.dto.order.delivery.OrderDeliveryDTO;

import java.util.List;

public interface OrderDeliveryService {

    List<OrderDeliveryDTO> findAll();

    void create(CreateOrderDeliveryDTO createOrderDeliveryDTO);
}

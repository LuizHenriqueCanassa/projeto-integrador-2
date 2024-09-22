package br.com.luizcanassa.projetintegrador2.service;

import br.com.luizcanassa.projetintegrador2.domain.dto.order.delivery.CreateOrderDeliveryDTO;
import br.com.luizcanassa.projetintegrador2.domain.dto.order.delivery.OrderDeliveryDTO;
import br.com.luizcanassa.projetintegrador2.domain.dto.order.delivery.OrderDeliveryDetailDTO;
import br.com.luizcanassa.projetintegrador2.domain.dto.order.delivery.OrderDeliveryEditDTO;

import java.util.List;

public interface OrderDeliveryService {

    List<OrderDeliveryDTO> findAll();

    OrderDeliveryDetailDTO findById(Long id);

    void create(CreateOrderDeliveryDTO createOrderDeliveryDTO);

    void editStatus(Long id, OrderDeliveryEditDTO orderDeliveryEditDTO);
}

package br.com.luizcanassa.projetintegrador2.service;

import br.com.luizcanassa.projetintegrador2.domain.dto.order.OrderSummaryDTO;
import br.com.luizcanassa.projetintegrador2.domain.dto.order.delivery.*;

import java.util.List;

public interface OrderDeliveryService {

    List<OrderDeliveryDTO> findAll();

    OrderDeliveryDetailDTO findById(Long id);

    void create(CreateOrderDeliveryDTO createOrderDeliveryDTO);

    void editStatus(Long id, OrderDeliveryEditDTO orderDeliveryEditDTO);

    Integer getQuantityOrdersDeliveryToday();

    OrderSummaryDTO getQuantityOrdersDeliveryLast7Days();
}

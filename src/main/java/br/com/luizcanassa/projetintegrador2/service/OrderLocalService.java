package br.com.luizcanassa.projetintegrador2.service;

import br.com.luizcanassa.projetintegrador2.domain.dto.order.OrderSummaryDTO;
import br.com.luizcanassa.projetintegrador2.domain.dto.order.local.CreateOrderLocalDTO;
import br.com.luizcanassa.projetintegrador2.domain.dto.order.local.OrderLocalDTO;
import br.com.luizcanassa.projetintegrador2.domain.dto.order.local.OrderLocalDetailDTO;
import br.com.luizcanassa.projetintegrador2.domain.dto.order.local.OrderLocalEditDTO;
import br.com.luizcanassa.projetintegrador2.exception.ProductNotFoundException;

import java.util.List;

public interface OrderLocalService {

    List<OrderLocalDTO> findAll();

    void create(CreateOrderLocalDTO createOrderLocalDTO) throws ProductNotFoundException;

    OrderLocalDetailDTO findById(Long id);

    void editStatus(Long id, OrderLocalEditDTO orderLocalEditDTO);

    Integer getQuantityOrdersLocalToday();

    OrderSummaryDTO getQuantityOrdersLocalLast7Days();
}

package br.com.luizcanassa.projetintegrador2.service;

import br.com.luizcanassa.projetintegrador2.domain.dto.order.local.CreateOrderLocalDTO;
import br.com.luizcanassa.projetintegrador2.domain.dto.order.local.OrderLocalDTO;
import br.com.luizcanassa.projetintegrador2.exception.ProductNotFoundException;

import java.util.List;

public interface OrderLocalService {

    List<OrderLocalDTO> findAll();

    void create(CreateOrderLocalDTO createOrderLocalDTO) throws ProductNotFoundException;

}

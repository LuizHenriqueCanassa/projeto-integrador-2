package br.com.luizcanassa.projetintegrador2.service;

import br.com.luizcanassa.projetintegrador2.domain.dto.order.local.CreateOrderLocalDTO;
import br.com.luizcanassa.projetintegrador2.exception.ProductNotFoundException;

public interface OrderLocalService {

    void create(CreateOrderLocalDTO createOrderLocalDTO) throws ProductNotFoundException;

}

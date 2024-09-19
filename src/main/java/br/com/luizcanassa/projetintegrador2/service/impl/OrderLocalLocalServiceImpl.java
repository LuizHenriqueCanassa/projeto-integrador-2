package br.com.luizcanassa.projetintegrador2.service.impl;

import br.com.luizcanassa.projetintegrador2.domain.dto.order.local.CreateOrderDTO;
import br.com.luizcanassa.projetintegrador2.domain.dto.order.local.CreateOrderLocalDTO;
import br.com.luizcanassa.projetintegrador2.domain.dto.order.local.OrderLocalDTO;
import br.com.luizcanassa.projetintegrador2.domain.entity.OrderEntity;
import br.com.luizcanassa.projetintegrador2.domain.entity.OrderItemEntity;
import br.com.luizcanassa.projetintegrador2.domain.entity.OrderLocalEntity;
import br.com.luizcanassa.projetintegrador2.exception.ProductNotFoundException;
import br.com.luizcanassa.projetintegrador2.mapper.OrderMapper;
import br.com.luizcanassa.projetintegrador2.repository.OrderLocalRepository;
import br.com.luizcanassa.projetintegrador2.repository.OrderRepository;
import br.com.luizcanassa.projetintegrador2.repository.ProductRepository;
import br.com.luizcanassa.projetintegrador2.service.OrderLocalService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderLocalLocalServiceImpl implements OrderLocalService {

    private final OrderRepository orderRepository;

    private final OrderLocalRepository orderLocalRepository;

    private final ProductRepository productRepository;

    private final OrderMapper orderMapper;

    public OrderLocalLocalServiceImpl(final OrderRepository orderRepository, final OrderLocalRepository orderLocalRepository, final ProductRepository productRepository, final OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.orderLocalRepository = orderLocalRepository;
        this.productRepository = productRepository;
        this.orderMapper = orderMapper;
    }

    @Override
    public List<OrderLocalDTO> findAll() {
        return orderLocalRepository.findAll().stream().map(orderMapper::toOrderLocalDTO).collect(Collectors.toList());
    }

    @Override
    public void create(final CreateOrderLocalDTO createOrderLocalDTO) throws ProductNotFoundException {
        final var order = new OrderEntity();

        order.setDetails(createOrderLocalDTO.getDetails());

        final var savedOrder = orderRepository.save(order);

        fillOrderItems(createOrderLocalDTO, savedOrder);

        fillOrderAmount(savedOrder);

        final var orderLocalEntity = new OrderLocalEntity();
        orderLocalEntity.setOrder(savedOrder);
        orderLocalEntity.setCardControl(createOrderLocalDTO.getCardControl());

        orderLocalRepository.save(orderLocalEntity);
        orderRepository.save(savedOrder);
    }

    private void fillOrderAmount(final OrderEntity orderEntity) {
        orderEntity.getOrderItems().forEach(orderItemEntity -> {
            orderEntity.setTotalAmount(orderEntity.getTotalAmount().add(orderItemEntity.getAmount()));
        });
    }

    private void fillOrderItems(final CreateOrderLocalDTO createOrderLocalDTO, final OrderEntity savedOrder) throws ProductNotFoundException {
        var orderItems = new ArrayList<OrderItemEntity>();

        createOrderLocalDTO.getOrderItems().forEach(item -> {
            final var product = productRepository.findById(item.getProductId()).orElseThrow(() -> new ProductNotFoundException("Produto não encontrado"));

            var orderItem = new OrderItemEntity();
            orderItem.setQuantity(item.getQuantity());
            orderItem.setProduct(product);
            orderItem.setAmount(product.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
            orderItem.setOrder(savedOrder);

            orderItems.add(orderItem);
        });

        savedOrder.setOrderItems(orderItems);
    }
}

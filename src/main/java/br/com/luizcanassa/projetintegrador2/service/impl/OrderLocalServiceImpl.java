package br.com.luizcanassa.projetintegrador2.service.impl;

import br.com.luizcanassa.projetintegrador2.domain.dto.order.local.CreateOrderDTO;
import br.com.luizcanassa.projetintegrador2.domain.dto.order.local.CreateOrderLocalDTO;
import br.com.luizcanassa.projetintegrador2.domain.entity.OrderEntity;
import br.com.luizcanassa.projetintegrador2.domain.entity.OrderItemEntity;
import br.com.luizcanassa.projetintegrador2.domain.entity.OrderLocalEntity;
import br.com.luizcanassa.projetintegrador2.repository.OrderLocalRepository;
import br.com.luizcanassa.projetintegrador2.repository.OrderRepository;
import br.com.luizcanassa.projetintegrador2.repository.ProductRepository;
import br.com.luizcanassa.projetintegrador2.service.OrderService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;

@Service
public class OrderLocalServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final OrderLocalRepository orderLocalRepository;

    private final ProductRepository productRepository;

    public OrderLocalServiceImpl(final OrderRepository orderRepository, final OrderLocalRepository orderLocalRepository, final ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.orderLocalRepository = orderLocalRepository;
        this.productRepository = productRepository;
    }

    @Override
    public void create(final CreateOrderDTO createOrderDTO) {
        final CreateOrderLocalDTO createOrderLocalDTO = (CreateOrderLocalDTO) createOrderDTO;

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

    private void fillOrderItems(final CreateOrderLocalDTO createOrderLocalDTO, final OrderEntity savedOrder) {
        var orderItems = new ArrayList<OrderItemEntity>();

        createOrderLocalDTO.getOrderItems().forEach(item -> {
            final var product = productRepository.findById(item.getProductId()).orElseThrow();

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

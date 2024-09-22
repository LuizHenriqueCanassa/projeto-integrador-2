package br.com.luizcanassa.projetintegrador2.service.impl;

import br.com.luizcanassa.projetintegrador2.domain.dto.order.CreateOrderDeliveryDTO;
import br.com.luizcanassa.projetintegrador2.domain.dto.order.local.CreateOrderLocalDTO;
import br.com.luizcanassa.projetintegrador2.domain.entity.OrderDeliveryEntity;
import br.com.luizcanassa.projetintegrador2.domain.entity.OrderEntity;
import br.com.luizcanassa.projetintegrador2.domain.entity.OrderItemEntity;
import br.com.luizcanassa.projetintegrador2.domain.entity.OrderLocalEntity;
import br.com.luizcanassa.projetintegrador2.exception.CustomerNotFoundException;
import br.com.luizcanassa.projetintegrador2.exception.ProductNotFoundException;
import br.com.luizcanassa.projetintegrador2.repository.CustomerRepository;
import br.com.luizcanassa.projetintegrador2.repository.OrderDeliveryRepository;
import br.com.luizcanassa.projetintegrador2.repository.OrderRepository;
import br.com.luizcanassa.projetintegrador2.repository.ProductRepository;
import br.com.luizcanassa.projetintegrador2.service.OrderDeliveryService;
import br.com.luizcanassa.projetintegrador2.utils.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Service
public class OrderDeliveryServiceImpl implements OrderDeliveryService {

    private final OrderRepository orderRepository;

    private final ProductRepository productRepository;

    private final OrderDeliveryRepository orderDeliveryRepository;

    private final CustomerRepository customerRepository;

    public OrderDeliveryServiceImpl(
            final OrderRepository orderRepository,
            final ProductRepository productRepository,
            final OrderDeliveryRepository orderDeliveryRepository,
            final CustomerRepository customerRepository
    ) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.orderDeliveryRepository = orderDeliveryRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    @Transactional(rollbackFor = {CustomerNotFoundException.class, ProductNotFoundException.class})
    public void create(final CreateOrderDeliveryDTO createOrderDeliveryDTO) {
        final var order = new OrderEntity();

        order.setDetails(createOrderDeliveryDTO.getDetails());
        order.setCreatedAt(LocalDateTime.now());

        final var savedOrder = orderRepository.save(order);

        fillOrderItems(createOrderDeliveryDTO, savedOrder);

        fillOrderAmount(savedOrder);

        final var orderDelivery = new OrderDeliveryEntity();
        orderDelivery.setOrder(savedOrder);
        orderDelivery.setCustomer(
                customerRepository.findByDocument(StringUtils.removeDocumentMask(createOrderDeliveryDTO.getDocument()))
                        .orElseThrow(() -> new CustomerNotFoundException("Cliente não encontrado!"))
        );

        orderDeliveryRepository.save(orderDelivery);
        orderRepository.save(savedOrder);
    }

    private void fillOrderAmount(final OrderEntity orderEntity) {
        orderEntity.getOrderItems().forEach(orderItemEntity -> {
            orderEntity.setTotalAmount(orderEntity.getTotalAmount().add(orderItemEntity.getAmount()));
        });
    }

    private void fillOrderItems(final CreateOrderDeliveryDTO createOrderDeliveryDTO, final OrderEntity savedOrder) throws ProductNotFoundException {
        var orderItems = new ArrayList<OrderItemEntity>();

        createOrderDeliveryDTO.getOrderItems().forEach(item -> {
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

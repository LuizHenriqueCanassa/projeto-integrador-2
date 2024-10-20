package br.com.luizcanassa.projetintegrador2.service.impl;

import br.com.luizcanassa.projetintegrador2.domain.dto.order.OrderSummaryDTO;
import br.com.luizcanassa.projetintegrador2.domain.dto.order.delivery.*;
import br.com.luizcanassa.projetintegrador2.domain.entity.OrderDeliveryEntity;
import br.com.luizcanassa.projetintegrador2.domain.entity.OrderEntity;
import br.com.luizcanassa.projetintegrador2.domain.entity.OrderItemEntity;
import br.com.luizcanassa.projetintegrador2.domain.enums.OrdersStatusEnum;
import br.com.luizcanassa.projetintegrador2.exception.CustomerNotFoundException;
import br.com.luizcanassa.projetintegrador2.exception.OrderNotFoundException;
import br.com.luizcanassa.projetintegrador2.exception.ProductNotFoundException;
import br.com.luizcanassa.projetintegrador2.mapper.OrderMapper;
import br.com.luizcanassa.projetintegrador2.repository.CustomerRepository;
import br.com.luizcanassa.projetintegrador2.repository.OrderDeliveryRepository;
import br.com.luizcanassa.projetintegrador2.repository.OrderRepository;
import br.com.luizcanassa.projetintegrador2.repository.ProductRepository;
import br.com.luizcanassa.projetintegrador2.service.OrderDeliveryService;
import br.com.luizcanassa.projetintegrador2.utils.DateUtils;
import br.com.luizcanassa.projetintegrador2.utils.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderDeliveryServiceImpl implements OrderDeliveryService {

    private final OrderRepository orderRepository;

    private final ProductRepository productRepository;

    private final OrderDeliveryRepository orderDeliveryRepository;

    private final CustomerRepository customerRepository;

    private final OrderMapper orderMapper;

    public OrderDeliveryServiceImpl(
            final OrderRepository orderRepository,
            final ProductRepository productRepository,
            final OrderDeliveryRepository orderDeliveryRepository,
            final CustomerRepository customerRepository,
            final OrderMapper orderMapper
    ) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.orderDeliveryRepository = orderDeliveryRepository;
        this.customerRepository = customerRepository;
        this.orderMapper = orderMapper;
    }

    @Override
    public List<OrderDeliveryDTO> findAll() {
        return orderDeliveryRepository.findAll().stream().map(orderMapper::toOrderDeliveryDTO).collect(Collectors.toList());
    }

    @Override
    public OrderDeliveryDetailDTO findById(final Long id) {
        return orderMapper.toDeliveryOrderDetailDTO(orderDeliveryRepository.findById(id).orElseThrow(() -> new OrderNotFoundException("Pedido não encontrado")));
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

    @Override
    public void editStatus(final Long id, final OrderDeliveryEditDTO orderDeliveryEditDTO) {
        final var order = orderDeliveryRepository.findById(id).orElseThrow(() -> new OrderNotFoundException("Pedido não encontrado!")).getOrder();

        order.setStatus(OrdersStatusEnum.valueOf(orderDeliveryEditDTO.getStatus()));
        order.setPaid(orderDeliveryEditDTO.getPaid());

        orderRepository.save(order);
    }

    @Override
    public Integer getQuantityOrdersDeliveryToday() {
        return orderDeliveryRepository.countByCreatedAtBetween(LocalDate.now().atStartOfDay(), LocalDate.now().atTime(LocalTime.MAX));
    }

    @Override
    public OrderSummaryDTO getQuantityOrdersDeliveryLast7Days() {
        final OrderSummaryDTO orderDeliverySummary = new OrderSummaryDTO();
        final Map<String, Long> quantityByDayOfWeek = new LinkedHashMap<>();

        final List<String> nameOfDayWeekLast7Days = DateUtils.getLastShortNameDayOfWeek(7);

        orderDeliverySummary.setLast7DaysOfWeek(nameOfDayWeekLast7Days);

        final var ordersDeliveryLast7Days = orderDeliveryRepository.findAllByCreatedAtBetween(
                LocalDate.now().minusDays(7).atStartOfDay(),
                LocalDate.now().atTime(LocalTime.MAX)
        );

        final var ordersPerDayOfWeek = ordersDeliveryLast7Days.stream().collect(
                Collectors.groupingBy(
                        orderDelivery -> DateUtils.getShortNameDayOfWeek(orderDelivery.getCreatedAt()),
                        Collectors.counting()
                )
        );

        nameOfDayWeekLast7Days.forEach(day -> {
            quantityByDayOfWeek.put(day, ordersPerDayOfWeek.getOrDefault(day, 0L));
        });

        orderDeliverySummary.setOrdersQuantityByDay(quantityByDayOfWeek);

        return orderDeliverySummary;
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

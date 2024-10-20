package br.com.luizcanassa.projetintegrador2.service.impl;

import br.com.luizcanassa.projetintegrador2.domain.dto.order.OrderSummaryDTO;
import br.com.luizcanassa.projetintegrador2.domain.dto.order.local.CreateOrderLocalDTO;
import br.com.luizcanassa.projetintegrador2.domain.dto.order.local.OrderLocalDTO;
import br.com.luizcanassa.projetintegrador2.domain.dto.order.local.OrderLocalDetailDTO;
import br.com.luizcanassa.projetintegrador2.domain.dto.order.local.OrderLocalEditDTO;
import br.com.luizcanassa.projetintegrador2.domain.entity.OrderEntity;
import br.com.luizcanassa.projetintegrador2.domain.entity.OrderItemEntity;
import br.com.luizcanassa.projetintegrador2.domain.entity.OrderLocalEntity;
import br.com.luizcanassa.projetintegrador2.domain.enums.OrdersStatusEnum;
import br.com.luizcanassa.projetintegrador2.exception.OrderNotFoundException;
import br.com.luizcanassa.projetintegrador2.exception.ProductNotFoundException;
import br.com.luizcanassa.projetintegrador2.mapper.OrderMapper;
import br.com.luizcanassa.projetintegrador2.repository.OrderLocalRepository;
import br.com.luizcanassa.projetintegrador2.repository.OrderRepository;
import br.com.luizcanassa.projetintegrador2.repository.ProductRepository;
import br.com.luizcanassa.projetintegrador2.service.OrderLocalService;
import br.com.luizcanassa.projetintegrador2.utils.DateUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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

    @Override
    public OrderLocalDetailDTO findById(final Long id) {
        return orderMapper.toLocalOrderDetailDTO(orderLocalRepository.findById(id).orElseThrow(() -> new OrderNotFoundException("Pedido não encontrado!")));
    }

    @Override
    public void editStatus(final Long id, final OrderLocalEditDTO orderLocalEditDTO) {
        final var order = orderLocalRepository.findById(id).orElseThrow(() -> new OrderNotFoundException("Pedido não encontrado!")).getOrder();

        order.setStatus(OrdersStatusEnum.valueOf(orderLocalEditDTO.getStatus()));
        order.setPaid(orderLocalEditDTO.getPaid());

        orderRepository.save(order);
    }

    @Override
    public Integer getQuantityOrdersLocalToday() {
        return orderLocalRepository.countByCreatedAtBetween(LocalDate.now().atStartOfDay(), LocalDate.now().atTime(LocalTime.MAX));
    }

    @Override
    public OrderSummaryDTO getQuantityOrdersLocalLast7Days() {
        final OrderSummaryDTO orderLocalSummary = new OrderSummaryDTO();
        final Map<String, Long> quantityByDayOfWeek = new LinkedHashMap<>();

        final List<String> nameOfDayWeekLast7Days = DateUtils.getLastShortNameDayOfWeek(7);

        orderLocalSummary.setLast7DaysOfWeek(nameOfDayWeekLast7Days);

        final var ordersDeliveryLast7Days = orderLocalRepository.findAllByCreatedAtBetween(
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

        orderLocalSummary.setOrdersQuantityByDay(quantityByDayOfWeek);

        return orderLocalSummary;
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

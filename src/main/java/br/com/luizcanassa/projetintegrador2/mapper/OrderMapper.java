package br.com.luizcanassa.projetintegrador2.mapper;

import br.com.luizcanassa.projetintegrador2.domain.dto.order.local.OrderLocalDTO;
import br.com.luizcanassa.projetintegrador2.domain.dto.order.local.OrderLocalDetailDTO;
import br.com.luizcanassa.projetintegrador2.domain.entity.OrderEntity;
import br.com.luizcanassa.projetintegrador2.domain.entity.OrderLocalEntity;
import br.com.luizcanassa.projetintegrador2.domain.enums.OrdersStatusEnum;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.math.BigDecimal;
import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderMapper {

    @Mapping(target = "totalAmount", source = "orderLocal", qualifiedByName = "toTotalAmount")
    @Mapping(target = "status", source = "orderLocal", qualifiedByName = "toStatus")
    OrderLocalDTO toOrderLocalDTO(OrderLocalEntity orderLocal);

    List<OrderLocalDTO> toOrderLocalDTOList(List<OrderLocalEntity> orders);

    @Mapping(target = "totalAmount", source = "orderLocal.order.totalAmount")
    @Mapping(target = "details", source = "orderLocal.order.details")
    @Mapping(target = "orderItems", source = "orderLocal.order", qualifiedByName = "toOrderItems")
    @Mapping(target = "status", source = "orderLocal.order.status", qualifiedByName = "toStatus")
    OrderLocalDetailDTO toLocalOrderDetailDTO(OrderLocalEntity orderLocal);

    @Named("toTotalAmount")
    default BigDecimal toTotalAmount(OrderLocalEntity orderLocal) {
        return orderLocal.getOrder().getTotalAmount();
    }

    @Named("toStatus")
    default String toStatus(OrderLocalEntity orderLocal) {
        return orderLocal.getOrder().getStatus().getName();
    }

    @Named("toOrderItems")
    default String toOrderItems(OrderEntity orderEntity) {
        StringBuilder stringBuilder = new StringBuilder();

        orderEntity.getOrderItems().forEach(orderItem -> {
            stringBuilder.append(orderItem.getQuantity()).append("x, ").append(orderItem.getProduct().getName()).append("\n");
        });

        return stringBuilder.toString();
    }

    @Named("toStatus")
    default String toStatus(OrdersStatusEnum ordersStatusEnum) {
        return ordersStatusEnum.toString();
    }
}

package br.com.luizcanassa.projetintegrador2.mapper;

import br.com.luizcanassa.projetintegrador2.domain.dto.order.delivery.OrderDeliveryDTO;
import br.com.luizcanassa.projetintegrador2.domain.dto.order.local.OrderLocalDTO;
import br.com.luizcanassa.projetintegrador2.domain.dto.order.local.OrderLocalDetailDTO;
import br.com.luizcanassa.projetintegrador2.domain.entity.OrderDeliveryEntity;
import br.com.luizcanassa.projetintegrador2.domain.entity.OrderEntity;
import br.com.luizcanassa.projetintegrador2.domain.entity.OrderLocalEntity;
import br.com.luizcanassa.projetintegrador2.domain.enums.OrdersStatusEnum;
import br.com.luizcanassa.projetintegrador2.utils.StringUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderMapper {

    @Mapping(target = "totalAmount", source = "orderLocal.order", qualifiedByName = "toTotalAmount")
    @Mapping(target = "status", source = "orderLocal.order", qualifiedByName = "toStatus")
    OrderLocalDTO toOrderLocalDTO(OrderLocalEntity orderLocal);

    @Mapping(target = "document", source = "orderDelivery.customer.document", qualifiedByName = "toDocumentMasked")
    @Mapping(target = "totalAmount", source = "orderDelivery.order", qualifiedByName = "toTotalAmount")
    @Mapping(target = "status", source = "orderDelivery.order", qualifiedByName = "toStatus")
    OrderDeliveryDTO toOrderDeliveryDTO(OrderDeliveryEntity orderDelivery);

    List<OrderLocalDTO> toOrderLocalDTOList(List<OrderLocalEntity> orders);

    List<OrderDeliveryDTO> toOrderDeliveryDTOList(List<OrderDeliveryEntity> orders);

    @Mapping(target = "totalAmount", source = "orderLocal.order.totalAmount")
    @Mapping(target = "details", source = "orderLocal.order.details")
    @Mapping(target = "orderItems", source = "orderLocal.order", qualifiedByName = "toOrderItems")
    @Mapping(target = "status", source = "orderLocal.order.status", qualifiedByName = "toStatus")
    OrderLocalDetailDTO toLocalOrderDetailDTO(OrderLocalEntity orderLocal);

    @Named("toTotalAmount")
    default BigDecimal toTotalAmount(OrderEntity order) {
        return order.getTotalAmount();
    }

    @Named("toStatus")
    default String toStatus(OrderEntity order) {
        return order.getStatus().getName();
    }

    @Named("toDocumentMasked")
    default String toDocumentMasked(String document) throws ParseException {
        return StringUtils.addDocumentMask(document);
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

package br.com.luizcanassa.projetintegrador2.mapper;

import br.com.luizcanassa.projetintegrador2.domain.dto.order.delivery.OrderDeliveryDTO;
import br.com.luizcanassa.projetintegrador2.domain.dto.order.delivery.OrderDeliveryDetailDTO;
import br.com.luizcanassa.projetintegrador2.domain.dto.order.local.OrderLocalDTO;
import br.com.luizcanassa.projetintegrador2.domain.dto.order.local.OrderLocalDetailDTO;
import br.com.luizcanassa.projetintegrador2.domain.entity.*;
import br.com.luizcanassa.projetintegrador2.domain.enums.OrdersStatusEnum;
import br.com.luizcanassa.projetintegrador2.utils.StringUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.math.BigDecimal;
import java.text.ParseException;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderMapper {

    @Mapping(target = "totalAmount", source = "orderLocal.order", qualifiedByName = "toTotalAmount")
    @Mapping(target = "status", source = "orderLocal.order", qualifiedByName = "toStatus")
    @Mapping(target = "paid", source = "orderLocal.order.paid")
    OrderLocalDTO toOrderLocalDTO(OrderLocalEntity orderLocal);

    @Mapping(target = "document", source = "orderDelivery.customer.document", qualifiedByName = "toDocumentMasked")
    @Mapping(target = "totalAmount", source = "orderDelivery.order", qualifiedByName = "toTotalAmount")
    @Mapping(target = "status", source = "orderDelivery.order", qualifiedByName = "toStatus")
    OrderDeliveryDTO toOrderDeliveryDTO(OrderDeliveryEntity orderDelivery);

    @Mapping(target = "totalAmount", source = "orderLocal.order.totalAmount")
    @Mapping(target = "details", source = "orderLocal.order.details")
    @Mapping(target = "orderItems", source = "orderLocal.order", qualifiedByName = "toOrderItems")
    @Mapping(target = "status", source = "orderLocal.order.status", qualifiedByName = "toStatus")
    @Mapping(target = "paid", source = "orderLocal.order.paid")
    OrderLocalDetailDTO toLocalOrderDetailDTO(OrderLocalEntity orderLocal);

    @Mapping(target = "totalAmount", source = "orderDelivery.order.totalAmount")
    @Mapping(target = "address", source = "orderDelivery.customer", qualifiedByName = "toAddress")
    @Mapping(target = "details", source = "orderDelivery.order.details")
    @Mapping(target = "orderItems", source = "orderDelivery.order", qualifiedByName = "toOrderItems")
    @Mapping(target = "status", source = "orderDelivery.order.status", qualifiedByName = "toStatus")
    @Mapping(target = "document", source = "orderDelivery.customer.document", qualifiedByName = "toDocumentMasked")
    OrderDeliveryDetailDTO toDeliveryOrderDetailDTO(OrderDeliveryEntity orderDelivery);

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

    @Named("toAddress")
    default String toAddress(CustomerEntity customerEntity) {
        StringBuilder stringBuilder = new StringBuilder();

        final var addressEntity = customerEntity.getAddresses().get(0);

        stringBuilder.append("Rua: ").append(addressEntity.getStreetName()).append("\n");
        stringBuilder.append("Número: ").append(addressEntity.getNumber()).append("\n");
        stringBuilder.append("Bairro: ").append(addressEntity.getDistrict());

        return stringBuilder.toString();
    }

    @Named("toStatus")
    default String toStatus(OrdersStatusEnum ordersStatusEnum) {
        return ordersStatusEnum.toString();
    }
}

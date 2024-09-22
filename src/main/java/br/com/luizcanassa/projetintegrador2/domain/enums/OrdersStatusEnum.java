package br.com.luizcanassa.projetintegrador2.domain.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public enum OrdersStatusEnum {
    AWAITING("Aguardando"),
    IN_PROCESSING("Em processamento"),
    ALREADY("Pronto"),
    PAID("Pago"),
    DELIVERING("Saiu para entrega"),
    DELIVERED("Entregue"),
    CANCELED("Cancelado");

    private final String name;

    OrdersStatusEnum(final String name) {
        this.name = name;
    }

    public static List<OrdersStatusEnum> getLocalStatus() {
        return Arrays.stream(OrdersStatusEnum.values()).filter(ordersStatusEnum -> ordersStatusEnum.equals(OrdersStatusEnum.AWAITING)
                || ordersStatusEnum.equals(OrdersStatusEnum.IN_PROCESSING)
                || ordersStatusEnum.equals(OrdersStatusEnum.ALREADY)
                || ordersStatusEnum.equals(OrdersStatusEnum.PAID)
                || ordersStatusEnum.equals(OrdersStatusEnum.CANCELED)).collect(Collectors.toList()
        );
    }
}

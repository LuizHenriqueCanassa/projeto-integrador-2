package br.com.luizcanassa.projetintegrador2.domain.enums;

import lombok.Getter;

@Getter
public enum OrdersStatusEnum {
    AWAITING("Aguardando"),
    IN_PROCESSING("Em processamento"),
    ALREADY("Pronto"),
    DELIVERING("Saiu para entrega"),
    DELIVERED("Entregue"),
    CANCELED("Cancelado");

    private final String name;

    OrdersStatusEnum(final String name) {
        this.name = name;
    }
}

package br.com.luizcanassa.projetintegrador2.mapper;

import br.com.luizcanassa.projetintegrador2.domain.dto.order.local.OrderLocalDTO;
import br.com.luizcanassa.projetintegrador2.domain.entity.OrderLocalEntity;
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

    @Named("toTotalAmount")
    default BigDecimal toTotalAmount(OrderLocalEntity orderLocal) {
        return orderLocal.getOrder().getTotalAmount();
    }

    @Named("toStatus")
    default String toStatus(OrderLocalEntity orderLocal) {
        return orderLocal.getOrder().getStatus().getName();
    }
}

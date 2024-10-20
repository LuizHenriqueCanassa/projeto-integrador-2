package br.com.luizcanassa.projetintegrador2.service.impl;

import br.com.luizcanassa.projetintegrador2.domain.dto.BillingSummaryDTO;
import br.com.luizcanassa.projetintegrador2.domain.entity.OrderEntity;
import br.com.luizcanassa.projetintegrador2.repository.OrderRepository;
import br.com.luizcanassa.projetintegrador2.service.OrderService;
import br.com.luizcanassa.projetintegrador2.utils.DateUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.DoubleSummaryStatistics;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    public OrderServiceImpl(final OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public BigDecimal getTotalBillingOfDay() {
        return orderRepository.sumBillingOfDay(
                LocalDate.now().atStartOfDay(), LocalDate.now().atTime(LocalTime.MAX)
        );
    }

    @Override
    public Integer getTotalOrdersOfMonth() {
       return orderRepository.countByCreatedAtBetween(
                DateUtils.dateStartOfMonth().atStartOfDay(),
                DateUtils.dateEndOfMonth().atTime(LocalTime.MAX)
        );
    }

    @Override
    public BillingSummaryDTO getBillingLast8Months() {
        final List<String> last8Months = DateUtils.getShortNameOfMonths(8);
        final Map<String, BigDecimal> billingByMonth = new LinkedHashMap<>();
        final BillingSummaryDTO billingSummaryDTO = new BillingSummaryDTO();

        billingSummaryDTO.setLast8Months(last8Months);

        final var ordersLast8Months = orderRepository.findByCreatedAtBetween(
                LocalDate.now().minusMonths(8).atStartOfDay(),
                LocalDate.now().atTime(LocalTime.MAX)
        );

        final var ordersSummarizedByMonth = ordersLast8Months.stream().collect(
                Collectors.groupingBy(
                        order -> DateUtils.getShortNameOfMonth(order.getCreatedAt()),
                        Collectors.summingDouble(order -> order.getTotalAmount().doubleValue())
                )
        );

        last8Months.forEach(month -> {
            if (!ordersSummarizedByMonth.containsKey(month)) {
                billingByMonth.put(month, BigDecimal.ZERO);
            } else {
                billingByMonth.put(month, BigDecimal.valueOf(ordersSummarizedByMonth.get(month)).setScale(2, RoundingMode.UNNECESSARY));
            }
        });

        billingSummaryDTO.setAmounts(billingByMonth);

        return billingSummaryDTO;
    }
}

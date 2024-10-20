package br.com.luizcanassa.projetintegrador2.domain.dto.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderSummaryDTO {

    public List<String> last7DaysOfWeek;

    public Map<String, Long> ordersQuantityByDay;
}

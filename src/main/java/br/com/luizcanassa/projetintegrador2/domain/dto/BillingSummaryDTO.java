package br.com.luizcanassa.projetintegrador2.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BillingSummaryDTO {

    private List<String> last8Months;

    private Map<String, BigDecimal> amounts;
}

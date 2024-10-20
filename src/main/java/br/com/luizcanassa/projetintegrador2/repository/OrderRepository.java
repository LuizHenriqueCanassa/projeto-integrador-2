package br.com.luizcanassa.projetintegrador2.repository;

import br.com.luizcanassa.projetintegrador2.domain.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

    @Query("SELECT sum(o.totalAmount) FROM OrderEntity o WHERE o.createdAt BETWEEN :start AND :end")
    BigDecimal sumBillingOfDay(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    Integer countByCreatedAtBetween(LocalDateTime start, LocalDateTime end);

    List<OrderEntity> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end);
}

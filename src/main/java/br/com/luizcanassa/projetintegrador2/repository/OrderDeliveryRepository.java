package br.com.luizcanassa.projetintegrador2.repository;

import br.com.luizcanassa.projetintegrador2.domain.entity.OrderDeliveryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderDeliveryRepository extends JpaRepository<OrderDeliveryEntity, Long> {

    Integer countByCreatedAtBetween(LocalDateTime todayStart, LocalDateTime todayEnd);

    List<OrderDeliveryEntity> findAllByCreatedAtBetween(LocalDateTime todayStart, LocalDateTime todayEnd);
}

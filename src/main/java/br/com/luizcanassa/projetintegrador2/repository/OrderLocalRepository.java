package br.com.luizcanassa.projetintegrador2.repository;

import br.com.luizcanassa.projetintegrador2.domain.entity.OrderDeliveryEntity;
import br.com.luizcanassa.projetintegrador2.domain.entity.OrderLocalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderLocalRepository extends JpaRepository<OrderLocalEntity, Long> {

    Integer countByCreatedAtBetween(LocalDateTime todayStart, LocalDateTime todayEnd);

    List<OrderLocalEntity> findAllByCreatedAtBetween(LocalDateTime todayStart, LocalDateTime todayEnd);

}

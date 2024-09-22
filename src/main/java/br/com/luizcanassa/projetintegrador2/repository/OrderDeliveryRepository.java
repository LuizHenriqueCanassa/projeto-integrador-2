package br.com.luizcanassa.projetintegrador2.repository;

import br.com.luizcanassa.projetintegrador2.domain.entity.OrderDeliveryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDeliveryRepository extends JpaRepository<OrderDeliveryEntity, Long> {
}

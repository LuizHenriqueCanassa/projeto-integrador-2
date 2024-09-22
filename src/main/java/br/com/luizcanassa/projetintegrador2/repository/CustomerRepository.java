package br.com.luizcanassa.projetintegrador2.repository;

import br.com.luizcanassa.projetintegrador2.domain.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {

    boolean existsByDocument(String document);

    Optional<CustomerEntity> findByDocument(String document);
}

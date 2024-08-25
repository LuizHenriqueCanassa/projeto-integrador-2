package br.com.luizcanassa.projetintegrador2.repository;

import br.com.luizcanassa.projetintegrador2.domain.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
}

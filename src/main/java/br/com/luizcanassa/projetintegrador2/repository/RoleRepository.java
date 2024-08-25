package br.com.luizcanassa.projetintegrador2.repository;

import br.com.luizcanassa.projetintegrador2.domain.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {

    @Query("SELECT r FROM RoleEntity r WHERE r.name NOT IN ('ROLE_ROOT')")
    List<RoleEntity> findAllRoles();
}

package br.com.luizcanassa.projetintegrador2.repository;

import br.com.luizcanassa.projetintegrador2.domain.entity.UserEntity;
import br.com.luizcanassa.projetintegrador2.domain.projections.UserProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByUsername(final String username);

    @Query(value = "SELECT " +
            "u.ID as ID," +
            "u.NAME as NAME," +
            "u.USERNAME as USERNAME," +
            "u.ACTIVE as ACTIVE, " +
            "u.CREATED_AT as CREATED_AT " +
            "FROM USERS u " +
            "INNER JOIN USERS_ROLES ur ON ur.user_id = u.id " +
            "INNER JOIN ROLES r ON ur.role_id = r.id " +
            "WHERE r.NAME NOT IN (:roles)", nativeQuery = true)
    List<UserProjection> findAllByRolesNotIn(@Param("roles") List<String> roles);

    @Query(value = "SELECT " +
            "u.ID as ID," +
            "u.NAME as NAME," +
            "u.USERNAME as USERNAME," +
            "u.ACTIVE as ACTIVE, " +
            "u.CREATED_AT as CREATED_AT " +
            "FROM USERS u " +
            "INNER JOIN USERS_ROLES ur ON ur.user_id = u.id " +
            "INNER JOIN ROLES r ON ur.role_id = r.id " , nativeQuery = true)
    List<UserProjection> findAllUsers();

}

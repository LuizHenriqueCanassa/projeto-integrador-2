package br.com.luizcanassa.projetintegrador2.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class UserEntity {

    @Id
    @SequenceGenerator(name = "users_sequence", sequenceName = "SQ_USERS_ID", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "users_sequence")
    private Long id;

    private String name;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_USERS_ID")),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_ROLES_ID"))
    )
    private Set<RoleEntity> roles;
}

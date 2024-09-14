package br.com.luizcanassa.projetintegrador2.domain.entity;

import br.com.luizcanassa.projetintegrador2.domain.enums.OrdersStatusEnum;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "orders")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class OrderEntity {

    @Id
    @SequenceGenerator(name = "orders_sequence", sequenceName = "SQ_ORDERS_ID", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "orders_sequence")
    private Long id;

    @Column(nullable = false, scale = 10, precision = 2)
    private BigDecimal amount;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OrdersStatusEnum status;

    @Column(nullable = false)
    private Boolean paid = Boolean.FALSE;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<OrderItemEntity> orderItems;
}

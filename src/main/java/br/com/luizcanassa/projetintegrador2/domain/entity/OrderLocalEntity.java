package br.com.luizcanassa.projetintegrador2.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders_local")
@ToString(callSuper = true)
public class OrderLocalEntity {

    @Id
    @SequenceGenerator(name = "orders_local_sequence", sequenceName = "SQ_ORDERS_LOCAL_ID", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "orders_local_sequence")
    private Long id;

    @Column(nullable = false)
    private Integer cardControl;

    @ManyToOne
    @JoinColumn(name = "id_order")
    private OrderEntity order;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}

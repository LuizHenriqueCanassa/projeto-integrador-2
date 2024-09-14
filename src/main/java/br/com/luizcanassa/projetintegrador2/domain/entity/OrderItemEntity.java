package br.com.luizcanassa.projetintegrador2.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "order_items")
public class OrderItemEntity {

    @Id
    @SequenceGenerator(name = "order_items_sequence", sequenceName = "SQ_ORDER_ITEMS_ID", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "order_items_sequence")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_order")
    private OrderEntity order;

    @ManyToOne
    @JoinColumn(name = "id_product")
    private ProductEntity product;

    @Column(nullable = false, scale = 10, precision = 2)
    private BigDecimal amount;

    @Column(nullable = false)
    private Integer quantity;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}

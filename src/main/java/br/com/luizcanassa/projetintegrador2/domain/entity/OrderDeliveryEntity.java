package br.com.luizcanassa.projetintegrador2.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders_delivery")
@ToString(callSuper = true)
public class OrderDeliveryEntity {

    @Id
    @SequenceGenerator(name = "orders_delivery_sequence", sequenceName = "SQ_ORDERS_DELIVERY_ID", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "orders_delivery_sequence")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_customer")
    private CustomerEntity customer;

    @ManyToOne
    @JoinColumn(name = "id_order")
    private OrderEntity order;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}

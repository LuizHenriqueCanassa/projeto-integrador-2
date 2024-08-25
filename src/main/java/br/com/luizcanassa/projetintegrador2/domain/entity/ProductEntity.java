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
@Table(name = "products")
public class ProductEntity {

    @Id
    @SequenceGenerator(name = "products_sequence", sequenceName = "SQ_PRODUCTS_ID", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "products_sequence")
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false, scale = 10, precision = 2)
    private BigDecimal price;

    private String description;

    @Column(nullable = false)
    private Boolean active = Boolean.TRUE;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "id_category")
    private CategoryEntity category;
}

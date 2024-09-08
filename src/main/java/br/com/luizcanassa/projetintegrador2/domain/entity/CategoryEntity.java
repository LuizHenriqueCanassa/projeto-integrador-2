package br.com.luizcanassa.projetintegrador2.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "categories")
public class CategoryEntity {

    @Id
    @SequenceGenerator(name = "categories_sequence", sequenceName = "SQ_CATEGORIES_ID", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "categories_sequence")
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private Boolean active = Boolean.TRUE;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "category", fetch = FetchType.EAGER)
    private List<ProductEntity> products;

}

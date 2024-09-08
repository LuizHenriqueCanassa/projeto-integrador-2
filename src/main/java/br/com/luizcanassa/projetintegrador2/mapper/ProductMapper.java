package br.com.luizcanassa.projetintegrador2.mapper;

import br.com.luizcanassa.projetintegrador2.domain.dto.product.ProductCreateDTO;
import br.com.luizcanassa.projetintegrador2.domain.dto.product.ProductDTO;
import br.com.luizcanassa.projetintegrador2.domain.dto.product.ProductEditDTO;
import br.com.luizcanassa.projetintegrador2.domain.entity.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", uses = {CategoryMapper.class}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductMapper {

    @Mapping(source = "category.name", target = "category")
    ProductDTO toProductDTO(ProductEntity product);

    List<ProductDTO> toProductDTOList(List<ProductEntity> productEntities);

    @Mapping(source = "category.id", target = "categoryId")
    ProductEditDTO toProductEditDTO(ProductEntity product);

    default ProductEntity toProductEntity(ProductEditDTO productDTO, ProductEntity productEntity) {
        productEntity.setId(productDTO.getId());
        productEntity.setName(productDTO.getName());
        productEntity.setDescription(productDTO.getDescription());
        productEntity.setPrice(productDTO.getPrice());

        return productEntity;
    }

    ProductEntity toProductEntity(ProductCreateDTO productDTO);
}

package br.com.luizcanassa.projetintegrador2.mapper;

import br.com.luizcanassa.projetintegrador2.domain.dto.ProductDTO;
import br.com.luizcanassa.projetintegrador2.domain.entity.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {CategoryMapper.class})
public interface ProductMapper {

    @Mapping(source = "category.name", target = "category")
    ProductDTO toProductDTO(ProductEntity product);

    List<ProductDTO> toProductDTOList(List<ProductEntity> productEntities);
}

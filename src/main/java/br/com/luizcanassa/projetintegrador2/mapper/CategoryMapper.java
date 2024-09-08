package br.com.luizcanassa.projetintegrador2.mapper;

import br.com.luizcanassa.projetintegrador2.domain.dto.CategoryCreateDTO;
import br.com.luizcanassa.projetintegrador2.domain.dto.CategoryDTO;
import br.com.luizcanassa.projetintegrador2.domain.dto.CategoryEditDTO;
import br.com.luizcanassa.projetintegrador2.domain.entity.CategoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CategoryMapper {

    CategoryEntity toCategoryEntity(CategoryCreateDTO categoryDTO);

    CategoryDTO toCategoryDTO(CategoryEntity categoryEntity);

    CategoryEditDTO toCategoryEditDTO(CategoryEntity categoryEntity);

}

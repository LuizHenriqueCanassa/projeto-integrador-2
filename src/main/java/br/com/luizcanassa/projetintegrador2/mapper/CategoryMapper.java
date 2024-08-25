package br.com.luizcanassa.projetintegrador2.mapper;

import br.com.luizcanassa.projetintegrador2.domain.dto.CategoryCreateDTO;
import br.com.luizcanassa.projetintegrador2.domain.dto.CategoryDTO;
import br.com.luizcanassa.projetintegrador2.domain.dto.CategoryEditDTO;
import br.com.luizcanassa.projetintegrador2.domain.entity.CategoryEntity;
import lombok.experimental.UtilityClass;

@UtilityClass
public final class CategoryMapper {

    public static CategoryEntity toCategoryEntity(final CategoryCreateDTO categoryCreateDTO) {
        final var category = new CategoryEntity();

        category.setName(categoryCreateDTO.getName());

        return category;
    }

    public static CategoryDTO toCategoryDTO(final CategoryEntity categoryEntity) {
        final var categoryDTO = new CategoryDTO();

        categoryDTO.setId(categoryEntity.getId());
        categoryDTO.setName(categoryEntity.getName());
        categoryDTO.setActive(categoryEntity.getActive());
        categoryDTO.setCreatedAt(categoryEntity.getCreatedAt());

        return categoryDTO;
    }

    public static CategoryEditDTO toCategoryEditDTO(final CategoryEntity categoryEntity) {
        final var categoryEditDTO = new CategoryEditDTO();

        categoryEditDTO.setId(categoryEntity.getId());
        categoryEditDTO.setName(categoryEntity.getName());

        return categoryEditDTO;
    }
}

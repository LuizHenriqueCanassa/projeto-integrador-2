package br.com.luizcanassa.projetintegrador2.service.impl;

import br.com.luizcanassa.projetintegrador2.domain.dto.CategoryCreateDTO;
import br.com.luizcanassa.projetintegrador2.domain.dto.CategoryDTO;
import br.com.luizcanassa.projetintegrador2.domain.dto.CategoryEditDTO;
import br.com.luizcanassa.projetintegrador2.domain.entity.CategoryEntity;
import br.com.luizcanassa.projetintegrador2.exception.CategoryNotFoundException;
import br.com.luizcanassa.projetintegrador2.exception.DeleteCategoryWithProductsException;
import br.com.luizcanassa.projetintegrador2.mapper.CategoryMapper;
import br.com.luizcanassa.projetintegrador2.repository.CategoryRepository;
import br.com.luizcanassa.projetintegrador2.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(final CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<CategoryDTO> findAll() {
        return categoryRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(CategoryEntity::getId))
                .map(CategoryMapper::toCategoryDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryEditDTO findByIdToEdit(final Long id) throws CategoryNotFoundException {
        final var category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Categoria não encontrada!"));

        return CategoryMapper.toCategoryEditDTO(category);
    }

    @Override
    public void createCategory(final CategoryCreateDTO categoryCreateDTO) {
        categoryRepository.saveAndFlush(CategoryMapper.toCategoryEntity(categoryCreateDTO));
    }

    @Override
    public void changeStatus(final Long id) throws CategoryNotFoundException {
        final var category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Categoria não encontrada!"));

        category.setActive(!category.getActive());

        categoryRepository.saveAndFlush(category);
    }

    @Override
    public void updateCategory(final Long id, final CategoryEditDTO categoryEditDTO) throws CategoryNotFoundException {
        final var category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Categoria não encontrada!"));

        category.setName(categoryEditDTO.getName());

        categoryRepository.saveAndFlush(category);
    }

    @Override
    public void deleteCategory(final Long id) throws CategoryNotFoundException, DeleteCategoryWithProductsException {
        final var category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Categoria não encontrada!"));

        if (category.getProducts().stream().findAny().isPresent()) {
            throw new DeleteCategoryWithProductsException("Essa categoria possui produtos cadastrados e não pode ser excluida!");
        }

        categoryRepository.delete(category);
    }
}

package br.com.luizcanassa.projetintegrador2.service;

import br.com.luizcanassa.projetintegrador2.domain.dto.category.CategoryCreateDTO;
import br.com.luizcanassa.projetintegrador2.domain.dto.category.CategoryDTO;
import br.com.luizcanassa.projetintegrador2.domain.dto.category.CategoryEditDTO;
import br.com.luizcanassa.projetintegrador2.exception.CategoryNotFoundException;
import br.com.luizcanassa.projetintegrador2.exception.DeleteCategoryWithProductsException;

import java.util.List;

public interface CategoryService {

    List<CategoryDTO> findAll();

    List<CategoryDTO> findAllActiveCategories();

    CategoryEditDTO findByIdToEdit(Long id) throws CategoryNotFoundException;

    void createCategory(CategoryCreateDTO categoryCreateDTO);

    void changeStatus(Long id) throws CategoryNotFoundException;

    void updateCategory(Long id, CategoryEditDTO categoryEditDTO) throws CategoryNotFoundException;

    void deleteCategory(Long id) throws CategoryNotFoundException, DeleteCategoryWithProductsException;
}

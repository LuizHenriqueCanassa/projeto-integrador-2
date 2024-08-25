package br.com.luizcanassa.projetintegrador2.service;

import br.com.luizcanassa.projetintegrador2.domain.dto.CategoryCreateDTO;
import br.com.luizcanassa.projetintegrador2.domain.dto.CategoryDTO;
import br.com.luizcanassa.projetintegrador2.domain.dto.CategoryEditDTO;
import br.com.luizcanassa.projetintegrador2.exception.CategoryNotFoundException;
import br.com.luizcanassa.projetintegrador2.exception.DeleteCategoryWithProductsException;

import java.util.List;

public interface CategoryService {

    List<CategoryDTO> findAll();

    CategoryEditDTO findByIdToEdit(Long id) throws CategoryNotFoundException;

    void createCategory(CategoryCreateDTO categoryCreateDTO);

    void changeStatus(Long id) throws CategoryNotFoundException;

    void updateCategory(Long id, CategoryEditDTO categoryEditDTO) throws CategoryNotFoundException;

    void deleteCategory(Long id) throws CategoryNotFoundException, DeleteCategoryWithProductsException;
}

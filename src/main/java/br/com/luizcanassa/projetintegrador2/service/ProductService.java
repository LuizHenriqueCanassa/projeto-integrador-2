package br.com.luizcanassa.projetintegrador2.service;

import br.com.luizcanassa.projetintegrador2.domain.dto.product.ProductCreateDTO;
import br.com.luizcanassa.projetintegrador2.domain.dto.product.ProductDTO;
import br.com.luizcanassa.projetintegrador2.domain.dto.product.ProductEditDTO;
import br.com.luizcanassa.projetintegrador2.exception.CategoryNotFoundException;
import br.com.luizcanassa.projetintegrador2.exception.ProductNotFoundException;

import java.util.List;

public interface ProductService {

    List<ProductDTO> findAll();

    ProductEditDTO findByIdToEdit(Long id) throws ProductNotFoundException;

    void create(ProductCreateDTO productCreateDTO) throws CategoryNotFoundException;

    void edit(ProductEditDTO productEditDTO) throws ProductNotFoundException, CategoryNotFoundException;

    void changeStatus(Long id) throws ProductNotFoundException;

    void delete(Long id) throws ProductNotFoundException;
}

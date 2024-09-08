package br.com.luizcanassa.projetintegrador2.service;

import br.com.luizcanassa.projetintegrador2.domain.dto.product.ProductCreateDTO;
import br.com.luizcanassa.projetintegrador2.domain.dto.product.ProductDTO;
import br.com.luizcanassa.projetintegrador2.exception.CategoryNotFoundException;

import java.util.List;

public interface ProductService {

    List<ProductDTO> findAll();

    void create(ProductCreateDTO productCreateDTO) throws CategoryNotFoundException;
}

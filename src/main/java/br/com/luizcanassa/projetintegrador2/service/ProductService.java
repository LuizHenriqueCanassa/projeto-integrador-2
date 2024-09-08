package br.com.luizcanassa.projetintegrador2.service;

import br.com.luizcanassa.projetintegrador2.domain.dto.ProductDTO;

import java.util.List;

public interface ProductService {
    List<ProductDTO> findAll();
}

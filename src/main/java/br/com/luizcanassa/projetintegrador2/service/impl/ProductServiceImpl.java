package br.com.luizcanassa.projetintegrador2.service.impl;

import br.com.luizcanassa.projetintegrador2.domain.dto.ProductDTO;
import br.com.luizcanassa.projetintegrador2.mapper.ProductMapper;
import br.com.luizcanassa.projetintegrador2.repository.ProductRepository;
import br.com.luizcanassa.projetintegrador2.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final ProductMapper productMapper;

    public ProductServiceImpl(final ProductRepository productRepository, final ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    @Override
    public List<ProductDTO> findAll() {
        return productMapper.toProductDTOList(productRepository.findAll());
    }
}

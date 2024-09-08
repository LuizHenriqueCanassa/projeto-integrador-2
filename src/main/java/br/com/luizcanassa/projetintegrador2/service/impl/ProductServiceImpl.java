package br.com.luizcanassa.projetintegrador2.service.impl;

import br.com.luizcanassa.projetintegrador2.domain.dto.product.ProductCreateDTO;
import br.com.luizcanassa.projetintegrador2.domain.dto.product.ProductDTO;
import br.com.luizcanassa.projetintegrador2.domain.entity.ProductEntity;
import br.com.luizcanassa.projetintegrador2.exception.CategoryNotFoundException;
import br.com.luizcanassa.projetintegrador2.exception.ProductNotFoundException;
import br.com.luizcanassa.projetintegrador2.mapper.ProductMapper;
import br.com.luizcanassa.projetintegrador2.repository.CategoryRepository;
import br.com.luizcanassa.projetintegrador2.repository.ProductRepository;
import br.com.luizcanassa.projetintegrador2.service.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final CategoryRepository categoryRepository;

    private final ProductMapper productMapper;

    public ProductServiceImpl(final ProductRepository productRepository, final CategoryRepository categoryRepository, final ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.productMapper = productMapper;
    }

    @Override
    public List<ProductDTO> findAll() {
        return productMapper.toProductDTOList(productRepository.findAll());
    }

    @Override
    public void create(final ProductCreateDTO productCreateDTO) throws CategoryNotFoundException {
        final var productToCreate = productMapper.toProductEntity(productCreateDTO);
        productToCreate.setCategory(
                categoryRepository.findById(productCreateDTO.getCategoryId())
                        .orElseThrow(() -> new CategoryNotFoundException("Categoria não encontrada!"))
        );

        productRepository.save(productToCreate);
    }

    @Override
    public void changeStatus(final Long id) throws ProductNotFoundException {
        final var product = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Produto não encontrado!"));

        product.setActive(!product.getActive());

        productRepository.saveAndFlush(product);
    }

    @Override
    public void delete(final Long id) throws ProductNotFoundException {
        final var productToDelete = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Produto não encontrado!"));

        productRepository.delete(productToDelete);
    }
}

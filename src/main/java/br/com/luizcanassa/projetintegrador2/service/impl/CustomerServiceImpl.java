package br.com.luizcanassa.projetintegrador2.service.impl;

import br.com.luizcanassa.projetintegrador2.domain.dto.customer.CustomerCreateDTO;
import br.com.luizcanassa.projetintegrador2.domain.dto.customer.CustomerDTO;
import br.com.luizcanassa.projetintegrador2.domain.dto.customer.CustomerEditDTO;
import br.com.luizcanassa.projetintegrador2.domain.entity.CustomerEntity;
import br.com.luizcanassa.projetintegrador2.exception.CustomerAlreadyExistException;
import br.com.luizcanassa.projetintegrador2.exception.CustomerNotFoundException;
import br.com.luizcanassa.projetintegrador2.mapper.AddressMapper;
import br.com.luizcanassa.projetintegrador2.mapper.CustomerMapper;
import br.com.luizcanassa.projetintegrador2.repository.AddressRepository;
import br.com.luizcanassa.projetintegrador2.repository.CustomerRepository;
import br.com.luizcanassa.projetintegrador2.service.CustomerService;
import br.com.luizcanassa.projetintegrador2.utils.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    private final AddressRepository addressRepository;

    private final CustomerMapper customerMapper;

    private final AddressMapper addressMapper;

    public CustomerServiceImpl(final CustomerRepository customerRepository, final AddressRepository addressRepository, final CustomerMapper customerMapper, final AddressMapper addressMapper) {
        this.customerRepository = customerRepository;
        this.addressRepository = addressRepository;
        this.customerMapper = customerMapper;
        this.addressMapper = addressMapper;
    }

    @Override
    public List<CustomerDTO> findAll() {
        return customerMapper.toCustomersDTOList(customerRepository.findAll());
    }

    @Override
    public void create(final CustomerCreateDTO customerCreateDTO) {
        if (customerRepository.existsByDocument(StringUtils.removeDocumentMask(customerCreateDTO.getDocument()))) {
            throw new CustomerAlreadyExistException("Já existe um cliente com esse documento cadastrado");
        }

        var customerCreate = customerRepository.save(customerMapper.toCustomerEntity(customerCreateDTO));

        var addressToCreate = addressMapper.toAddressEntity(customerCreateDTO.getAddress());
        addressToCreate.setCustomer(customerCreate);

        addressRepository.save(addressToCreate);
    }

    @Override
    public CustomerEditDTO findById(final Long id) {
        final var customerEntity = customerRepository.findById(id).orElseThrow(() -> new CustomerNotFoundException("Cliente não encontrado."));

        var customerToEdit = customerMapper.toCustomerEditDTO(customerEntity);

        customerToEdit.setAddress(addressMapper.toAddressEditDTO(customerEntity.getAddresses().get(0)));

        return customerToEdit;
    }

    @Override
    public void update(final CustomerEditDTO customerEditDTO) {
        final var customerToEdit = customerRepository.findById(customerEditDTO.getId())
                .orElseThrow(() -> new CustomerNotFoundException("Cliente não encontrado."));

        if (!customerToEdit.getDocument().equals(StringUtils.removeDocumentMask(customerEditDTO.getDocument()))) {
            if (customerRepository.existsByDocument(StringUtils.removeDocumentMask(customerEditDTO.getDocument()))) {
                throw new CustomerAlreadyExistException("Já existe outro cliente cadastrado com esse documento.");
            }
        }

        customerRepository.save(customerMapper.toCustomerEntity(customerEditDTO, customerToEdit));
        addressRepository.save(addressMapper.toAddressEntity(customerEditDTO.getAddress(), customerToEdit.getAddresses().get(0)));
    }
}

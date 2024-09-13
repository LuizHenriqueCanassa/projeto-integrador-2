package br.com.luizcanassa.projetintegrador2.mapper;

import br.com.luizcanassa.projetintegrador2.domain.dto.customer.CustomerCreateDTO;
import br.com.luizcanassa.projetintegrador2.domain.dto.customer.CustomerDTO;
import br.com.luizcanassa.projetintegrador2.domain.entity.CustomerEntity;
import br.com.luizcanassa.projetintegrador2.utils.StringUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.text.ParseException;
import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CustomerMapper {

    @Mapping(source = "name", target = "name")
    @Mapping(source = "document", target = "document", qualifiedByName = "addMaskDocument")
    @Mapping(source = "mobilePhone", target = "mobilePhone", qualifiedByName = "addMaskMobilePhone")
    CustomerDTO toCustomerDTO(CustomerEntity customerEntity);

    List<CustomerDTO> toCustomersDTOList(List<CustomerEntity> customers);

    @Mapping(source = "name", target = "name")
    @Mapping(source = "document", target = "document", qualifiedByName = "removeMaskDocument")
    @Mapping(source = "mobilePhone", target = "mobilePhone", qualifiedByName = "removeMaskMobilePhone")
    @Mapping(target = "addresses", ignore = true)
    CustomerEntity toCustomerEntity(CustomerCreateDTO customerCreateDTO);

    @Named("removeMaskDocument")
    default String removeMaskDocument(String document) {
        return StringUtils.removeDocumentMask(document);
    }

    @Named("removeMaskMobilePhone")
    default String removeMaskMobilePhone(String mobilePhone) {
        return StringUtils.removeMobilePhoneMask(mobilePhone);
    }

    @Named("addMaskDocument")
    default String addMaskDocument(String document) throws ParseException {
        return StringUtils.addDocumentMask(document);
    }

    @Named("addMaskMobilePhone")
    default String addMaskMobilePhone(String mobilePhone) throws ParseException {
        return StringUtils.addMobilePhoneMask(mobilePhone);
    }
}

package br.com.luizcanassa.projetintegrador2.mapper;

import br.com.luizcanassa.projetintegrador2.domain.dto.customer.CustomerCreateDTO;
import br.com.luizcanassa.projetintegrador2.domain.dto.customer.CustomerEditDTO;
import br.com.luizcanassa.projetintegrador2.domain.entity.AddressEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AddressMapper {

    @Mapping(source = "streetName", target = "streetName")
    @Mapping(source = "number", target = "number")
    @Mapping(source = "city", target = "city")
    @Mapping(source = "district", target = "district")
    AddressEntity toAddressEntity(CustomerCreateDTO.AddressDTO addressDTO);

    CustomerEditDTO.AddressEditDTO toAddressEditDTO(AddressEntity addressEntity);

    default AddressEntity toAddressEntity(CustomerEditDTO.AddressEditDTO addressEditDTO, AddressEntity addressEntity) {
        addressEntity.setStreetName(addressEditDTO.getStreetName());
        addressEntity.setNumber(addressEditDTO.getNumber());
        addressEntity.setCity(addressEditDTO.getCity());
        addressEntity.setDistrict(addressEditDTO.getDistrict());

        return addressEntity;
    }
}

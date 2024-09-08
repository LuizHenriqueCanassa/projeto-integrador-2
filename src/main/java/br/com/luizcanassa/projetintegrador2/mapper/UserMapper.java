package br.com.luizcanassa.projetintegrador2.mapper;

import br.com.luizcanassa.projetintegrador2.domain.dto.UserCreateDTO;
import br.com.luizcanassa.projetintegrador2.domain.dto.UserDTO;
import br.com.luizcanassa.projetintegrador2.domain.dto.UserEditDTO;
import br.com.luizcanassa.projetintegrador2.domain.entity.RoleEntity;
import br.com.luizcanassa.projetintegrador2.domain.entity.UserEntity;
import lombok.experimental.UtilityClass;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Set;

@Mapper(componentModel = "spring", uses = {RoleMapper.class})
public interface UserMapper {

    @Mapping(target = "isRoot", source = "roles", qualifiedByName = "isRoot")
    UserDTO toUserDTO(UserEntity userEntity);

    @Mapping(target = "roleId", source = "roles", qualifiedByName = "roleId")
    UserEditDTO toUserEditDTO(UserEntity userEntity);

    UserEntity createDTOToUserEntity(UserCreateDTO userCreateDTO);

    @Named("isRoot")
    default Boolean isRoot(Set<RoleEntity> roles) {
       return roles.stream().anyMatch(roleEntity -> roleEntity.getName().equals("ROLE_ROOT"));
    }

    @Named("roleId")
    default Long roleId(Set<RoleEntity> roles) {
        return roles.stream().findFirst().get().getId();
    }
}

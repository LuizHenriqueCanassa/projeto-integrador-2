package br.com.luizcanassa.projetintegrador2.mapper;

import br.com.luizcanassa.projetintegrador2.domain.dto.UserCreateDTO;
import br.com.luizcanassa.projetintegrador2.domain.dto.UserDTO;
import br.com.luizcanassa.projetintegrador2.domain.entity.RoleEntity;
import br.com.luizcanassa.projetintegrador2.domain.entity.UserEntity;
import lombok.experimental.UtilityClass;

import java.util.Set;

@UtilityClass
public final class UserMapper {

    public static UserDTO userToUserDTO (final UserEntity user) {
         return new UserDTO(
                 user.getId(),
                 user.getName(),
                 user.getUsername(),
                 user.getActive(),
                 isUserRoot(user.getRoles()),
                 user.getCreatedAt()
         );
    }

    public static UserEntity userCreateDTOToUserEntity(final UserCreateDTO userCreateDTO, final String passwordEncoded) {
        final var user = new UserEntity();

        user.setId(null);
        user.setName(userCreateDTO.getName());
        user.setUsername(userCreateDTO.getUsername());
        user.setPassword(passwordEncoded);

        return user;
    }

    private static Boolean isUserRoot(final Set<RoleEntity> roles) {
        return roles.stream().anyMatch(roleEntity -> roleEntity.getName().equals("ROLE_ROOT"));
    }
}

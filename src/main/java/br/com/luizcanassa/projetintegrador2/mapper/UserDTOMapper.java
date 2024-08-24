package br.com.luizcanassa.projetintegrador2.mapper;

import br.com.luizcanassa.projetintegrador2.domain.dto.UserDTO;
import br.com.luizcanassa.projetintegrador2.domain.entity.RoleEntity;
import br.com.luizcanassa.projetintegrador2.domain.entity.UserEntity;
import lombok.experimental.UtilityClass;

import java.util.Set;

@UtilityClass
public final class UserDTOMapper {

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

    private static Boolean isUserRoot(final Set<RoleEntity> roles) {
        return roles.stream().anyMatch(roleEntity -> roleEntity.getName().equals("ROLE_ROOT"));
    }
}

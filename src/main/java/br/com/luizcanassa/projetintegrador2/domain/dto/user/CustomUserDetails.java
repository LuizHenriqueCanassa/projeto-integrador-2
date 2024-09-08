package br.com.luizcanassa.projetintegrador2.domain.dto.user;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
public class CustomUserDetails extends User {

    private final Long id;

    private final String displayName;

    private final Boolean userActive;

    public CustomUserDetails(
            String displayName,
            String username,
            String password,
            Collection<? extends GrantedAuthority> authorities,
            final Long id,
            Boolean userActive
    ) {
        super(username, password, userActive, true, true, true, authorities);
        this.displayName = displayName;
        this.id = id;
        this.userActive = userActive;
    }

}

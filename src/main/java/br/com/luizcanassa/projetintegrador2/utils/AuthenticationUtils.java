package br.com.luizcanassa.projetintegrador2.utils;

import br.com.luizcanassa.projetintegrador2.domain.dto.CustomUserDetails;
import lombok.experimental.UtilityClass;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public final class AuthenticationUtils {

    public static List<String> getUserAuthorities() {
        return authentication().getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
    }

    public static String getDisplayName() {
        return authentication().getDisplayName();
    }

    public static String getUsername() {
        return authentication().getUsername();
    }

    public static Boolean isRoot() {
        return getUserAuthorities().contains("ROLE_ROOT");
    }

    public static Long getId() {
        return authentication().getId();
    }

    private CustomUserDetails authentication() {
        return (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}

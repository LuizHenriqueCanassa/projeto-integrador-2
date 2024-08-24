package br.com.luizcanassa.projetintegrador2.controllers.dashboard;

import br.com.luizcanassa.projetintegrador2.domain.dto.CustomUserDetails;
import br.com.luizcanassa.projetintegrador2.domain.enums.PageEnum;
import br.com.luizcanassa.projetintegrador2.service.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.stream.Collectors;

@Controller
@RequestMapping("/dashboard/users")
public class UsersController {

    private final UserService userService;

    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String index(final Model model, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        final var authorities = customUserDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        model.addAttribute("users", userService.findAll());
        model.addAttribute("page", PageEnum.USERS);
        model.addAttribute("displayName", customUserDetails.getDisplayName());
        model.addAttribute("roles", authorities);

        return "dashboard/users/index";
    }
}
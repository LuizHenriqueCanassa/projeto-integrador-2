package br.com.luizcanassa.projetintegrador2.controllers.dashboard;

import br.com.luizcanassa.projetintegrador2.domain.dto.CustomUserDetails;
import br.com.luizcanassa.projetintegrador2.domain.enums.PageEnum;
import br.com.luizcanassa.projetintegrador2.utils.AuthenticationUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.stream.Collectors;

@Controller
@RequestMapping(path = {"/dashboard", "/"})
public class DashboardController {

    @GetMapping
    public String index(final Model model) {

        model.addAttribute("page", PageEnum.DASHBOARD);
        model.addAttribute("displayName", AuthenticationUtils.getDisplayName());
        model.addAttribute("roles", AuthenticationUtils.getUserAuthorities());

        return "dashboard/index";
    }
}

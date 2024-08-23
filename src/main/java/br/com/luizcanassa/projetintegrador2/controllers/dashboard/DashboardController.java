package br.com.luizcanassa.projetintegrador2.controllers.dashboard;

import br.com.luizcanassa.projetintegrador2.domain.dto.CustomUserDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    @GetMapping
    public String index(final Model model, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        model.addAttribute("title", "Lanchonete - Página Inicial");
        model.addAttribute("currentPage", "Página Inicial");
        model.addAttribute("displayName", customUserDetails.getDisplayName());
        model.addAttribute("roles", customUserDetails.getAuthorities());
        return "dashboard/index";
    }
}

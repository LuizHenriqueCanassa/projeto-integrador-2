package br.com.luizcanassa.projetintegrador2.controllers.dashboard;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    @GetMapping
    public String index(final Model model) {
        model.addAttribute("title", "Lanchonete - Página Inicial");
        model.addAttribute("currentPage", "Página Inicial");
        return "dashboard/index";
    }
}

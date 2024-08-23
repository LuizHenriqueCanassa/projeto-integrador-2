package br.com.luizcanassa.projetintegrador2.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class LoginController {

    @GetMapping
    public String login(final Model model) {
        model.addAttribute("title", "Lanchonete - Login");
        return "login/index";
    }
}

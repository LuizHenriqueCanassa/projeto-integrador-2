package br.com.luizcanassa.projetintegrador2.controllers.dashboard;

import br.com.luizcanassa.projetintegrador2.domain.enums.PageEnum;
import br.com.luizcanassa.projetintegrador2.service.ProductService;
import br.com.luizcanassa.projetintegrador2.utils.AuthenticationUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/dashboard/products")
public class ProductsController {

    private final ProductService productService;

    public ProductsController(final ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String index(final Model model) {

        model.addAttribute("page", PageEnum.PRODUCTS);
        model.addAttribute("displayName", AuthenticationUtils.getDisplayName());
        model.addAttribute("roles", AuthenticationUtils.getUserAuthorities());
        model.addAttribute("products", productService.findAll());

        return "dashboard/products/index";
    }

    @GetMapping("/create")
    public String create(final Model model) {

        model.addAttribute("page", PageEnum.PRODUCTS);
        model.addAttribute("displayName", AuthenticationUtils.getDisplayName());
        model.addAttribute("roles", AuthenticationUtils.getUserAuthorities());

        return "dashboard/products/create";
    }
}

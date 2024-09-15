package br.com.luizcanassa.projetintegrador2.controllers.dashboard.order;

import br.com.luizcanassa.projetintegrador2.domain.dto.category.CategoryDTO;
import br.com.luizcanassa.projetintegrador2.domain.dto.order.local.CreateOrderLocalDTO;
import br.com.luizcanassa.projetintegrador2.domain.dto.product.ProductDTO;
import br.com.luizcanassa.projetintegrador2.domain.enums.PageEnum;
import br.com.luizcanassa.projetintegrador2.service.CategoryService;
import br.com.luizcanassa.projetintegrador2.service.ProductService;
import br.com.luizcanassa.projetintegrador2.utils.AuthenticationUtils;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/dashboard/orders/local")
public class OrdersLocalController {

    private final ProductService productService;

    private final CategoryService categoryService;

    public OrdersLocalController(final ProductService productService, final CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping
    public String index(final Model model) {
        model.addAttribute("page", PageEnum.ORDERS_LOCAL);
        model.addAttribute("displayName", AuthenticationUtils.getDisplayName());
        model.addAttribute("roles", AuthenticationUtils.getUserAuthorities());

        return "dashboard/orders/local/index";
    }

    @GetMapping("/create")
    public String create(
            @ModelAttribute(name = "orderLocalCreate") final CreateOrderLocalDTO createOrderLocalDTO,
            final Model model
    ) {
        final var products = productService.findAllActiveProducts();
        final var categories = categoryService.findAllActiveCategories()
                .stream()
                .filter(categoryDTO -> products.stream().anyMatch(productDTO -> categoryDTO.getName().equals(productDTO.getCategory()))).collect(Collectors.toList());

        model.addAttribute("page", PageEnum.ORDERS_LOCAL_CREATE);
        model.addAttribute("displayName", AuthenticationUtils.getDisplayName());
        model.addAttribute("roles", AuthenticationUtils.getUserAuthorities());
        model.addAttribute("products", products);
        model.addAttribute("categories", categories);

        return "dashboard/orders/local/create";
    }

    @PostMapping("/create")
    public String createAction(
            @ModelAttribute(name = "orderLocalCreate") @Valid final CreateOrderLocalDTO createOrderLocalDTO,
            final Model model,
            final BindingResult bindingResult
    ) {
        model.addAttribute("page", PageEnum.ORDERS_LOCAL_CREATE);
        model.addAttribute("displayName", AuthenticationUtils.getDisplayName());
        model.addAttribute("roles", AuthenticationUtils.getUserAuthorities());

        if (bindingResult.hasErrors()) {
            return "dashboard/orders/local/create";
        }

        System.out.println(createOrderLocalDTO);

        return "";
    }
}

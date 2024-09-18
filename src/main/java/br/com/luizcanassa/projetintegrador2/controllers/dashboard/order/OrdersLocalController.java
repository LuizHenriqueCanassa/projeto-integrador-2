package br.com.luizcanassa.projetintegrador2.controllers.dashboard.order;

import br.com.luizcanassa.projetintegrador2.domain.dto.order.local.CreateOrderLocalDTO;
import br.com.luizcanassa.projetintegrador2.domain.enums.PageEnum;
import br.com.luizcanassa.projetintegrador2.service.CategoryService;
import br.com.luizcanassa.projetintegrador2.service.OrderService;
import br.com.luizcanassa.projetintegrador2.service.ProductService;
import br.com.luizcanassa.projetintegrador2.utils.AuthenticationUtils;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping("/dashboard/orders/local")
public class OrdersLocalController {

    private final ProductService productService;

    private final CategoryService categoryService;

    private final OrderService orderService;

    public OrdersLocalController(final ProductService productService, final CategoryService categoryService, final OrderService orderService) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.orderService = orderService;
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

        try {
            orderService.create(createOrderLocalDTO);

            return "redirect:/dashboard/orders/local?createOrderLocalSuccess=true";
        } catch (Exception e) {
            log.error(e.getMessage());
            return "redirect:/dashboard/orders/local?createOrderLocalError=unknown-error";
        }
    }
}

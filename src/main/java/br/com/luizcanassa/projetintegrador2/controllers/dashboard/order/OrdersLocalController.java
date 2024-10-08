package br.com.luizcanassa.projetintegrador2.controllers.dashboard.order;

import br.com.luizcanassa.projetintegrador2.domain.dto.order.local.CreateOrderLocalDTO;
import br.com.luizcanassa.projetintegrador2.domain.dto.order.local.OrderLocalEditDTO;
import br.com.luizcanassa.projetintegrador2.domain.enums.OrdersStatusEnum;
import br.com.luizcanassa.projetintegrador2.domain.enums.PageEnum;
import br.com.luizcanassa.projetintegrador2.exception.ProductNotFoundException;
import br.com.luizcanassa.projetintegrador2.service.OrderLocalService;
import br.com.luizcanassa.projetintegrador2.service.ProductService;
import br.com.luizcanassa.projetintegrador2.utils.AuthenticationUtils;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("/dashboard/orders/local")
public class OrdersLocalController {

    private final ProductService productService;

    private final OrderLocalService orderLocalService;

    public OrdersLocalController(final ProductService productService, final OrderLocalService orderLocalService) {
        this.productService = productService;
        this.orderLocalService = orderLocalService;
    }

    @GetMapping
    public String index(final Model model) {
        model.addAttribute("page", PageEnum.ORDERS_LOCAL);
        model.addAttribute("displayName", AuthenticationUtils.getDisplayName());
        model.addAttribute("roles", AuthenticationUtils.getUserAuthorities());
        model.addAttribute("orders", orderLocalService.findAll());

        return "dashboard/orders/local/index";
    }

    @GetMapping("/create")
    public String create(
            @ModelAttribute(name = "orderLocalCreate") final CreateOrderLocalDTO createOrderLocalDTO,
            final Model model
    ) {
        model.addAttribute("page", PageEnum.ORDERS_LOCAL_CREATE);
        model.addAttribute("displayName", AuthenticationUtils.getDisplayName());
        model.addAttribute("roles", AuthenticationUtils.getUserAuthorities());
        model.addAttribute("products", productService.findAllActiveProducts());

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
            orderLocalService.create(createOrderLocalDTO);

            return "redirect:/dashboard/orders/local?createOrderLocalSuccess=true";
        } catch (ProductNotFoundException e) {
            log.error(e.getMessage());
            return "redirect:/dashboard/orders/local?createOrderLocalError=product-not-found";
        } catch (Exception e) {
            log.error(e.getMessage());
            return "redirect:/dashboard/orders/local?createOrderLocalError=unknown-error";
        }
    }

    @GetMapping("/details/{id}")
    public String details(@PathVariable("id") final Long id, Model model) {
        model.addAttribute("page", PageEnum.ORDERS_LOCAL_DETAILS);
        model.addAttribute("displayName", AuthenticationUtils.getDisplayName());
        model.addAttribute("roles", AuthenticationUtils.getUserAuthorities());
        model.addAttribute("orderDetails", orderLocalService.findById(id));
        model.addAttribute("orderStatus", OrdersStatusEnum.getLocalStatus());

        return "dashboard/orders/local/details";
    }

    @PostMapping("/details/{id}/edit-status")
    public String editStatusAction(
            @PathVariable("id") final Long id,
            final Model model,
            @ModelAttribute final OrderLocalEditDTO orderLocalEditDTO
            ) {
        model.addAttribute("page", PageEnum.ORDERS_LOCAL_DETAILS);
        model.addAttribute("displayName", AuthenticationUtils.getDisplayName());
        model.addAttribute("roles", AuthenticationUtils.getUserAuthorities());

        orderLocalService.editStatus(id, orderLocalEditDTO);

        return "redirect:/dashboard/orders/local";
    }
}

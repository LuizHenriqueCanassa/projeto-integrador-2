package br.com.luizcanassa.projetintegrador2.controllers.dashboard.order;

import br.com.luizcanassa.projetintegrador2.domain.dto.order.delivery.CreateOrderDeliveryDTO;
import br.com.luizcanassa.projetintegrador2.domain.enums.OrdersStatusEnum;
import br.com.luizcanassa.projetintegrador2.domain.enums.PageEnum;
import br.com.luizcanassa.projetintegrador2.exception.CustomerNotFoundException;
import br.com.luizcanassa.projetintegrador2.exception.ProductNotFoundException;
import br.com.luizcanassa.projetintegrador2.service.OrderDeliveryService;
import br.com.luizcanassa.projetintegrador2.service.ProductService;
import br.com.luizcanassa.projetintegrador2.utils.AuthenticationUtils;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Slf4j
@Controller
@RequestMapping("/dashboard/orders/delivery")
public class OrdersDeliveryController {

    private final ProductService productService;

    private final OrderDeliveryService orderDeliveryService;

    public OrdersDeliveryController(final ProductService productService, final OrderDeliveryService orderDeliveryService) {
        this.productService = productService;
        this.orderDeliveryService = orderDeliveryService;
    }

    @GetMapping
    public String index(final Model model) {
        model.addAttribute("page", PageEnum.ORDERS_DELIVERY);
        model.addAttribute("displayName", AuthenticationUtils.getDisplayName());
        model.addAttribute("roles", AuthenticationUtils.getUserAuthorities());
        model.addAttribute("orders", orderDeliveryService.findAll());

        return "dashboard/orders/delivery/index";
    }

    @GetMapping("/create")
    public String create(
            @ModelAttribute(name = "orderDeliveryCreate") final CreateOrderDeliveryDTO createOrderDeliveryDTO,
            final Model model
    ) {
        model.addAttribute("page", PageEnum.ORDERS_DELIVERY_CREATE);
        model.addAttribute("displayName", AuthenticationUtils.getDisplayName());
        model.addAttribute("roles", AuthenticationUtils.getUserAuthorities());
        model.addAttribute("products", productService.findAllActiveProducts());

        return "dashboard/orders/delivery/create";
    }

    @PostMapping("/create")
    public String createAction(
            @ModelAttribute(name = "orderDeliveryCreate") @Valid final CreateOrderDeliveryDTO createOrderDeliveryDTO,
            final Model model,
            final BindingResult result
    ) {
        model.addAttribute("page", PageEnum.ORDERS_DELIVERY_CREATE);
        model.addAttribute("displayName", AuthenticationUtils.getDisplayName());
        model.addAttribute("roles", AuthenticationUtils.getUserAuthorities());

        try {
            orderDeliveryService.create(createOrderDeliveryDTO);

            return "redirect:/dashboard/orders/delivery?createOrderDeliverySuccess=true";
        } catch (ProductNotFoundException e) {
            log.error(e.getMessage());
            return "redirect:/dashboard/orders/delivery?createOrderDeliveryError=product-not-found";
        } catch (CustomerNotFoundException e) {
            log.error(e.getMessage());
            return "redirect:/dashboard/orders/delivery?createOrderDeliveryError=customer-not-found";
        } catch (Exception e) {
            log.error(e.getMessage());
            return "redirect:/dashboard/orders/delivery?createOrderDeliveryError=unknown-error";
        }
    }

    @GetMapping("/details/{id}")
    public String details(@PathVariable("id") final Long id, Model model) {
        model.addAttribute("page", PageEnum.ORDERS_DELIVERY_DETAILS);
        model.addAttribute("displayName", AuthenticationUtils.getDisplayName());
        model.addAttribute("roles", AuthenticationUtils.getUserAuthorities());
        model.addAttribute("orderDetails", orderDeliveryService.findById(id));
        model.addAttribute("orderStatus", OrdersStatusEnum.values());

        return "dashboard/orders/delivery/details";
    }

    @PostMapping("/details/{id}/edit-status")
    public String editStatusAction(@PathVariable("id") final Long id, Model model, final String status) {
        model.addAttribute("page", PageEnum.ORDERS_DELIVERY_DETAILS);
        model.addAttribute("displayName", AuthenticationUtils.getDisplayName());
        model.addAttribute("roles", AuthenticationUtils.getUserAuthorities());

        orderDeliveryService.editStatus(id, status);

        return "redirect:/dashboard/orders/delivery";
    }
}

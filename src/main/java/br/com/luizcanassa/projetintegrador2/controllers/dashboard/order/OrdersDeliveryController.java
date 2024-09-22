package br.com.luizcanassa.projetintegrador2.controllers.dashboard.order;

import br.com.luizcanassa.projetintegrador2.domain.dto.order.CreateOrderDeliveryDTO;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
        model.addAttribute("orders", new ArrayList<>());

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
}

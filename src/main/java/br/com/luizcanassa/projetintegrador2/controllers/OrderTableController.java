package br.com.luizcanassa.projetintegrador2.controllers;

import br.com.luizcanassa.projetintegrador2.domain.dto.order.local.CreateOrderLocalDTO;
import br.com.luizcanassa.projetintegrador2.domain.enums.PageEnum;
import br.com.luizcanassa.projetintegrador2.exception.ProductNotFoundException;
import br.com.luizcanassa.projetintegrador2.service.OrderLocalService;
import br.com.luizcanassa.projetintegrador2.service.ProductService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/orders-table")
public class OrderTableController {

    private final ProductService productService;
    
    private final OrderLocalService orderLocalService;

    public OrderTableController(final ProductService productService, final OrderLocalService orderLocalService) {
        this.productService = productService;
        this.orderLocalService = orderLocalService;
    }

    @GetMapping
    public String index(final Model model, @ModelAttribute(name = "orderLocalCreate") final CreateOrderLocalDTO createOrderLocalDTO) {
        model.addAttribute("page", PageEnum.ORDERS_TABLE);
        model.addAttribute("products", productService.findAllActiveProducts());
        return "orders-table/index";
    }

    @PostMapping("/create")
    public String create(
            @ModelAttribute(name = "orderLocalCreate") @Valid final CreateOrderLocalDTO createOrderLocalDTO,
            final BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return "orders-table/index";
        }

        try {
            orderLocalService.create(createOrderLocalDTO);

            return "redirect:/orders-table?createOrderLocalSuccess=true";
        } catch (ProductNotFoundException e) {
            log.error(e.getMessage());
            return "redirect:/orders-table?createOrderLocalError=product-not-found";
        } catch (Exception e) {
            log.error(e.getMessage());
            return "redirect:/orders-table?createOrderLocalError=unknown-error";
        }
    }
}

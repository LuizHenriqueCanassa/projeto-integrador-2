package br.com.luizcanassa.projetintegrador2.controllers.dashboard;

import br.com.luizcanassa.projetintegrador2.domain.enums.PageEnum;
import br.com.luizcanassa.projetintegrador2.service.OrderDeliveryService;
import br.com.luizcanassa.projetintegrador2.service.OrderLocalService;
import br.com.luizcanassa.projetintegrador2.service.OrderService;
import br.com.luizcanassa.projetintegrador2.utils.AuthenticationUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = {"/dashboard"})
public class DashboardController {

    private final OrderLocalService orderLocalService;

    private final OrderDeliveryService orderDeliveryService;

    private final OrderService orderService;

    public DashboardController(
            final OrderLocalService orderLocalService,
            final OrderDeliveryService orderDeliveryService,
            final OrderService orderService
    ) {
        this.orderLocalService = orderLocalService;
        this.orderDeliveryService = orderDeliveryService;
        this.orderService = orderService;
    }

    @GetMapping
    public String index(final Model model) {

        model.addAttribute("page", PageEnum.DASHBOARD);
        model.addAttribute("displayName", AuthenticationUtils.getDisplayName());
        model.addAttribute("roles", AuthenticationUtils.getUserAuthorities());
        model.addAttribute("ordersDeliveryToday", orderDeliveryService.getQuantityOrdersDeliveryToday());
        model.addAttribute("ordersLocalToday", orderLocalService.getQuantityOrdersLocalToday());
        model.addAttribute("totalBillingOfDay", orderService.getTotalBillingOfDay());
        model.addAttribute("totalOrdersOfMonth", orderService.getTotalOrdersOfMonth());
        model.addAttribute("totalOrdersDeliveryLast7Days", orderDeliveryService.getQuantityOrdersDeliveryLast7Days());
        model.addAttribute("totalOrdersLocalLast7Days", orderLocalService.getQuantityOrdersLocalLast7Days());
        model.addAttribute("billingLast8Months", orderService.getBillingLast8Months());

        return "dashboard/index";
    }
}

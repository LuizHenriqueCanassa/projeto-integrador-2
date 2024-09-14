package br.com.luizcanassa.projetintegrador2.controllers.dashboard;

import br.com.luizcanassa.projetintegrador2.domain.dto.customer.CustomerCreateDTO;
import br.com.luizcanassa.projetintegrador2.domain.dto.customer.CustomerEditDTO;
import br.com.luizcanassa.projetintegrador2.domain.enums.PageEnum;
import br.com.luizcanassa.projetintegrador2.exception.CustomerAlreadyExistException;
import br.com.luizcanassa.projetintegrador2.exception.CustomerNotFoundException;
import br.com.luizcanassa.projetintegrador2.service.CustomerService;
import br.com.luizcanassa.projetintegrador2.utils.AuthenticationUtils;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("/dashboard/customers")
public class CustomersController {

    private final CustomerService customerService;

    public CustomersController(final CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public String index(final Model model) {
        model.addAttribute("page", PageEnum.CUSTOMERS);
        model.addAttribute("displayName", AuthenticationUtils.getDisplayName());
        model.addAttribute("roles", AuthenticationUtils.getUserAuthorities());
        model.addAttribute("customers", customerService.findAll());

        return "dashboard/customers/index";
    }

    @GetMapping("/create")
    public String create(
            @ModelAttribute(value = "customerCreate") CustomerCreateDTO customerCreateDTO,
            final Model model
    ) {
        model.addAttribute("page", PageEnum.CREATE_CUSTOMERS);
        model.addAttribute("displayName", AuthenticationUtils.getDisplayName());
        model.addAttribute("roles", AuthenticationUtils.getUserAuthorities());

        return "dashboard/customers/create";
    }

    @PostMapping("/create")
    public String createAction(
            @ModelAttribute(value = "customerCreate") @Valid CustomerCreateDTO customerCreateDTO,
            final BindingResult bindingResult,
            final Model model
    ) {
        model.addAttribute("page", PageEnum.CREATE_CUSTOMERS);
        model.addAttribute("displayName", AuthenticationUtils.getDisplayName());
        model.addAttribute("roles", AuthenticationUtils.getUserAuthorities());

        if (bindingResult.hasErrors()) {
            return "dashboard/customers/create";
        }

        try {
            customerService.create(customerCreateDTO);

            return "redirect:/dashboard/customers?customerSuccessCreate=true";
        } catch (CustomerAlreadyExistException e) {
            log.error(e.getMessage());
            return "redirect:/dashboard/customers?createCustomerError=customer-already-exist";
        } catch (Exception e) {
            log.error(e.getMessage());
            return "redirect:/dashboard/customers?createCustomerError=unknown-error";
        }
    }

    @GetMapping("/edit/{id}")
    public String edit(
            @PathVariable final Long id,
            @ModelAttribute(value = "customerToEdit") CustomerEditDTO customerEditDTO,
            final Model model
    ) {
        model.addAttribute("page", PageEnum.EDIT_CUSTOMERS);
        model.addAttribute("displayName", AuthenticationUtils.getDisplayName());
        model.addAttribute("roles", AuthenticationUtils.getUserAuthorities());

        try {
            model.addAttribute("customer", customerService.findById(id));

            return "dashboard/customers/edit";
        } catch (CustomerNotFoundException e) {
            log.error(e.getMessage());
            return "redirect:/dashboard/customers?customerEditError=customer-not-found";
        } catch (Exception e) {
            log.error(e.getMessage());
            return "redirect:/dashboard/customers?customerEditError=unknown-error";
        }
    }

    @PostMapping("/edit/{id}")
    public String editAction(
            @PathVariable final Long id,
            @ModelAttribute(value = "customerToEdit") @Valid CustomerEditDTO customerEditDTO,
            final Model model,
            final BindingResult bindingResult
    ) {
        model.addAttribute("page", PageEnum.EDIT_CUSTOMERS);
        model.addAttribute("displayName", AuthenticationUtils.getDisplayName());
        model.addAttribute("roles", AuthenticationUtils.getUserAuthorities());

        if (bindingResult.hasErrors()) {
            return "dashboard/customers/edit";
        }

        try {
            customerService.update(customerEditDTO);

            return "redirect:/dashboard/customers?customerSuccessEdit=true";
        } catch (CustomerNotFoundException e) {
            log.error(e.getMessage());
            return "redirect:/dashboard/customers?customerEditError=customer-not-found";
        } catch (CustomerAlreadyExistException e) {
            log.error(e.getMessage());
            return "redirect:/dashboard/customers?createCustomerError=customer-already-exist";
        }  catch (Exception e) {
            log.error(e.getMessage());
            return "redirect:/dashboard/customers?customerEditError=unknown-error";
        }
    }
}

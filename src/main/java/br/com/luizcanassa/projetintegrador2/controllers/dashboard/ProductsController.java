package br.com.luizcanassa.projetintegrador2.controllers.dashboard;

import br.com.luizcanassa.projetintegrador2.domain.dto.product.ProductCreateDTO;
import br.com.luizcanassa.projetintegrador2.domain.enums.PageEnum;
import br.com.luizcanassa.projetintegrador2.exception.CategoryNotFoundException;
import br.com.luizcanassa.projetintegrador2.service.CategoryService;
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

@Slf4j
@Controller
@RequestMapping("/dashboard/products")
public class ProductsController {

    private final ProductService productService;

    private final CategoryService categoryService;

    public ProductsController(final ProductService productService, final CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
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
    public String create(
            @ModelAttribute(value = "productCreate") ProductCreateDTO productCreateDTO,
            final Model model
    ) {

        model.addAttribute("page", PageEnum.PRODUCTS);
        model.addAttribute("displayName", AuthenticationUtils.getDisplayName());
        model.addAttribute("roles", AuthenticationUtils.getUserAuthorities());
        model.addAttribute("categories", categoryService.findAllActiveCategories());

        return "dashboard/products/create";
    }

    @PostMapping("/create")
    public String createAction(
            @ModelAttribute(value = "productCreate") @Valid final ProductCreateDTO productCreateDTO,
            final BindingResult bindingResult,
            final Model model
    ) {
        model.addAttribute("page", PageEnum.PRODUCTS);
        model.addAttribute("displayName", AuthenticationUtils.getDisplayName());
        model.addAttribute("roles", AuthenticationUtils.getUserAuthorities());

        if (bindingResult.hasErrors()) {
            return "dashboard/products/create";
        }

        try {
            productService.create(productCreateDTO);

            return "redirect:/dashboard/products?productSuccessCreate=true";
        } catch (CategoryNotFoundException e) {
            log.error(e.getMessage());
            return "redirect:/dashboard/products/index?createProductError=category-not-found";
        } catch (Exception e) {
            log.error(e.getMessage());
            return "redirect:/dashboard/products/index?createProductError=unknown-error";
        }
    }
}

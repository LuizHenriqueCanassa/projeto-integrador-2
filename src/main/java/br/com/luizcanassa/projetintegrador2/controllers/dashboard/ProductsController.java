package br.com.luizcanassa.projetintegrador2.controllers.dashboard;

import br.com.luizcanassa.projetintegrador2.domain.dto.product.ProductCreateDTO;
import br.com.luizcanassa.projetintegrador2.domain.dto.product.ProductEditDTO;
import br.com.luizcanassa.projetintegrador2.domain.enums.PageEnum;
import br.com.luizcanassa.projetintegrador2.exception.CategoryNotFoundException;
import br.com.luizcanassa.projetintegrador2.exception.ProductNotFoundException;
import br.com.luizcanassa.projetintegrador2.service.CategoryService;
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
    public String createProduct(
            @ModelAttribute(value = "productCreate") ProductCreateDTO productCreateDTO,
            final Model model
    ) {

        model.addAttribute("page", PageEnum.CREATE_PRODUCTS);
        model.addAttribute("displayName", AuthenticationUtils.getDisplayName());
        model.addAttribute("roles", AuthenticationUtils.getUserAuthorities());
        model.addAttribute("categories", categoryService.findAllActiveCategories());

        return "dashboard/products/create";
    }

    @PostMapping("/create")
    public String createProductAction(
            @ModelAttribute(value = "productCreate") @Valid final ProductCreateDTO productCreateDTO,
            final BindingResult bindingResult,
            final Model model
    ) {
        model.addAttribute("page", PageEnum.CREATE_PRODUCTS);
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

    @GetMapping("/edit/{id}")
    public String editProduct(
            @PathVariable final Long id,
            @ModelAttribute(value = "productEdit") final ProductEditDTO productEditDTO,
            final Model model
    ) {
        model.addAttribute("page", PageEnum.EDIT_PRODUCTS);
        model.addAttribute("displayName", AuthenticationUtils.getDisplayName());
        model.addAttribute("roles", AuthenticationUtils.getUserAuthorities());
        model.addAttribute("categories", categoryService.findAllActiveCategories());
        model.addAttribute("productToEdit", productService.findByIdToEdit(id));

        return "dashboard/products/edit";
    }

    @PostMapping("/edit/{id}")
    public String editProductAction(
            @PathVariable final Long id,
            @ModelAttribute(value = "productEdit") @Valid final ProductEditDTO productEditDTO,
            final Model model,
            final BindingResult bindingResult
    ) {
        model.addAttribute("page", PageEnum.EDIT_PRODUCTS);
        model.addAttribute("displayName", AuthenticationUtils.getDisplayName());
        model.addAttribute("roles", AuthenticationUtils.getUserAuthorities());

        productEditDTO.setId(id);

        productService.edit(productEditDTO);

        return "redirect:/dashboard/products?productSuccessEdit=true";
    }

    @GetMapping("/change-status/{id}")
    public String changeProductStatus(@PathVariable("id") final Long id) {
        try {
            productService.changeStatus(id);

            return "redirect:/dashboard/products?changeStatusProductSuccess=true";
        } catch (ProductNotFoundException e) {
            log.error(e.getMessage());
            return "redirect:/dashboard/products?changeStatusProductError=not-found";
        } catch (Exception e) {
            log.error(e.getMessage());
            return "redirect:/dashboard/products?changeStatusProductError=unknown-error";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable final Long id) {
        try {
            productService.delete(id);

            return "redirect:/dashboard/products?productDeleteSuccess=true";
        } catch (ProductNotFoundException e) {
            log.error(e.getMessage());
            return "redirect:/dashboard/products?productDeleteError=product-not-found";
        } catch (Exception e) {
            log.error(e.getMessage());
            return "redirect:/dashboard/products?productDeleteError=unknown-error";
        }
    }
}

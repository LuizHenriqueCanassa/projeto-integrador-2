package br.com.luizcanassa.projetintegrador2.controllers.dashboard;

import br.com.luizcanassa.projetintegrador2.domain.dto.CategoryCreateDTO;
import br.com.luizcanassa.projetintegrador2.domain.dto.CategoryEditDTO;
import br.com.luizcanassa.projetintegrador2.domain.enums.PageEnum;
import br.com.luizcanassa.projetintegrador2.exception.CategoryNotFoundException;
import br.com.luizcanassa.projetintegrador2.exception.DeleteCategoryWithProductsException;
import br.com.luizcanassa.projetintegrador2.service.CategoryService;
import br.com.luizcanassa.projetintegrador2.utils.AuthenticationUtils;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("/dashboard/categories")
public class CategoriesController {

    private final CategoryService categoryService;

    public CategoriesController(final CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public String index(final Model model) {

        model.addAttribute("page", PageEnum.CATEGORIES);
        model.addAttribute("displayName", AuthenticationUtils.getDisplayName());
        model.addAttribute("roles", AuthenticationUtils.getUserAuthorities());
        model.addAttribute("categories", categoryService.findAll());

        return "dashboard/categories/index";
    }

    @GetMapping("/create")
    public String create(
            @ModelAttribute(value = "categoryCreate") final CategoryCreateDTO categoryCreate,
            final Model model
    ) {

        model.addAttribute("page", PageEnum.CREATE_CATEGORIES);
        model.addAttribute("displayName", AuthenticationUtils.getDisplayName());
        model.addAttribute("roles", AuthenticationUtils.getUserAuthorities());

        return "dashboard/categories/create";
    }

    @PostMapping("/create")
    public String createCategoryAction(
            @ModelAttribute(value = "categoryCreate") @Valid final CategoryCreateDTO categoryCreate,
            final BindingResult bindingResult,
            final Model model
    ) {
        model.addAttribute("page", PageEnum.CREATE_CATEGORIES);
        model.addAttribute("displayName", AuthenticationUtils.getDisplayName());
        model.addAttribute("roles", AuthenticationUtils.getUserAuthorities());

        if (bindingResult.hasErrors()) {
            return "dashboard/categories/create";
        }

        try {
            categoryService.createCategory(categoryCreate);

            return "redirect:/dashboard/categories?createCategorySuccess=true";
        } catch (Exception e) {
            log.error(e.getMessage());
            return "redirect:/dashboard/categories?createCategoryError=unknown-error";
        }
    }

    @GetMapping("/edit/{id}")
    public String editCategory(
            @PathVariable("id") final Long id,
            final Model model
    ) {
        try {
            model.addAttribute("page", PageEnum.EDIT_CATEGORIES);
            model.addAttribute("displayName", AuthenticationUtils.getDisplayName());
            model.addAttribute("roles", AuthenticationUtils.getUserAuthorities());
            model.addAttribute("categoryEdit", categoryService.findByIdToEdit(id));

            return "dashboard/categories/edit";
        } catch (CategoryNotFoundException e) {
            log.error(e.getMessage());
            return "redirect:/dashboard/categories?updateCategoryError=not-found";
        } catch (Exception e) {
            log.error(e.getMessage());
            return "redirect:/dashboard/categories?updateCategoryError=unknown-error";
        }
    }

    @PostMapping("/edit/{id}")
    public String editCategoryAction(
            @PathVariable("id") final Long id,
            @ModelAttribute(value = "categoryEdit") @Valid final CategoryEditDTO categoryEdit,
            final BindingResult bindingResult,
            final Model model
    ) {
        model.addAttribute("page", PageEnum.EDIT_CATEGORIES);
        model.addAttribute("displayName", AuthenticationUtils.getDisplayName());
        model.addAttribute("roles", AuthenticationUtils.getUserAuthorities());

        if (bindingResult.hasErrors()) {
            return "dashboard/categories/edit";
        }

        try {
            categoryService.updateCategory(id, categoryEdit);

            return "redirect:/dashboard/categories?updateCategorySuccess=true";
        } catch (CategoryNotFoundException e) {
            log.error(e.getMessage());
            return "redirect:/dashboard/categories?updateCategoryError=not-found";
        } catch (Exception e) {
            log.error(e.getMessage());
            return "redirect:/dashboard/categories?updateCategoryError=unknown-error";
        }
    }

    @GetMapping("/change-status/{id}")
    public String changeCategoryStatus(@PathVariable("id") final Long id) {
        try {
            categoryService.changeStatus(id);

            return "redirect:/dashboard/categories?changeStatusCategorySuccess=true";
        } catch (CategoryNotFoundException e) {
            log.error(e.getMessage());
            return "redirect:/dashboard/categories?changeStatusCategoryError=not-found";
        } catch (Exception e) {
            log.error(e.getMessage());
            return "redirect:/dashboard/categories?changeStatusCategoryError=unknown-error";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteCategory(@PathVariable("id") final Long id) {
        try {
            categoryService.deleteCategory(id);

            return "redirect:/dashboard/categories?deleteCategorySuccess=true";
        } catch (DeleteCategoryWithProductsException e) {
            log.error(e.getMessage());
            return "redirect:/dashboard/categories?deleteCategoryError=category-with-products";
        } catch (CategoryNotFoundException e) {
            log.error(e.getMessage());
            return "redirect:/dashboard/categories?deleteCategoryError=not-found";
        } catch (Exception e) {
            log.error(e.getMessage());
            return "redirect:/dashboard/categories?deleteCategoryError=unknown-error";
        }
    }
}

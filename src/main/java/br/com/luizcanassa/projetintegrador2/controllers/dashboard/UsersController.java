package br.com.luizcanassa.projetintegrador2.controllers.dashboard;

import br.com.luizcanassa.projetintegrador2.domain.dto.UserCreateDTO;
import br.com.luizcanassa.projetintegrador2.domain.enums.PageEnum;
import br.com.luizcanassa.projetintegrador2.exception.*;
import br.com.luizcanassa.projetintegrador2.service.RoleService;
import br.com.luizcanassa.projetintegrador2.service.UserService;
import br.com.luizcanassa.projetintegrador2.utils.AuthenticationUtils;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("/dashboard/users")
public class UsersController {

    private final UserService userService;

    private final RoleService roleService;

    public UsersController(final UserService userService, final RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping
    public String index(final Model model) {

        model.addAttribute("users", userService.findAll());
        model.addAttribute("page", PageEnum.USERS);
        model.addAttribute("displayName", AuthenticationUtils.getDisplayName());
        model.addAttribute("roles", AuthenticationUtils.getUserAuthorities());

        return "dashboard/users/index";
    }

    @GetMapping("/create")
    public String createUser(@ModelAttribute(value = "userCreate") final UserCreateDTO userCreate, final Model model) {

        model.addAttribute("page", PageEnum.CREATE_USERS);
        model.addAttribute("displayName", AuthenticationUtils.getDisplayName());
        model.addAttribute("roles", AuthenticationUtils.getUserAuthorities());
        model.addAttribute("roleToCreate", roleService.findAll());

        return "dashboard/users/create";
    }

    @PostMapping("/create")
    public String sendCreateUser(@ModelAttribute(value = "userCreate") @Valid final UserCreateDTO userCreate, final BindingResult bindingResult, final Model model) {

        model.addAttribute("page", PageEnum.CREATE_USERS);
        model.addAttribute("displayName", AuthenticationUtils.getDisplayName());
        model.addAttribute("roles", AuthenticationUtils.getUserAuthorities());
        model.addAttribute("roleToCreate", roleService.findAll());

        if  (bindingResult.hasErrors()) {
            return "/dashboard/users/create";
        }

        try {
            userService.createUser(userCreate);

            return "redirect:/dashboard/users?createUserSuccess=true";
        } catch (RoleNotFoundException e) {
            log.error(e.getMessage());
            return "redirect:/dashboard/users/create?createUserError=role-not-found";
        } catch (Exception e) {
            log.error(e.getMessage());
            return "redirect:/dashboard/users/create?createUserError=unknown-error";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") final Long id) {
        try {
            userService.deleteUser(id);

            return "redirect:/dashboard/users?deleteUserSuccess=true";
        } catch (UserNotFoundException e) {
            log.error(e.getMessage());
            return "redirect:/dashboard/users?deleteUserError=not-found";
        } catch (DeleteUserException e) {
            log.error(e.getMessage());
            return "redirect:/dashboard/users?deleteUserError=same-user";
        } catch (DeleteRootUserException e) {
            log.error(e.getMessage());
            return "redirect:/dashboard/users?deleteUserError=root-user";
        } catch (Exception e) {
            log.error(e.getMessage());
            return "redirect:/dashboard/users?deleteUserError=unknown-error";
        }
    }

    @GetMapping("/change-status/{id}")
    public String changeUserStatus(@PathVariable("id") Long id) {
        try {
            userService.changeUserStatus(id);

            return "redirect:/dashboard/users?changeStatusSuccess=true";
        } catch (UserNotFoundException e) {
            log.error(e.getMessage());
            return "redirect:/dashboard/users?changeStatusError=not-found";
        } catch (ChangeStatusUserException e) {
            log.error(e.getMessage());
            return "redirect:/dashboard/users?changeStatusError=same-user";
        } catch (ChangeStatusRootUserException e) {
            log.error(e.getMessage());
            return "redirect:/dashboard/users?changeStatusError=root-user";
        } catch (Exception e) {
            log.error(e.getMessage());
            return "redirect:/dashboard/users?changeStatusError=unknown-error";
        }
    }
}

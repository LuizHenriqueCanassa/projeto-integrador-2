package br.com.luizcanassa.projetintegrador2.controllers.dashboard;

import br.com.luizcanassa.projetintegrador2.domain.enums.PageEnum;
import br.com.luizcanassa.projetintegrador2.exception.ChangeStatusRootUserException;
import br.com.luizcanassa.projetintegrador2.exception.ChangeStatusUserException;
import br.com.luizcanassa.projetintegrador2.exception.UserNotFoundException;
import br.com.luizcanassa.projetintegrador2.service.UserService;
import br.com.luizcanassa.projetintegrador2.utils.AuthenticationUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/dashboard/users")
public class UsersController {

    private final UserService userService;

    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String index(final Model model) {

        model.addAttribute("users", userService.findAll());
        model.addAttribute("page", PageEnum.USERS);
        model.addAttribute("displayName", AuthenticationUtils.getDisplayName());
        model.addAttribute("roles", AuthenticationUtils.getUserAuthorities());

        return "dashboard/users/index";
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

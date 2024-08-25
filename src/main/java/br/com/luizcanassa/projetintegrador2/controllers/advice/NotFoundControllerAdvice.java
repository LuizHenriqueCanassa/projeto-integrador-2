package br.com.luizcanassa.projetintegrador2.controllers.advice;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class NotFoundControllerAdvice {

    @ExceptionHandler(NoHandlerFoundException.class)
    public String handle(Exception e) {
        return "redirect:/dashboard/404";
    }

}

package br.edu.unialfa.gabarito.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalControllerAdvice {

    @ModelAttribute("isLoginPage")
    public boolean isLoginPage(HttpServletRequest request) {
        return request.getRequestURI().contains("/login");
    }
}

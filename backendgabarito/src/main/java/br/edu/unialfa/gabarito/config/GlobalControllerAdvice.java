package br.edu.unialfa.gabarito.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalControllerAdvice {

    @ModelAttribute("httpServletRequest")
    public HttpServletRequest httpServletRequest(HttpServletRequest request) {
        return request;
    }
}

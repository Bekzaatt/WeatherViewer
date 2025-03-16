package com.bekzataitymov.ExceptionHandler;

import com.bekzataitymov.Service.Interface.LocationService;
import org.hibernate.NonUniqueResultException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(HttpClientErrorException.class)
    public ModelAndView handleClientError(HttpClientErrorException ex) {
        return createErrorPage(ex.getMessage());
    }
    @ExceptionHandler(CityNotFoundException.class)
    public ModelAndView handleCityNotFoundException(CityNotFoundException ex){
        return createErrorPage("You can write only with latin letters");
    }

    private ModelAndView createErrorPage(String errorMessage) {
        ModelAndView modelAndView = new ModelAndView("errors");
        modelAndView.addObject("errors", errorMessage);
        return modelAndView;
    }

}

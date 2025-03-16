package com.bekzataitymov.Controller;

import com.bekzataitymov.Entity.DTO.UserDTO;
import com.bekzataitymov.Entity.User;
import com.bekzataitymov.Service.Interface.UserService;
import com.bekzataitymov.Util.ValidationUtil;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
public class AuthenticationController {
    @Autowired
    private UserService userService;
    @Autowired
    private LocationController locationController;
    @GetMapping("/register")
    public String registration(Model model){

        model.addAttribute("user", new UserDTO());
        return "registration.html";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("user") UserDTO user,
                           @RequestParam("repeatPassword") String repeatPassword, Model model, HttpServletResponse response){

        UserDTO userToCheck = userService.findByCredentials(user.getUsername(), user.getPassword(), response);
        String validated = ValidationUtil.signUpValidation(user, repeatPassword, model, userToCheck);
        if(validated != null){
            return validated;
        }
        UserDTO userDTO = userService.save(user.getUsername(), user.getPassword(), response);
        model.addAttribute("user", userDTO);
        return locationController.weathersViewer(model);
    }

    @GetMapping("/login")
    public String login(){
        return "login.html";
    }

    @PostMapping("/login")
    public String logIn(@ModelAttribute("user") UserDTO userDTO,
                        Model model, HttpServletResponse response) throws UserPrincipalNotFoundException {
        UserDTO user = userService.find(userDTO.getUsername(), userDTO.getPassword(), response);

        String validated = ValidationUtil.signInValidation(userDTO, model, user);
        if(validated != null){
            return validated;
        }

        model.addAttribute("user", user);
        return locationController.weathersViewer(model);
    }

    @GetMapping("/logout")
    public String logout(@CookieValue(name = "session", required = false) String sessionsId, HttpServletResponse response){
        userService.logout(sessionsId, response);
        return "login.html";
    }



}

package com.bekzataitymov.Controller;

import com.bekzataitymov.Entity.User;
import com.bekzataitymov.Service.Interface.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.nio.file.attribute.UserPrincipalNotFoundException;

@Controller
public class AuthenticationController {
    @Autowired
    private UserService userService;
    @Autowired
    private LocationController locationController;
    @GetMapping("/register")
    public String registration(Model model){

        model.addAttribute("user", new User());
        return "registration.html";
    }

    @PostMapping("/register")
    public String register(@RequestParam("login") String login, @RequestParam("password")  String password,
                           Model model, HttpServletResponse response){
        User modelUser = userService.save(login, password, response);
        model.addAttribute("user", modelUser);
        return locationController.weathersViewer(model);
    }

    @GetMapping("/login")
    public String login(){
        return "login.html";
    }

    @PostMapping("/login")
    public String logIn(@RequestParam("login") String login, @RequestParam("password")  String password,
                        Model model, HttpServletResponse response) throws UserPrincipalNotFoundException {

        User user = userService.find(login, password, response);
        model.addAttribute("user", user);
        return locationController.weathersViewer(model);
    }

    @GetMapping("/logout")
    public String logout(@CookieValue(name = "session", required = false) String sessionsId, HttpServletResponse response){
        userService.logout(sessionsId, response);
        return "login.html";
    }



}

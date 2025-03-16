package com.bekzataitymov.Util;

import com.bekzataitymov.Entity.DTO.UserDTO;
import com.bekzataitymov.Entity.User;
import org.springframework.ui.Model;

import java.util.HashMap;

public class ValidationUtil {

    public static String signUpValidation(UserDTO user, String repeatPassword, Model model, UserDTO userToCheck){
        HashMap<String, String> errors = new HashMap<>();
        if(userToCheck != null){
            errors.put("credentialsError", "There's user with same credentials.");
        }
        if(user.getUsername().length() < 2){
            errors.put("usernameError", "Username should be greater than 2 letters.");
        }if(user.getPassword().length() < 6) {
            errors.put("passwordError", "Password should be greater than 6 letters.");
        }
        if(!user.getPassword().equals(repeatPassword)){
            errors.put("repeatPasswordError", "Password don't match");
        }
        if(!errors.isEmpty()){
            System.out.println("errors method");
            model.addAttribute("errors", errors);
            return "register-with-errors";
        }
        return null;
    }

    public static String signInValidation(UserDTO user, Model model, UserDTO userToCheck){
        HashMap<String, String> errors = new HashMap<>();
        if(userToCheck == null){
            errors.put("credentialsError", "There's no user with such credentials.");
        }
        if(!errors.isEmpty()){
            System.out.println("errors method");
            model.addAttribute("errors", errors);
            return "login-with-errors";
        }
        return null;
    }
}

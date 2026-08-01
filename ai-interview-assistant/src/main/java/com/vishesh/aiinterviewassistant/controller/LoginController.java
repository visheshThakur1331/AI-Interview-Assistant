package com.vishesh.aiinterviewassistant.controller;

import com.vishesh.aiinterviewassistant.entity.Trainer;
import com.vishesh.aiinterviewassistant.entity.User;
import com.vishesh.aiinterviewassistant.service.TrainerService;
import com.vishesh.aiinterviewassistant.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private TrainerService trainerService;

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String login(String email,
                        String password,
                        String role,
                        HttpSession session) {

        // Student Login
        if(role.equals("student")) {

            User user = userService.loginUser(email, password);

            if(user != null) {

                session.setAttribute("userEmail", user.getEmail());

                session.setAttribute("userName", user.getName());
                return "dashboard";
            }
        }

        // Trainer Login
        if(role.equals("trainer")) {

            Trainer trainer = trainerService.loginTrainer(email, password);

            if(trainer != null) {

                session.setAttribute("trainerEmail", trainer.getEmail());

                return "redirect:/trainer/dashboard";
            }
        }

        // Admin Login
        if(role.equals("admin")) {

            if(email.equals("admin@gmail.com") &&
                    password.equals("admin123")) {

                session.setAttribute("admin", "admin");

                return "redirect:/admin";
            }
        }

        return "login";
    }
}
package com.vishesh.aiinterviewassistant.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AdminLoginController {

    // Admin Login Page
    @GetMapping("/admin/login")
    public String adminLoginPage() {
        return "admin-login";
    }

    // Admin Login
    @PostMapping("/admin/login")
    public String adminLogin(String username,
                             String password,
                             HttpSession session) {

        if (username.equals("admin")
                && password.equals("admin123")) {

            session.setAttribute("admin", username);

            return "redirect:/admin";
        }

        return "redirect:/admin/login";
    }

    // Admin Logout
    @GetMapping("/admin/logout")
    public String logout(HttpSession session) {

        session.invalidate();

        return "redirect:/admin/login";
    }
}
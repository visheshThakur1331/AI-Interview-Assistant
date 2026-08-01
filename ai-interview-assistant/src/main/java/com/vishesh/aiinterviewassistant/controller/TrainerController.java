package com.vishesh.aiinterviewassistant.controller;

import com.vishesh.aiinterviewassistant.entity.Trainer;
import com.vishesh.aiinterviewassistant.service.TrainerService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import com.vishesh.aiinterviewassistant.repository.UserRepository;
import com.vishesh.aiinterviewassistant.repository.ResultRepository;
import org.springframework.ui.Model;
import com.vishesh.aiinterviewassistant.service.ResultService;

@Controller
public class TrainerController {

    @Autowired
    private TrainerService trainerService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ResultRepository resultRepository;

    @Autowired
    private ResultService resultService;

    // Trainer Registration Page
    @GetMapping("/trainer/register")
    public String trainerRegisterPage() {
        return "trainer-register";
    }

    // Save Trainer
    @PostMapping("/trainer/register")
    public String registerTrainer(Trainer trainer) {

        trainerService.saveTrainer(trainer);

        return "trainer-login";
    }

    // Trainer Login Page
    @GetMapping("/trainer/login")
    public String trainerLoginPage() {
        return "trainer-login";
    }

    @GetMapping("/trainer/students")
    public String viewStudents(Model model,
                               HttpSession session) {

        if(session.getAttribute("trainerEmail")==null){

            return "redirect:/login";
        }

        model.addAttribute("students",
                userRepository.findAll());

        return "trainer-students";
    }

    // Trainer Login
    @PostMapping("/trainer/login")
    public String trainerLogin(String email,
                               String password,
                               HttpSession session) {

        Trainer trainer = trainerService.loginTrainer(email, password);

        if (trainer != null) {

            session.setAttribute("trainerEmail", trainer.getEmail());

            return "trainer-dashboard";
        }

        return "trainer-login";
    }

    // Trainer Dashboard
    @GetMapping("/trainer/dashboard")
    public String trainerDashboard(Model model,
                                   HttpSession session) {

        if(session.getAttribute("trainerEmail")==null){
            return "redirect:/login";
        }

        model.addAttribute(
                "totalStudents",
                userRepository.count());

        model.addAttribute(
                "totalInterviews",
                resultRepository.count());

        double averageScore =
                resultRepository.findAll()
                        .stream()
                        .mapToInt(r -> r.getScore())
                        .average()
                        .orElse(0);

        model.addAttribute(
                "averageScore",
                String.format("%.2f", averageScore));

        model.addAttribute(
                "topPerformers",
                resultService.getTopPerformers());

        model.addAttribute(
                "recentInterviews",
                resultService.getRecentInterviews());

        return "trainer-dashboard";
    }

    // Logout
    @GetMapping("/trainer/logout")
    public String trainerLogout(HttpSession session) {

        session.invalidate();

        return "redirect:/trainer/login";
    }
}
package com.vishesh.aiinterviewassistant.controller;

import com.vishesh.aiinterviewassistant.entity.Question;
import com.vishesh.aiinterviewassistant.repository.QuestionRepository;
import com.vishesh.aiinterviewassistant.repository.ResultRepository;
import com.vishesh.aiinterviewassistant.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.vishesh.aiinterviewassistant.service.ResultService;
import com.vishesh.aiinterviewassistant.service.UserService;
import jakarta.servlet.http.HttpSession;
import com.vishesh.aiinterviewassistant.service.TrainerService;
import com.vishesh.aiinterviewassistant.entity.Trainer;
import com.vishesh.aiinterviewassistant.service.TrainerService;

@Controller
public class AdminController {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ResultRepository resultRepository;

    @Autowired
    private ResultService resultService;

    @Autowired
    private UserService userService;

    @Autowired
    private TrainerService trainerService;

    @GetMapping("/admin")
    public String adminPage(Model model,
                            HttpSession session) {

        if(session.getAttribute("admin")==null){
            return "redirect:/admin/login";
        }

        model.addAttribute("question", new Question());

        model.addAttribute(
                "questions",
                questionRepository.findAll());

        model.addAttribute(
                "totalTrainers",
                trainerService.getAllTrainers().size());

        // ===== Analytics =====

        long totalUsers =
                userRepository.count();

        long totalQuestions =
                questionRepository.count();

        long totalInterviews =
                resultRepository.count();

        double averageScore = resultRepository
                .findAll()
                .stream()
                .mapToInt(r -> r.getScore())
                .average()
                .orElse(0);

        model.addAttribute(
                "totalUsers",
                totalUsers);

        model.addAttribute(
                "totalQuestions",
                totalQuestions);

        model.addAttribute(
                "totalInterviews",
                totalInterviews);

        model.addAttribute(
                "averageScore",
                String.format("%.2f", averageScore));

        // ===== Phase 14 Category Analytics =====

        model.addAttribute(
                "javaQuestions",
                questionRepository.findByCategory("Java").size());

        model.addAttribute(
                "springQuestions",
                questionRepository.findByCategory("Spring Boot").size());

        model.addAttribute(
                "oopQuestions",
                questionRepository.findByCategory("OOP").size());

        model.addAttribute(
                "topPerformers",
                resultService.getTopPerformers());

        model.addAttribute(
                "recentInterviews",
                resultService.getRecentInterviews());

        model.addAttribute(
                "latestUsers",
                userService.getLatestUsers());

        return "admin";
    }

    @PostMapping("/admin/save")
    public String saveQuestion(Question question) {

        questionRepository.save(question);

        return "redirect:/admin";
    }

    @GetMapping("/admin/delete/{id}")
    public String deleteQuestion(
            @PathVariable Long id) {

        questionRepository.deleteById(id);

        return "redirect:/admin";
    }

    @GetMapping("/admin/edit/{id}")
    public String editQuestion(
            @PathVariable Long id,
            Model model) {

        Question question =
                questionRepository.findById(id)
                        .orElse(null);

        model.addAttribute(
                "question",
                question);

        return "edit-question";
    }

    @PostMapping("/admin/update")
    public String updateQuestion(
            Question question) {

        questionRepository.save(question);

        return "redirect:/admin";
    }

    @GetMapping("/admin/search")
    public String searchQuestions(
            @RequestParam String keyword,
            Model model) {

        model.addAttribute(
                "questions",
                questionRepository
                        .findByQuestionTextContainingIgnoreCase(keyword));

        return "admin";
    }

    @GetMapping("/admin/trainers")
    public String trainerPage(Model model) {

        model.addAttribute("trainer", new Trainer());

        model.addAttribute("trainers",
                trainerService.getAllTrainers());

        return "manage-trainers";
    }
    @PostMapping("/admin/trainers/save")
    public String saveTrainer(Trainer trainer){

        trainerService.saveTrainer(trainer);

        return "redirect:/admin/trainers";
    }
}

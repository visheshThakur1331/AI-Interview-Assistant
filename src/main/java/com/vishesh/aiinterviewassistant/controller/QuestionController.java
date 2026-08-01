package com.vishesh.aiinterviewassistant.controller;

import com.vishesh.aiinterviewassistant.entity.Question;
import com.vishesh.aiinterviewassistant.entity.Result;
import com.vishesh.aiinterviewassistant.service.QuestionService;
import com.vishesh.aiinterviewassistant.service.ResultService;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private ResultService resultService;

    @GetMapping("/questions")
    public String showQuestions(Model model) {

        model.addAttribute("questions",
                questionService.getAllQuestions());

        return "questions";
    }

    @GetMapping("/questions/{category}")
    public String showQuestionsByCategory(
            @PathVariable String category,
            Model model) {

        model.addAttribute("questions",
                questionService.getQuestionsByCategory(category));

        model.addAttribute("category", category);

        return "questions";
    }

    @GetMapping("/result")
    public String result() {
        return "result";
    }

    @PostMapping("/submit")
    public String submitInterview(
            @RequestParam Map<String, String> answers,
            @RequestParam String category,
            Model model,
            HttpSession session) {

        List<Question> questions =
                questionService.getQuestionsByCategory(category);

        int score = 0;

        for (Question q : questions) {

            String selectedAnswer = answers.get("q" + q.getId());

            if (selectedAnswer != null &&
                    selectedAnswer.equals(q.getCorrectAnswer())) {

                score++;
            }
        }

        String userEmail = (String) session.getAttribute("userEmail");

        String userName = (String) session.getAttribute("userName");

        if (userName == null || userName.isBlank()) {
            userName = userEmail;
        }

        Result result = new Result();

        result.setUserEmail(userEmail);
        result.setUserName(userName);
        result.setCategory(category);
        result.setScore(score);
        result.setTotal(questions.size());
        result.setInterviewDate(LocalDateTime.now());

        resultService.saveResult(result);

        String feedback;
        String recommendation;

        double percentage = (score * 100.0) / questions.size();

        if (percentage >= 80) {

            feedback = "Excellent Performance! 🎉";
            recommendation = "Try Advanced Java, Spring Boot, Microservices and System Design.";

        } else if (percentage >= 60) {

            feedback = "Good Performance 👍";
            recommendation = "Focus on Spring Boot, SQL and REST APIs.";

        } else {

            feedback = "Need Improvement 📚";
            recommendation = "Practice Java Basics, OOP Concepts and Collections.";
        }
        String badge;

        if (score == questions.size()) {

            badge = "🏆 Excellent";

        } else if (score >= questions.size() * 0.75) {

            badge = "🥇 Very Good";

        } else if (score >= questions.size() * 0.50) {

            badge = "🥈 Good";

        } else {

            badge = "📚 Keep Practicing";

        }
        model.addAttribute("badge", badge);
        model.addAttribute("feedback", feedback);
        model.addAttribute("recommendation", recommendation);
        model.addAttribute("score", score);
        model.addAttribute("total", questions.size());

        return "result";
    }
}
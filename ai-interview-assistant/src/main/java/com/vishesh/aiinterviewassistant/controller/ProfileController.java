package com.vishesh.aiinterviewassistant.controller;

import com.vishesh.aiinterviewassistant.service.ResultService;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProfileController {

    @Autowired
    private ResultService resultService;

    @GetMapping("/profile")
    public String profile(
            HttpSession session,
            Model model) {

        String userEmail =
                (String) session.getAttribute("userEmail");

        model.addAttribute("email", userEmail);

        model.addAttribute(
                "bestScore",
                resultService.getBestScore(userEmail));

        model.addAttribute(
                "totalInterviews",
                resultService.getTotalInterviews(userEmail));

        model.addAttribute(
                "averageScore",
                resultService.getAverageScore(userEmail));

        model.addAttribute(
                "latestCategory",
                resultService.getLatestCategory(userEmail));

        model.addAttribute(
                "latestDate",
                resultService.getLatestInterviewDate(userEmail));

        return "profile";
    }
}
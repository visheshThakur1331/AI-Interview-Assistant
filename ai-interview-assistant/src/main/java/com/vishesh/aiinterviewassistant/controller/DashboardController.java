package com.vishesh.aiinterviewassistant.controller;

import com.vishesh.aiinterviewassistant.service.ResultService;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @Autowired
    private ResultService resultService;

    @GetMapping("/dashboard")
    public String dashboard(
            HttpSession session,
            Model model) {

        String userEmail =
                (String) session.getAttribute("userEmail");

        model.addAttribute(
                "bestScore",
                resultService.getBestScore(userEmail));

        model.addAttribute(
                "totalInterviews",
                resultService.getTotalInterviews(userEmail));

        double average = resultService.getAverageScore(userEmail);

        model.addAttribute("averageScore", average);
        model.addAttribute(
                "latestCategory",
                resultService.getLatestCategory(userEmail));

        model.addAttribute(
                "latestDate",
                resultService.getLatestInterviewDate(userEmail));

        model.addAttribute(
                "javaCount",
                resultService.getJavaCount(userEmail));

        model.addAttribute(
                "springCount",
                resultService.getSpringCount(userEmail));

        model.addAttribute(
                "oopCount",
                resultService.getOopCount(userEmail));
        return "dashboard";
    }
}
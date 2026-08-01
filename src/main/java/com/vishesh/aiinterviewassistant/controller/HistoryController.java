package com.vishesh.aiinterviewassistant.controller;

import com.vishesh.aiinterviewassistant.repository.ResultRepository;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HistoryController {

    @Autowired
    private ResultRepository resultRepository;

    @GetMapping("/history")
    public String history(
            HttpSession session,
            Model model) {

        String userEmail =
                (String) session.getAttribute("userEmail");

        model.addAttribute(
                "historyList",
                resultRepository.findByUserEmail(userEmail));

        return "history";
    }
}
package com.vishesh.aiinterviewassistant.controller;

import com.vishesh.aiinterviewassistant.service.ResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LeaderboardController {

    @Autowired
    private ResultService resultService;

    @GetMapping("/leaderboard")
    public String leaderboard(Model model) {

        model.addAttribute(
                "leaders",
                resultService.getLeaderboard());

        return "leaderboard";
    }
}
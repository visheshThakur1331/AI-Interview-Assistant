package com.vishesh.aiinterviewassistant.service;

import com.vishesh.aiinterviewassistant.entity.Result;
import com.vishesh.aiinterviewassistant.repository.ResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResultService {

    @Autowired
    private ResultRepository resultRepository;

    public Result saveResult(Result result) {
        return resultRepository.save(result);
    }

    public int getBestScore(String userEmail) {

        List<Result> results =
                resultRepository.findByUserEmail(userEmail);

        int bestScore = 0;

        for (Result r : results) {

            if (r.getScore() > bestScore) {
                bestScore = r.getScore();
            }
        }

        return bestScore;
    }

    public int getTotalInterviews(String userEmail) {

        return resultRepository
                .findByUserEmail(userEmail)
                .size();
    }

    public Result getLatestResult(String userEmail) {

        return resultRepository
                .findTopByUserEmailOrderByInterviewDateDesc(userEmail);
    }

    public List<Result> getLeaderboard() {

        return resultRepository
                .findAllByOrderByScoreDesc();
    }

    public double getAverageScore(String userEmail) {

        List<Result> results = resultRepository.findByUserEmail(userEmail);

        if (results.isEmpty()) {
            return 0;
        }

        int totalScore = 0;
        int totalQuestions = 0;

        for (Result r : results) {
            totalScore += r.getScore();
            totalQuestions += r.getTotal();
        }

        double percentage = ((double) totalScore / totalQuestions) * 100;

        return Math.round(percentage * 100.0) / 100.0;
    }
    public String getLatestCategory(String userEmail) {

        Result latest =
                resultRepository
                        .findTopByUserEmailOrderByInterviewDateDesc(userEmail);

        if (latest == null) {
            return "N/A";
        }

        return latest.getCategory();
    }

    public String getLatestInterviewDate(String userEmail) {

        Result latest =
                resultRepository
                        .findTopByUserEmailOrderByInterviewDateDesc(userEmail);

        if (latest == null) {
            return "N/A";
        }

        return latest.getInterviewDate().toString();
    }

    public long getJavaCount(String userEmail) {

        return resultRepository
                .findByUserEmail(userEmail)
                .stream()
                .filter(r -> "Java".equalsIgnoreCase(r.getCategory()))
                .count();
    }

    public long getSpringCount(String userEmail) {

        return resultRepository
                .findByUserEmail(userEmail)
                .stream()
                .filter(r -> "Spring Boot".equalsIgnoreCase(r.getCategory()))
                .count();
    }

    public long getOopCount(String userEmail) {

        return resultRepository
                .findByUserEmail(userEmail)
                .stream()
                .filter(r -> "OOP".equalsIgnoreCase(r.getCategory()))
                .count();
    }
    // ===== PHASE 10.5 =====

    public List<Result> getUserHistory(String userEmail) {

        return resultRepository
                .findByUserEmailOrderByInterviewDateDesc(userEmail);
    }

    public List<Result> getTopPerformers() {

        return resultRepository
                .findTop10ByOrderByScoreDesc();
    }

    public List<Result> getRecentInterviews() {

        return resultRepository
                .findTop10ByOrderByInterviewDateDesc();
    }
}
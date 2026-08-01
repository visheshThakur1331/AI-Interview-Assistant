package com.vishesh.aiinterviewassistant.repository;

import com.vishesh.aiinterviewassistant.entity.Result;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResultRepository extends JpaRepository<Result, Long> {

    List<Result> findByUserEmail(String userEmail);

    Result findTopByUserEmailOrderByInterviewDateDesc(String userEmail);

    List<Result> findAllByOrderByScoreDesc();

    List<Result> findByUserEmailOrderByInterviewDateDesc(String userEmail);

    List<Result> findTop10ByOrderByScoreDesc();

    List<Result> findTop10ByOrderByInterviewDateDesc();
}
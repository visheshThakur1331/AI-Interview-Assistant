package com.vishesh.aiinterviewassistant.repository;

import com.vishesh.aiinterviewassistant.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    List<Question> findByCategory(String category);

    List<Question> findByQuestionTextContainingIgnoreCase(String keyword);
}
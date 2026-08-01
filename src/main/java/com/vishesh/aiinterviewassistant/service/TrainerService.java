package com.vishesh.aiinterviewassistant.service;

import com.vishesh.aiinterviewassistant.entity.Trainer;
import com.vishesh.aiinterviewassistant.repository.TrainerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainerService {

    @Autowired
    private TrainerRepository trainerRepository;

    // Register Trainer
    public Trainer saveTrainer(Trainer trainer) {
        return trainerRepository.save(trainer);
    }

    // Trainer Login
    public Trainer loginTrainer(String email, String password) {

        Trainer trainer = trainerRepository.findByEmail(email);

        if (trainer != null && trainer.getPassword().equals(password)) {
            return trainer;
        }

        return null;
    }

    // View All Trainers
    public List<Trainer> getAllTrainers() {
        return trainerRepository.findAll();
    }

    // Delete Trainer
    public void deleteTrainer(Long id) {
        trainerRepository.deleteById(id);
    }
}
package com.vishesh.aiinterviewassistant.repository;

import com.vishesh.aiinterviewassistant.entity.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrainerRepository extends JpaRepository<Trainer, Long> {

    Trainer findByEmail(String email);

}
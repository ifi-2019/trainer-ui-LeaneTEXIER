package com.ifi.trainer_ui.trainers.service;

import com.ifi.trainer_ui.trainers.bo.Trainer;

import java.util.List;

public interface TrainerService {

    List<Trainer> listTrainers(String name);

    Trainer getTrainer(String name);

}

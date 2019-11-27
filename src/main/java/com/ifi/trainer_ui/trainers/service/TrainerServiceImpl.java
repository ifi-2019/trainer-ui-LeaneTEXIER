package com.ifi.trainer_ui.trainers.service;

import com.ifi.trainer_ui.trainers.bo.Trainer;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TrainerServiceImpl implements TrainerService {

    public List<Trainer> listTrainers(){
        var arr = new ArrayList<Trainer>();
        arr.add(new Trainer());
        return arr;
    }
}

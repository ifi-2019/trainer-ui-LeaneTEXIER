package com.ifi.trainer_ui.controller;

import com.ifi.trainer_ui.trainers.service.TrainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TrainerController {

    private TrainerService trainerService;

    @Autowired
    public void setTrainerService(TrainerService trainerService) {
        this.trainerService = trainerService;
    }

    @GetMapping("/trainers")
    ModelAndView trainers(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        var modelAndView = new ModelAndView("trainers");
        modelAndView.addObject("trainers", trainerService.listTrainers(auth.getName()));
        return modelAndView;
    }

    @GetMapping("/trainer/{name}")
    ModelAndView trainer(@PathVariable String name){
        var modelAndView = new ModelAndView("trainer");
        modelAndView.addObject("trainer", trainerService.getTrainer(name));
        return modelAndView;
    }

    @GetMapping("/profile")
    ModelAndView profile(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        var modelAndView = new ModelAndView("profile");
        modelAndView.addObject("profile", trainerService.getTrainer(auth.getName()));
        return modelAndView;
    }

}

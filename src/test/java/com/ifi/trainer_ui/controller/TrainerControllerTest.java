package com.ifi.trainer_ui.controller;

import com.ifi.trainer_ui.pokemonTypes.bo.PokemonType;
import com.ifi.trainer_ui.pokemonTypes.service.PokemonTypeService;
import com.ifi.trainer_ui.trainers.bo.Trainer;
import com.ifi.trainer_ui.trainers.service.TrainerService;
import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TrainerControllerTest {

    @Test
    void controllerShouldBeAnnotated(){
        assertNotNull(TrainerController.class.getAnnotation(Controller.class));
    }

    @Test
    void trainers_shouldReturnAModelAndView() {
        var trainerService = mock(TrainerService.class);

        when(trainerService.listTrainers()).thenReturn(List.of(new Trainer(), new Trainer()));

        var trainerController = new TrainerController();
        trainerController.setTrainerService(trainerService);
        var modelAndView = trainerController.trainers();

        assertEquals("trainers", modelAndView.getViewName());
        var trainers = (List<Trainer>)modelAndView.getModel().get("trainers");
        assertEquals(2, trainers.size());
        verify(trainerService).listTrainers();
    }

    @Test
    void trainers_shouldBeAnnotated() throws NoSuchMethodException {
        var trainersMethod = TrainerController.class.getDeclaredMethod("trainers");
        var getMapping = trainersMethod.getAnnotation(GetMapping.class);

        assertNotNull(getMapping);
        assertArrayEquals(new String[]{"/trainers"}, getMapping.value());
    }

    @Test
    void trainer_shouldReturnAModelAndView() {
        var trainerService = mock(TrainerService.class);

        when(trainerService.getTrainer("Misty")).thenReturn(new Trainer("Misty"));

        var trainerController = new TrainerController();
        trainerController.setTrainerService(trainerService);
        var modelAndView = trainerController.trainers("Misty");

        assertEquals("trainer", modelAndView.getViewName());
        var trainer = (Trainer)modelAndView.getModel().get("trainer");
        assertEquals("Misty", trainer.getName());
        verify(trainerService).getTrainer("Misty");
    }

//    @Test
//    void trainers_shouldBeAnnotated() throws NoSuchMethodException {
//        var trainersMethod = TrainerController.class.getDeclaredMethod("trainers");
//        var getMapping = trainersMethod.getAnnotation(GetMapping.class);
//
//        assertNotNull(getMapping);
//        assertArrayEquals(new String[]{"/trainers"}, getMapping.value());
//    }
}

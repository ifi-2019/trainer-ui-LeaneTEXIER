package com.ifi.trainer_ui.controller;

import com.ifi.trainer_ui.trainers.bo.Trainer;
import com.ifi.trainer_ui.trainers.service.TrainerService;
import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TrainerControllerTest {

    @Test
    void controllerShouldBeAnnotated(){
        assertNotNull(TrainerController.class.getAnnotation(Controller.class));
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
        var modelAndView = trainerController.trainer("Misty");

        assertEquals("trainer", modelAndView.getViewName());
        var trainer = (Trainer)modelAndView.getModel().get("trainer");
        assertEquals("Misty", trainer.getName());
        verify(trainerService).getTrainer("Misty");
    }

    @Test
    void trainer_shouldBeAnnotated() throws NoSuchMethodException {
        var trainerMethod =
                TrainerController.class.getDeclaredMethod("trainer", String.class);
        var getMapping = trainerMethod.getAnnotation(GetMapping.class);

        var pathVariableAnnotation = trainerMethod.getParameters()[0].getAnnotation(PathVariable.class);

        assertNotNull(getMapping);
        assertArrayEquals(new String[]{"/trainer/{name}"}, getMapping.value());

        assertNotNull(pathVariableAnnotation);
    }

    @Test
    void profile_shouldBeAnnotated() throws NoSuchMethodException {
        var profileMethod =
                TrainerController.class.getDeclaredMethod("profile");
        var getMapping = profileMethod.getAnnotation(GetMapping.class);

        assertNotNull(getMapping);
        assertArrayEquals(new String[]{"/profile"}, getMapping.value());
    }
}

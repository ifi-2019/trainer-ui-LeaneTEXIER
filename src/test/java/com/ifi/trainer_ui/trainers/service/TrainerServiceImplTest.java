package com.ifi.trainer_ui.trainers.service;

import com.ifi.trainer_ui.pokemonTypes.service.PokemonTypeService;
import com.ifi.trainer_ui.trainers.bo.Pokemon;
import com.ifi.trainer_ui.trainers.bo.Trainer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

class TrainerServiceImplTest {

    @Test
    void trainerServiceImpl_shouldBeAnnotatedWithService(){
        assertNotNull(TrainerServiceImpl.class.getAnnotation(Service.class));
    }

    @Test
    void listTrainers_shouldCallTheRemoteService() {
        var url = "http://localhost:8080";

        var restTemplate = mock(RestTemplate.class);
        var trainerServiceImpl = new TrainerServiceImpl();
        var pokemonTypeService = mock(PokemonTypeService.class);
        trainerServiceImpl.setRestTemplate(restTemplate);
        trainerServiceImpl.setTrainerServiceUrl(url);
        trainerServiceImpl.setPokemonTypeService(pokemonTypeService);

        var expectedUrl = "http://localhost:8080/trainers/{name}";
        when(restTemplate.getForObject(expectedUrl, Trainer[].class, "Ash")).thenReturn(new Trainer[]{new Trainer(), new Trainer()});

        var trainers = trainerServiceImpl.listTrainers("Ash");

        assertNotNull(trainers);
        assertEquals(2, trainers.size());

        verify(restTemplate).getForObject(expectedUrl, Trainer[].class, "Ash");
    }

    @Test
    void getTrainer_shouldCallTheRemoteService() {
        var url = "http://localhost:8080";

        var restTemplate = mock(RestTemplate.class);
        var trainerServiceImpl = new TrainerServiceImpl();
        var pokemonTypeService = mock(PokemonTypeService.class);
        trainerServiceImpl.setRestTemplate(restTemplate);
        trainerServiceImpl.setTrainerServiceUrl(url);
        trainerServiceImpl.setPokemonTypeService(pokemonTypeService);

        var pikachu = new Pokemon();
        pikachu.setPokemonType(25);
        pikachu.setLevel(18);

        var ash = new Trainer();
        ash.setName("Ash");
        ash.setTeam(Arrays.asList(pikachu));

        var expectedUrl = "http://localhost:8080/trainer/{name}";
        when(restTemplate.getForObject(expectedUrl, Trainer.class, "Ash")).thenReturn(ash);

        var result_ash = trainerServiceImpl.getTrainer("Ash");

        assertNotNull(result_ash);
        assertEquals("Ash", result_ash.getName());
        assertEquals(1, result_ash.getTeam().size());
        assertEquals(25, result_ash.getTeam().get(0).getPokemonType());
        assertEquals(18, result_ash.getTeam().get(0).getLevel());

        verify(restTemplate).getForObject(expectedUrl, Trainer.class, "Ash");
    }

    @Test
    void setRestTemplate_shouldBeAnnotatedWithAutowired() throws NoSuchMethodException {
        var setRestTemplateMethod = TrainerServiceImpl.class.getDeclaredMethod("setRestTemplate", RestTemplate.class);
        assertNotNull(setRestTemplateMethod.getAnnotation(Autowired.class));
    }

    @Test
    void setTrainerServiceUrl_shouldBeAnnotatedWithValue() throws NoSuchMethodException {
        var setter = TrainerServiceImpl.class.getDeclaredMethod("setTrainerServiceUrl", String.class);
        var valueAnnotation = setter.getAnnotation(Value.class);
        assertNotNull(valueAnnotation);
        assertEquals("${trainer.service.url}", valueAnnotation.value());
    }

    @Test
    void setPokemonTypeService_shouldBeAnnotatedWithAutowired() throws NoSuchMethodException {
        var setPokemonTypeServiceMethod = TrainerServiceImpl.class.getDeclaredMethod("setPokemonTypeService", PokemonTypeService.class);
        assertNotNull(setPokemonTypeServiceMethod.getAnnotation(Autowired.class));
    }
}

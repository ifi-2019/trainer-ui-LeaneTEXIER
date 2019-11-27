package com.ifi.trainer_ui.trainers.service;

import com.ifi.trainer_ui.pokemonTypes.service.PokemonTypeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import org.junit.jupiter.api.Test;
import static org.junit.Assert.assertNotNull;

@SpringBootTest
public class TrainerServiceIntegrationTest {

    @Autowired
    private TrainerService service;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private PokemonTypeService pokemonTypeService;

    @Test
    void serviceAndTemplateShouldNotBeNull(){
        assertNotNull(service);
        assertNotNull(restTemplate);
        assertNotNull(pokemonTypeService);
    }

}
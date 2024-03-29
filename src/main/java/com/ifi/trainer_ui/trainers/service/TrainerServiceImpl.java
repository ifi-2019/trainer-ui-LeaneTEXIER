package com.ifi.trainer_ui.trainers.service;

import com.ifi.trainer_ui.pokemonTypes.service.PokemonTypeService;
import com.ifi.trainer_ui.trainers.bo.Pokemon;
import com.ifi.trainer_ui.trainers.bo.Trainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class TrainerServiceImpl implements TrainerService {

    private RestTemplate restTemplate;
    private String trainerServiceUrl;
    private PokemonTypeService pokemonTypeService;

    public List<Trainer> listTrainers(String name){
        var url = trainerServiceUrl + "/trainers/{name}";
        var trainers = Arrays.asList(restTemplate.getForObject(url, Trainer[].class, name));
        for(Trainer trainer : trainers){
            for (Pokemon pokemon: trainer.getTeam()) {
                pokemon.setType(pokemonTypeService.getPokemonType(pokemon.getPokemonType()));
            }
        }
        return trainers;
    }

    public Trainer getTrainer(String name) {
        var url = trainerServiceUrl + "/trainer/{name}";
        var trainer = restTemplate.getForObject(url, Trainer.class, name);
        for (Pokemon pokemon: trainer.getTeam()) {
            pokemon.setType(pokemonTypeService.getPokemonType(pokemon.getPokemonType()));
        }
        return trainer;
    }

    @Autowired
    @Qualifier("trainerApiRestTemplate")
    void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Value("${trainer.service.url}")
    void setTrainerServiceUrl(String trainerServiceUrl) {
        this.trainerServiceUrl = trainerServiceUrl;
    }

    @Autowired
    void setPokemonTypeService(PokemonTypeService pokemonTypeService) {
        this.pokemonTypeService = pokemonTypeService;
    }
}

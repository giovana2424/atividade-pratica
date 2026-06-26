package br.com.atividade.controller;

import br.com.atividade.model.Animal;
import br.com.atividade.service.AnimalService;

import java.sql.SQLException;
import java.util.List;

public class AnimalController {
    private AnimalService animalService;

    public AnimalController(AnimalService animalService) {
        this.animalService = animalService;
    }

    public List<Animal> buscarAnimaisPorTutor(Long idTutor) throws SQLException {
        return animalService.listarAnimaisDoTutor(idTutor);
    }
}

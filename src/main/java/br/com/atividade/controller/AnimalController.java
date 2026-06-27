package br.com.atividade.controller;

import br.com.atividade.model.Animal;
import br.com.atividade.repository.AnimalRepository;
import br.com.atividade.repository.TutorRepository;
import br.com.atividade.service.AnimalService;

import java.sql.SQLException;
import java.util.List;

public class AnimalController {
    private final AnimalService animalService;

    public AnimalController() {
        AnimalRepository animalRepository = new AnimalRepository();
        TutorRepository tutorRepository = new TutorRepository();
        this.animalService = new AnimalService(animalRepository, tutorRepository);
    }

    public Animal cadastrarAnimal(String nome, String especie, String raca, Long idTutor){
        try{
            return animalService.cadastrarAnimal(nome, especie, raca, idTutor);

        } catch (IllegalArgumentException e) {
            System.out.println("Erro de validação: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Erro ao salvar animal: " + e.getMessage());
        }
        return null;
    }

    public List<Animal> buscarAnimaisPorTutor(Long idTutor) throws SQLException {
        return animalService.listarAnimaisDoTutor(idTutor);
    }
}

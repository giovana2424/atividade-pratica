package br.com.atividade.service;

import br.com.atividade.model.Animal;
import br.com.atividade.repository.AnimalRepository;

import java.sql.SQLException;
import java.util.List;

public class AnimalService {
    AnimalRepository animalRepository = new AnimalRepository();

    public List<Animal> listarAnimaisDoTutor(Long idTutor) throws SQLException {
        if (idTutor == null) {
            throw new IllegalArgumentException("O ID do tutor não pode ser nulo.");
        }

        return animalRepository.buscarPorTutor(idTutor);
    }
}

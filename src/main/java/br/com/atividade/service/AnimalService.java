package br.com.atividade.service;

import br.com.atividade.model.Animal;
import br.com.atividade.model.Endereco;
import br.com.atividade.model.Tutor;
import br.com.atividade.repository.AnimalRepository;
import br.com.atividade.repository.TutorRepository;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class AnimalService {
    private final AnimalRepository animalRepository;
    private final TutorRepository tutorRepository;

    public AnimalService(AnimalRepository animalRepository, TutorRepository tutorRepository){
        this.animalRepository = animalRepository;
        this.tutorRepository = tutorRepository;
    }

    public Animal cadastrarAnimal(String nome, String especie, String raca, Long idTutor) throws SQLException {
        Optional<Tutor> tutorOpt = tutorRepository.buscarPorId(idTutor);

        Tutor tutor = tutorOpt.orElseThrow(()->
                new IllegalArgumentException("Não foi possível cadastrar animal: Tutor com ID " + idTutor + " não existe.")
        );

        Animal novoAnimal = new Animal(null, nome, especie, raca, tutor);
        return animalRepository.salvar(novoAnimal);
    }

    public List<Animal> listarAnimaisDoTutor(Long idTutor) throws SQLException {
        if (idTutor == null) {
            throw new IllegalArgumentException("O ID do tutor não pode ser nulo.");
        }

        return animalRepository.buscarPorTutor(idTutor);
    }
}

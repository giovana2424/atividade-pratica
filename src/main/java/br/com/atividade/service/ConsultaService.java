package br.com.atividade.service;

import br.com.atividade.model.Animal;
import br.com.atividade.model.Consulta;
import br.com.atividade.repository.AnimalRepository;
import br.com.atividade.repository.ConsultaRepository;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class ConsultaService {
    private ConsultaRepository consultaRepository = new ConsultaRepository();
    private AnimalRepository animalRepository = new AnimalRepository();

    public Consulta registrar(LocalDate data, String motivo, BigDecimal valor, Long idAnimal) throws SQLException {
        Optional<Animal> animalOpt = animalRepository.buscarPorId(idAnimal);

        Animal animal = animalOpt.orElseThrow(() ->
                new IllegalArgumentException("Não foi possível registrar a consulta: Animal com ID " + idAnimal + " não existe.")
        );

        Consulta novaConsulta = new Consulta(null, animal, data, motivo, valor);
        return consultaRepository.salvar(novaConsulta);
    }

    public List<Consulta> listarConsultas() throws SQLException {
        return consultaRepository.listarTodos();
    }

    public List<Consulta> listarConsultasPorAnimal(Long idAinmal) throws SQLException {
        return consultaRepository.buscarPorAnimal(idAinmal);
    }
}

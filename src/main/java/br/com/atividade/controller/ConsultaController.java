package br.com.atividade.controller;

import br.com.atividade.model.Consulta;
import br.com.atividade.repository.AnimalRepository;
import br.com.atividade.repository.ConsultaRepository;
import br.com.atividade.service.ConsultaService;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class ConsultaController {
    private final ConsultaService consultaService;

    public ConsultaController(){
        ConsultaRepository consultaRepository = new ConsultaRepository();
        AnimalRepository animalRepository = new AnimalRepository();
        this.consultaService = new ConsultaService(consultaRepository, animalRepository);
    }

    public Consulta registrarConsulta(LocalDate data, String motivo, BigDecimal valor, Long idAnimal){
        try{
            return consultaService.registrar(data, motivo, valor, idAnimal);

        } catch (IllegalArgumentException e) {
            System.out.println("Erro de validação: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Erro ao salvar venda: " + e.getMessage());
        }
        return null;
    }

    public List<Consulta> buscarConsultas() throws SQLException {
        return consultaService.listarConsultas();
    }

    public List<Consulta> buscarConsultasPorAnimal(Long idAnimal) throws SQLException {
        return consultaService.listarConsultasPorAnimal(idAnimal);
    }
}

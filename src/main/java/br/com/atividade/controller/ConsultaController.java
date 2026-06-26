package br.com.atividade.controller;

import br.com.atividade.model.Animal;
import br.com.atividade.model.Consulta;
import br.com.atividade.service.ConsultaService;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class ConsultaController {
    private ConsultaService consultaService;

    public ConsultaController(ConsultaService consultaService){
        this.consultaService = consultaService;
    }

    public Consulta registrarConsulta(LocalDate data, String motivo, BigDecimal valor, Animal animal){
        try{
            return consultaService.registrar(data, motivo, valor, animal.getId());

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

package br.com.atividade.controller;

import br.com.atividade.model.Cliente;
import br.com.atividade.model.Veiculo;
import br.com.atividade.repository.ClienteRepository;
import br.com.atividade.repository.VeiculoRepository;
import br.com.atividade.service.VeiculoService;

import java.sql.SQLException;
import java.util.List;

public class VeiculoController {
    private final VeiculoService veiculoService;

    public VeiculoController() {
        VeiculoRepository veiculoRepository = new VeiculoRepository();
        ClienteRepository clienteRepository = new ClienteRepository();
        this.veiculoService = new VeiculoService(veiculoRepository, clienteRepository);
    }

    public Veiculo cadastrarVeiculo(String placa, String modelo, Integer ano, Long idCliente){
        try{
            return veiculoService.cadastrarVeiculo(placa, modelo, ano, idCliente);

        } catch (IllegalArgumentException e) {
            System.out.println("Erro de validação: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Erro ao salvar veículo: " + e.getMessage());
        }
        return null;
    }

    public List<Veiculo> buscarVeiculosPorCliente(Long idCliente) throws SQLException {
        return veiculoService.listarVeiculosDoCliente(idCliente);
    }
}

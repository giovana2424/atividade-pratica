package br.com.atividade.controller;

import br.com.atividade.model.OrdemServico;
import br.com.atividade.repository.OrdemServicoRepository;
import br.com.atividade.repository.VeiculoRepository;
import br.com.atividade.service.OrdemServicoService;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public class OrdemServicoController {
    private final OrdemServicoService ordemServicoService;

    public OrdemServicoController(){
        OrdemServicoRepository ordemServicoRepository = new OrdemServicoRepository();
        VeiculoRepository veiculoRepository = new VeiculoRepository();
        this.ordemServicoService = new OrdemServicoService(ordemServicoRepository, veiculoRepository);
    }

    public OrdemServico registrarOrdemServico(String descricao, BigDecimal valor, Long idVeiculo){
        try{
            return ordemServicoService.registrar(descricao, valor, idVeiculo);

        } catch (IllegalArgumentException e) {
            System.out.println("Erro de validação: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Erro ao salvar ordem de serviço: " + e.getMessage());
        }
        return null;
    }

    public void finalizarOrdemServico(Long idOrdemServico){
        try{
            ordemServicoService.finalizar(idOrdemServico);

        } catch (IllegalArgumentException e) {
            System.out.println("Erro de validação: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Erro ao finalizar ordem de serviço: " + e.getMessage());
        }
    }

    public List<OrdemServico> buscarOrdensDeServico() throws SQLException {
        return ordemServicoService.listarOrdensServico();
    }

    public List<OrdemServico> buscarOrdensDeServicoPorVeiculo(Long idVeiculo) throws SQLException {
        return ordemServicoService.listarOrdensServicoPorVeiculo(idVeiculo);
    }
}

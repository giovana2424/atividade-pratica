package br.com.atividade.service;

import br.com.atividade.model.OrdemServico;
import br.com.atividade.model.Veiculo;
import br.com.atividade.repository.OrdemServicoRepository;
import br.com.atividade.repository.VeiculoRepository;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class OrdemServicoService {
    private final OrdemServicoRepository ordemServicoRepository;
    private final  VeiculoRepository veiculoRepository;

    public OrdemServicoService(OrdemServicoRepository ordemServicoRepository, VeiculoRepository veiculoRepository) {
        this.ordemServicoRepository = ordemServicoRepository;
        this.veiculoRepository = veiculoRepository;
    }

    public OrdemServico registrar(String descricao, BigDecimal valor, Long idVeiculo) throws SQLException {
        Optional<Veiculo> veiculoOpt = veiculoRepository.buscarPorId(idVeiculo);

        Veiculo veiculo = veiculoOpt.orElseThrow(() ->
                new IllegalArgumentException("Não foi possível registrar a ordem de serviço: Veículo com ID " + idVeiculo + " não existe.")
        );

        OrdemServico novaOrdemServico = new OrdemServico(null, descricao, valor, veiculo);
        return ordemServicoRepository.salvar(novaOrdemServico);
    }

    public void finalizar(Long idOrdemServico) throws SQLException{
        Optional<OrdemServico> ordemServicoOpt = ordemServicoRepository.buscarPorId(idOrdemServico);

        OrdemServico ordemServico = ordemServicoOpt.orElseThrow(() ->
                new IllegalArgumentException("Não foi possível finalizar a ordem de serviço: Ordem de serviço com ID " + idOrdemServico + " não existe.")
        );

        ordemServico.finalizar();

        ordemServicoRepository.atualizar(ordemServico);
    }

    public List<OrdemServico> listarOrdensServico() throws SQLException { return ordemServicoRepository.listarTodos(); }

    public List<OrdemServico> listarOrdensServicoPorVeiculo(Long idVeiculo) throws SQLException { return ordemServicoRepository.buscarPorVeiculo(idVeiculo); }
}

package br.com.atividade.service;

import br.com.atividade.model.Cliente;
import br.com.atividade.model.Veiculo;
import br.com.atividade.repository.ClienteRepository;
import br.com.atividade.repository.VeiculoRepository;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class VeiculoService {
    private final VeiculoRepository veiculoRepository;
    private final ClienteRepository clienteRepository;

    public VeiculoService(VeiculoRepository veiculoRepository, ClienteRepository clienteRepository) {
        this.veiculoRepository = veiculoRepository;
        this.clienteRepository = clienteRepository;
    }

    public Veiculo cadastrarVeiculo(String placa, String modelo, Integer ano, Long idCliente) throws SQLException{
        Optional<Cliente> clienteOpt = clienteRepository.buscarPorId(idCliente);

        Cliente cliente = clienteOpt.orElseThrow(() ->
                new IllegalArgumentException("Não foi possível cadastrar o veículo: Cliente com ID " + idCliente + " não existe.")
        );
        Veiculo novoVeiculo = new Veiculo(null, placa, modelo, ano, cliente);
        return veiculoRepository.salvar(novoVeiculo);
    }

    public List<Veiculo> listarVeiculosDoCliente(Long idCliente) throws SQLException {
        if (idCliente == null) {
            throw new IllegalArgumentException("O ID do cliente não pode ser nulo.");
        }

        return  veiculoRepository.buscarPorCliente(idCliente);
    }
}

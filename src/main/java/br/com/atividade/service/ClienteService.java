package br.com.atividade.service;

import br.com.atividade.model.Cliente;
import br.com.atividade.repository.ClienteRepository;

import java.sql.SQLException;

public class ClienteService {
    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public Cliente cadastrarCliente(String nome, String telefone) throws SQLException {
        Cliente novoCliente = new Cliente(null, nome, telefone);
        return clienteRepository.salvar(novoCliente);
    }
}

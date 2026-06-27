package br.com.atividade.controller;

import br.com.atividade.model.Cliente;
import br.com.atividade.repository.ClienteRepository;
import br.com.atividade.service.ClienteService;

import java.sql.SQLException;

public class ClienteController {
    private final ClienteService clienteService;

    public ClienteController() {
        ClienteRepository repository = new ClienteRepository();
        this.clienteService = new ClienteService(repository);
    }

    public Cliente cadastrarCliente(String nome, String telefone){
        try{
            return clienteService.cadastrarCliente(nome, telefone);

        } catch (IllegalArgumentException e) {
            System.out.println("Erro de validação: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Erro ao salvar cliente: " + e.getMessage());
        }
        return null;
    }
}

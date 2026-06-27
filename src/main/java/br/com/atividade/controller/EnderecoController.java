package br.com.atividade.controller;

import br.com.atividade.model.Endereco;
import br.com.atividade.repository.EnderecoRepository;
import br.com.atividade.service.EnderecoService;

import java.sql.SQLException;

public class EnderecoController {
    private final EnderecoService enderecoService;

    public EnderecoController() {
        EnderecoRepository enderecoRepository = new EnderecoRepository();
        this.enderecoService = new EnderecoService(enderecoRepository);
    }

    public Endereco cadastrarEndereco(String logradouro, String numero, String bairro, String cidade, String uf, String cep){
        try{
            return enderecoService.cadastrarEndereco(logradouro, numero, bairro, cidade, uf, cep);

        } catch (IllegalArgumentException e) {
            System.out.println("Erro de validação: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Erro ao salvar endereço: " + e.getMessage());
        }
        return null;
    }
}

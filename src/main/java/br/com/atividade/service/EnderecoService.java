package br.com.atividade.service;

import br.com.atividade.model.Endereco;
import br.com.atividade.repository.EnderecoRepository;

import java.sql.SQLException;

public class EnderecoService {
    private final EnderecoRepository enderecoRepository;

    public EnderecoService(EnderecoRepository enderecoRepository) {
        this.enderecoRepository = enderecoRepository;
    }

    public Endereco cadastrarEndereco(String logradouro, String numero, String bairro, String cidade, String uf, String cep) throws SQLException {
        Endereco novoEndereco = new Endereco(null, logradouro, numero, bairro, cidade, uf, cep);
        return enderecoRepository.salvar(novoEndereco);
    }
}

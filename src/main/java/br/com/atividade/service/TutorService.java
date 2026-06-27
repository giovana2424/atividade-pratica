package br.com.atividade.service;

import br.com.atividade.model.Endereco;
import br.com.atividade.model.Tutor;
import br.com.atividade.repository.EnderecoRepository;
import br.com.atividade.repository.TutorRepository;

import java.sql.SQLException;
import java.util.Optional;

public class TutorService {
    private final TutorRepository tutorRepository;
    private final EnderecoRepository enderecoRepository;

    public TutorService(TutorRepository tutorRepository, EnderecoRepository enderecoRepository) {
        this.tutorRepository = tutorRepository;
        this.enderecoRepository = enderecoRepository;
    }

    public Tutor cadastrarTutor(String nome, Long idEndereco, String telefone) throws SQLException {
        Optional<Endereco> enderecoOpt = enderecoRepository.buscarPorId(idEndereco);

        Endereco endereco = enderecoOpt.orElseThrow(()->
                new IllegalArgumentException("Não foi possível cadastrar o tutor: Endereço com ID " + idEndereco + " não existe.")
        );

        Tutor novoTutor = new Tutor(null, nome, endereco, telefone);
        return tutorRepository.salvar(novoTutor);
    }
}

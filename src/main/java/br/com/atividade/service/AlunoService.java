package br.com.atividade.service;

import br.com.atividade.model.Aluno;
import br.com.atividade.repository.AlunoRepository;

import java.sql.SQLException;

public class AlunoService {
    private final AlunoRepository alunoRepository;

    public AlunoService(AlunoRepository alunoRepository) {
        this.alunoRepository = alunoRepository;
    }

    public Aluno cadastrarAluno(String nome, String email, String telefone) throws SQLException {
        Aluno aluno = new Aluno(null, nome, email, telefone);
        return alunoRepository.salvar(aluno);
    }
}

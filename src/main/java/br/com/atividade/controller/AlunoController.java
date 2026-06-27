package br.com.atividade.controller;

import br.com.atividade.model.Aluno;
import br.com.atividade.repository.AlunoRepository;
import br.com.atividade.service.AlunoService;

import java.sql.SQLException;

public class AlunoController {
    private final AlunoService alunoService;

    public AlunoController() {
        AlunoRepository alunoRepository = new AlunoRepository();
        this.alunoService = new AlunoService(alunoRepository);
    }

    public Aluno cadastrarAluno(String nome, String email, String telefone){
        try{
            return alunoService.cadastrarAluno(nome, email, telefone);

        } catch (IllegalArgumentException e) {
            System.out.println("Erro de validação: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Erro ao salvar aluno: " + e.getMessage());
        }
        return null;
    }
}

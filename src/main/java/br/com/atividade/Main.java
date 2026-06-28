package br.com.atividade;

import br.com.atividade.controller.AlunoController;
import br.com.atividade.controller.CursoController;
import br.com.atividade.controller.MatriculaController;
import br.com.atividade.model.*;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        AlunoController alunoController = new AlunoController();
        CursoController cursoController = new CursoController();
        MatriculaController matriculaController = new MatriculaController();

        try {
            System.out.println("Salvando o aluno...");
            Aluno aluno1 = alunoController.cadastrarAluno("Ivy Lin", "ivylin@gmail.com", "(44) 99999-8888");

            System.out.println("Salvando o aluno...");
            Aluno aluno2 = alunoController.cadastrarAluno("Ian Kim", "ian2324@gmail.com", "(44) 99999-7777");

            System.out.println("Salvando o curso...");
            Curso curso = cursoController.cadastrarCurso("Lógica de Programação com Java", "Aprenda os conceitos básicos de POO, estruturas condicionais e JDBC.", 40, 30);

            System.out.println("Salvando a matrícula...");
            Matricula matricula = matriculaController.registrarMatricula(LocalDate.now(), new BigDecimal("100"), aluno1.getId(), curso.getId());
            System.out.println("Salvando a matrícula...");
            Matricula matricula2 = matriculaController.registrarMatricula(LocalDate.now(), new BigDecimal("100"), aluno2.getId(), curso.getId());

            apresentarResultado(matriculaController.listarAlunosPorCurso(curso.getId()), curso);

            apresentarResultado(matriculaController.listarCursosPorAluno(aluno1.getId()), aluno1);

        } catch (SQLException e) {
            System.err.println("Erro: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void apresentarResultado(List<Curso> cursos, Aluno aluno){
        System.out.println("Cursos de " + aluno.getNome() + ":");
        if (cursos.isEmpty()) {
            System.out.println("Nenhum curso cadastrado.");
            return;
        }
        for (Curso c : cursos) {
            System.out.println("Nome: " +c.getNome() +
                    " | Carga Horária: " + c.getCargaHoraria());
        }
    }

    public static void apresentarResultado(List<Aluno> alunos, Curso curso){
        System.out.println("Alunos do curso de " + curso.getNome() + ":");
        if (alunos.isEmpty()) {
            System.out.println("Nenhum aluno cadastrado.");
            return;
        }
        for (Aluno a : alunos) {
            System.out.println("Nome: " + a.getNome() +
                    " | E-mail: " + a.getEmail() +
                    " | Telefone: " + a.getTelefone());
        }
    }
}
package br.com.atividade.controller;

import br.com.atividade.model.Aluno;
import br.com.atividade.model.Curso;
import br.com.atividade.model.Matricula;
import br.com.atividade.repository.AlunoRepository;
import br.com.atividade.repository.CursoRepository;
import br.com.atividade.repository.MatriculaRepository;
import br.com.atividade.service.MatriculaService;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class MatriculaController {
    private final MatriculaService matriculaService;

    public MatriculaController() {
        MatriculaRepository matriculaRepository = new MatriculaRepository();
        AlunoRepository alunoRepository = new AlunoRepository();
        CursoRepository cursoRepository = new CursoRepository();
        this.matriculaService = new MatriculaService(matriculaRepository, alunoRepository, cursoRepository);
    }

    public Matricula registrarMatricula(LocalDate dataMatricula, BigDecimal valor, Long idAluno, Long idCurso){
        try{
            return matriculaService.registrarMatricula(dataMatricula, valor, idAluno, idCurso);

        } catch (IllegalArgumentException e) {
            System.out.println("Erro de validação: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Erro ao salvar matrícula: " + e.getMessage());
        }
        return null;
    }

    public List<Curso> listarCursosPorAluno(Long idAluno) throws SQLException {
        return matriculaService.listarCursosPorAluno(idAluno);
    }

    public List<Aluno> listarAlunosPorCurso(Long idCurso) throws SQLException {
        return matriculaService.listarAlunosPorCurso(idCurso);
    }
}

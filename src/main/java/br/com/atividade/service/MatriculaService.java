package br.com.atividade.service;

import br.com.atividade.model.Aluno;
import br.com.atividade.model.Curso;
import br.com.atividade.model.Matricula;
import br.com.atividade.repository.AlunoRepository;
import br.com.atividade.repository.CursoRepository;
import br.com.atividade.repository.MatriculaRepository;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class MatriculaService {
    private final MatriculaRepository matriculaRepository;
    private final AlunoRepository alunoRepository;
    private final CursoRepository cursoRepository;

    public MatriculaService(MatriculaRepository matriculaRepository, AlunoRepository alunoRepository, CursoRepository cursoRepository) {
        this.matriculaRepository = matriculaRepository;
        this.alunoRepository = alunoRepository;
        this.cursoRepository = cursoRepository;
    }

    public Matricula registrarMatricula(LocalDate dataMatricula, BigDecimal valor, Long idAluno, Long idCurso) throws SQLException {
        Optional<Aluno> alunoOpt = alunoRepository.buscarPorId(idAluno);

        Aluno aluno = alunoOpt.orElseThrow(() ->
                new IllegalArgumentException("Não foi possível registrar a matrícula: Aluno com ID " + idAluno + " não existe.")
        );

        Optional<Curso> cursoOpt = cursoRepository.buscarPorId(idCurso);

        Curso curso = cursoOpt.orElseThrow(() ->
                new IllegalArgumentException("Não foi possível registrar a matrícula: Curso com ID " + idAluno + " não existe.")
        );

        if (!curso.possuiVagaDisponivel()) {
                throw new IllegalStateException("Curso sem vagas disponíveis.");
        }

        Matricula novaMatricula = new Matricula(null, dataMatricula, valor, aluno, curso);
        return matriculaRepository.salvar(novaMatricula);
    }

    public List<Curso> listarCursosPorAluno(Long idAluno) throws SQLException {
        return matriculaRepository.buscarCursosPorAluno(idAluno);
    }

    public List<Aluno> listarAlunosPorCurso(Long idCurso) throws SQLException {
        return matriculaRepository.buscarAlunosPorCurso(idCurso);

    }
}

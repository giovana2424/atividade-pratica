package br.com.atividade.service;

import br.com.atividade.model.Curso;
import br.com.atividade.repository.CursoRepository;

import java.sql.SQLException;

public class CursoService {
    private final CursoRepository cursoRepository;

    public CursoService(CursoRepository cursoRepository) {
        this.cursoRepository = cursoRepository;
    }

    public Curso cadastrarCurso(String nome, String descricao, Integer cargaHoraria, Integer vagasTotais) throws SQLException {
        Curso curso = new Curso(null, nome, descricao, cargaHoraria, vagasTotais);
        return cursoRepository.salvar(curso);
    }
}

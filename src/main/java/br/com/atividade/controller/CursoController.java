package br.com.atividade.controller;

import br.com.atividade.model.Aluno;
import br.com.atividade.model.Curso;
import br.com.atividade.repository.CursoRepository;
import br.com.atividade.service.CursoService;

import java.sql.SQLException;

public class CursoController {
    private final CursoService cursoService;

    public CursoController() {
        CursoRepository cursoRepository = new CursoRepository();
        this.cursoService = new CursoService(cursoRepository);
    }

    public Curso cadastrarCurso(String nome, String descricao, Integer cargaHoraria, Integer vagasTotais){
        try{
            return cursoService.cadastrarCurso(nome, descricao, cargaHoraria, vagasTotais);

        } catch (IllegalArgumentException e) {
            System.out.println("Erro de validação: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Erro ao salvar curso: " + e.getMessage());
        }
        return null;
    }
}

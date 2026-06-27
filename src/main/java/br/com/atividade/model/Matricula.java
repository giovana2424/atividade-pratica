package br.com.atividade.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Matricula {
    private Long id;
    private LocalDate dataMatricula;
    private BigDecimal valor;
    private Aluno aluno;
    private Curso curso;

    public Matricula(Long id, LocalDate dataMatricula, BigDecimal valor, Aluno aluno, Curso curso) {
        if (dataMatricula == null){
            throw new IllegalArgumentException("Data da matrícula não pode ser vazio.");
        }
        if (valor == null || valor.compareTo(BigDecimal.ZERO) < 0){
            throw new IllegalArgumentException("Valor da matrícula não pode ser menor que zero.");
        }
        if (aluno == null){
            throw new IllegalArgumentException("A matrícula deve possuir um aluno vinculado.");
        }
        if (curso == null){
            throw new IllegalArgumentException("A matrícula deve possuir um curso vinculado.");
        }

        if (!curso.possuiVagaDisponivel()){
            throw new IllegalArgumentException("Não é possível realizar a matrícula: O curso selecionado não possui vagas disponíveis.");
        }

        this.id = id;
        this.dataMatricula = dataMatricula;
        this.valor = valor;
        this.aluno = aluno;
        this.curso = curso;
    }

    public Long getId() {
        return id;
    }

    public LocalDate getDataMatricula() {
        return dataMatricula;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setId(Long id) {
        this.id = id;
    }
}

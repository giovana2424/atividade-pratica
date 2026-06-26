package br.com.atividade.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Consulta {
    private Long id;
    private Animal animal;
    private LocalDate data;
    private String motivo;
    private BigDecimal valor;

    public Consulta(Long id, Animal animal, LocalDate data, String motivo, BigDecimal valor) {
        if (animal == null){
            throw new IllegalArgumentException("Informe o animal.");
        }

        if (data == null){
            throw new IllegalArgumentException("Informe a data do agendamento da consulta.");
        }

        if (motivo == null || motivo.isEmpty()){
            throw new IllegalArgumentException("Informe o motivo da consulta.");
        }

        if(valor == null || valor.compareTo(BigDecimal.ZERO) < 0){
            throw new IllegalArgumentException("Valor da consulta não pode ser negativo");
        }

        this.id = id;
        this.animal = animal;
        this.data = data;
        this.motivo = motivo;
        this.valor = valor;
    }

    public Long getId() {
        return id;
    }

    public Animal getAnimal() {
        return animal;
    }

    public LocalDate getData() {
        return data;
    }

    public String getMotivo() {
        return motivo;
    }

    public BigDecimal getValor() {
        return valor;
    }
}

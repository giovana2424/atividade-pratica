package br.com.atividade.model;

public class Animal {
    private Long id;
    private String nome;
    private String especie;
    private String raca;
    private Tutor tutor;

    public Animal() {
    }

    public Animal(Long id, String nome, String especie, String raca, Tutor tutor) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome do animal não pode ser vazio");
        }

        if (especie == null || especie.isBlank()) {
            throw new IllegalArgumentException("Espécie do animal não pode ser vazio");
        }

        if (raca == null || raca.isBlank()) {
            throw new IllegalArgumentException("Raça do animal não pode ser vazio");
        }

        if (tutor == null) {
            throw new IllegalArgumentException("Tutor não pode ser vazio");
        }

        this.id = id;
        this.nome = nome;
        this.especie = especie;
        this.raca = raca;
        this.tutor = tutor;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public Tutor getTutor() {
        return tutor;
    }

    public String getEspecie() {
        return especie;
    }

    public String getRaca() {
        return raca;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}

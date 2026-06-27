package br.com.atividade.model;

public class Curso {
    private Long id;
    private String nome;
    private String descricao;
    private Integer cargaHoraria;
    private Integer vagasTotais;
    private Integer vagasDisponiveis;

    public Curso() {
    }

    public Curso(Long id, String nome, String descricao, Integer cargaHoraria, Integer vagasTotais, Integer vagasDisponiveis) {
        if (nome == null || nome.isEmpty()){
            throw new IllegalArgumentException("Nome do curso não pode ser vazio.");
        }
        if (descricao == null || descricao.isEmpty()){
            throw new IllegalArgumentException("Descrição do curso não pode ser vazio.");
        }
        if (cargaHoraria == null || cargaHoraria < 0){
            throw new IllegalArgumentException("Carga horária do curso não pode ser negativa.");
        }
        if (vagasTotais == null || vagasTotais <= 0){
            throw new IllegalArgumentException("As vagas totais devem ser maiores que zero.");
        }
        if (vagasDisponiveis == null || vagasDisponiveis < 0){
            throw new IllegalArgumentException("Vagas disponíveis do curso não pode ser negativa.");
        }
        if (vagasDisponiveis > vagasTotais){
            throw new IllegalArgumentException("Vagas disponíveis do curso não pode ser maior que as vagas totais.");
        }

        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.cargaHoraria = cargaHoraria;
        this.vagasTotais = vagasTotais;
        this.vagasDisponiveis = vagasDisponiveis;
    }

    public Curso(Long id, String nome, String descricao, Integer cargaHoraria, Integer vagasTotais) {
        this(id, nome, descricao, cargaHoraria, vagasTotais, vagasTotais);
    }

    public boolean possuiVagaDisponivel(){
        return vagasDisponiveis > 0;
    }

    public void decrementarVaga(){
        vagasDisponiveis -= 1;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public Integer getCargaHoraria() {
        return cargaHoraria;
    }

    public Integer getVagasTotais() {
        return vagasTotais;
    }

    public Integer getVagasDisponiveis() {
        return vagasDisponiveis;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCargaHoraria(Integer cargaHoraria) {
        this.cargaHoraria = cargaHoraria;
    }
}

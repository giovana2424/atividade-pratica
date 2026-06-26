package br.com.atividade.model;

public class Tutor {
    private Long id;
    private String nome;
    private Endereco endereco;
    private String telefone;

    public Tutor() {
    }

    public Tutor(Long id, String nome, Endereco endereco, String telefone) {
        if (nome == null || nome.isEmpty()){
            throw new IllegalArgumentException("Nome do tutor não pode ser vazio.");
        }

        if (endereco == null){
            throw new IllegalArgumentException("Endereço não pode ser vazio.");
        }

        if (telefone == null || telefone.isEmpty()){
            throw new IllegalArgumentException("Telefone não pode ser vazio.");
        }

        this.id = id;
        this.nome = nome;
        this.endereco = endereco;
        this.telefone = telefone;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}

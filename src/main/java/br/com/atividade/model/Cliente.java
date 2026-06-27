package br.com.atividade.model;

public class Cliente {
    private Long id;
    private String nome;
    private String telefone;

    public Cliente() {
    }

    public Cliente(Long id, String nome, String telefone) {
        if (nome == null || nome.isEmpty()){
            throw new IllegalArgumentException("Nome não pode ser vazio.");
        }

        if (telefone == null || telefone.isEmpty()){
            throw new IllegalArgumentException("Telefone não pode ser vazio.");
        }
        this.id = id;
        this.nome = nome;
        this.telefone = telefone;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}

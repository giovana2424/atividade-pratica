package br.com.atividade.model;

public class Aluno {
    private Long id;
    private String nome;
    private String email;
    private String telefone;

    public Aluno() {
    }

    public Aluno(Long id, String nome, String email, String telefone) {
        if (nome == null || nome.isEmpty()){
            throw new IllegalArgumentException("O nome do aluno não pode ser vazio.");
        }
        if (email == null || email.isEmpty()){
            throw new IllegalArgumentException("O e-mail do aluno não pode ser vazio.");
        }
        if (telefone == null || telefone.isEmpty()){
            throw new IllegalArgumentException("O telefone do aluno não pode ser vazio.");
        }

        this.id = id;
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
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

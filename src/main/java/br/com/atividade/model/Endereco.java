package br.com.atividade.model;

public class Endereco {
    private Long id;
    private String logradouro;
    private String numero;
    private String bairro;
    private String cidade;
    private String uf;
    private String cep;

    public Endereco() {
    }

    public Endereco(Long id, String logradouro, String numero, String bairro, String cidade, String uf, String cep) {
        if (logradouro == null || logradouro.trim().length() < 3) {
            throw new IllegalArgumentException("Logradouro inválido. Deve ter pelo menos 3 caracteres.");
        }

        if (numero == null || numero.trim().isEmpty()) {
            throw new IllegalArgumentException("O número não pode ser vazio.");
        }

        if (bairro == null || bairro.trim().length() < 3) {
            throw new IllegalArgumentException("Bairro inválido. Deve ter pelo menos 3 caracteres.");
        }

        if (cidade == null || cidade.trim().length() < 3) {
            throw new IllegalArgumentException("Cidade inválida. Deve ter pelo menos 3 caracteres.");
        }

        if (uf == null || uf.trim().length() != 2) {
            throw new IllegalArgumentException("UF inválida. Deve conter exatamente 2 caracteres (ex: SP).");
        }

        if (cep == null) {
            throw new IllegalArgumentException("O CEP não pode ser nulo.");
        }

        this.id = id;
        this.logradouro = logradouro.trim();
        this.numero = numero.trim();
        this.bairro = bairro.trim();
        this.cidade = cidade.trim();
        this.uf = uf.trim();
        this.cep = cep.trim();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public String getNumero() {
        return numero;
    }

    public String getBairro() {
        return bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public String getUf() {
        return uf;
    }

    public String getCep() {
        return cep;
    }
}

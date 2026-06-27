package br.com.atividade.model;

import java.math.BigDecimal;

public class OrdemServico {
    private Long id;
    private String descricao;
    private BigDecimal valor;
    private String status;
    private Veiculo veiculo;

    public OrdemServico(Long id, String descricao, BigDecimal valor, String status, Veiculo veiculo) {
        if (descricao == null || descricao.trim().isEmpty()) {
            throw new IllegalArgumentException("A descrição da ordem de serviço não pode ser vazia.");
        }
        if (valor == null || valor.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("O valor do serviço não pode ser negativo.");
        }
        if (veiculo == null) {
            throw new IllegalArgumentException("A ordem de serviço deve estar vinculada a um veículo válido.");
        }
        if (status == null || status.trim().isEmpty()) {
            throw new IllegalArgumentException("O status não pode ser nulo ou vazio.");
        }

        this.id = id;
        this.descricao = descricao;
        this.valor = valor;
        this.status = status.toUpperCase();
        this.veiculo = veiculo;
    }

    public OrdemServico(Long id, String descricao, BigDecimal valor, Veiculo veiculo) {
        this(id, descricao, valor, "ABERTA", veiculo);
    }

    public Long getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public String getStatus() {
        return status;
    }

    public Veiculo getVeiculo() {
        return veiculo;
    }

    public void finalizar() {
        if ("CONCLUÍDA".equals(this.status)) {
            throw new IllegalStateException("Esta ordem de serviço já está concluída.");
        }
        this.status = "CONCLUÍDA";
    }
}

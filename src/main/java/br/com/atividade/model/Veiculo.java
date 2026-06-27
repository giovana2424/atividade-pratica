package br.com.atividade.model;

public class Veiculo {
    private Long id;
    private String placa;
    private String modelo;
    private Integer ano;
    private Cliente cliente;

    public Veiculo() {
    }

    public Veiculo(Long id, String placa, String modelo, Integer ano, Cliente cliente) {
        if (placa == null || placa.isEmpty()){
            throw new IllegalArgumentException("Placa não pode ser vazio.");
        }

        if (modelo == null || modelo.isEmpty()){
            throw new IllegalArgumentException("Modelo não pode ser vazio.");
        }

        if (ano == null){
            throw new IllegalArgumentException("Ano não pode ser vazio.");
        }

        if (cliente == null){
            throw new IllegalArgumentException("Cliente não pode ser vazio.");
        }

        this.id = id;
        this.placa = placa;
        this.modelo = modelo;
        this.ano = ano;
        this.cliente = cliente;
    }

    public Long getId() {
        return id;
    }

    public String getPlaca() {
        return placa;
    }

    public String getModelo() {
        return modelo;
    }

    public Integer getAno() {
        return ano;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }
}

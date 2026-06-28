package br.com.atividade;

import br.com.atividade.controller.ClienteController;
import br.com.atividade.controller.VeiculoController;
import br.com.atividade.controller.OrdemServicoController;
import br.com.atividade.model.Cliente;
import br.com.atividade.model.OrdemServico;
import br.com.atividade.model.Veiculo;

import java.math.BigDecimal;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        ClienteController clienteController = new ClienteController();
        VeiculoController veiculoController = new VeiculoController();
        OrdemServicoController ordemServicoController = new OrdemServicoController();

        try {
            System.out.println("Salvando o cliente...");
            Cliente cliente = clienteController.cadastrarCliente("Carmen Cabelo", "(44) 91234-5678");

            System.out.println("Salvando o veículo...");
            Veiculo veiculo = veiculoController.cadastrarVeiculo("AVD-8600", "SL-2 1.9", 1991, cliente.getId());

            System.out.println("\nRegistrando ordem de serviço...");
            OrdemServico ordemServico = ordemServicoController.registrarOrdemServico("Troca de óleo e filtro", new BigDecimal("50"), veiculo.getId());

            ordemServicoController.finalizarOrdemServico(ordemServico.getId());

            apresentarResultado(veiculoController.buscarVeiculosPorCliente(cliente.getId()), cliente);

            apresentarResultado(ordemServicoController.buscarOrdensDeServicoPorVeiculo(veiculo.getId()), veiculo);

        } catch (Exception e) {
            System.err.println("Erro: " + e.getMessage());
        }
    }

    public static void apresentarResultado(List<Veiculo> veiculos, Cliente cliente){
        System.out.println("\n--- Veículos de " + cliente.getNome() + " ---");
        if (veiculos.isEmpty()) {
            System.out.println("Nenhum veículo cadastrado.");
            return;
        }
        for (Veiculo v : veiculos) {
            System.out.println("Placa: " + v.getPlaca() +
                    " | Modelo: " + v.getModelo() +
                    " | Ano: " + v.getAno());
        }
    }

    public static void apresentarResultado(List<OrdemServico> ordensServico, Veiculo veiculo){
        System.out.println("\nHistórico de manutenções do veículo [" + veiculo.getPlaca() + " - " + veiculo.getModelo() + "]:");
        if (ordensServico.isEmpty()) {
            System.out.println("Nenhuma ordem de serviço cadastrada.");
            return;
        }
        for (OrdemServico os : ordensServico){
            System.out.println("Descrição: " + os.getDescricao() +
                    " | Valor: R$" + os.getValor() +
                    " | Status: " + os.getStatus());
        }
    }
}
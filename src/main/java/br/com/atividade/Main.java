package br.com.atividade;

import br.com.atividade.controller.ClienteController;
import br.com.atividade.controller.VeiculoController;
import br.com.atividade.controller.OrdemServicoController;
import br.com.atividade.model.Cliente;
import br.com.atividade.model.OrdemServico;
import br.com.atividade.model.Veiculo;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
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

            List<Veiculo> veiculosDoCliente = veiculoController.buscarVeiculosPorCliente(cliente.getId());

            System.out.println("\n--- Veículos de " + cliente.getNome() + " ---");
            for (Veiculo v : veiculosDoCliente) {
                System.out.println("Placa: " + v.getPlaca() +
                        " | Modelo: " + v.getModelo() +
                        " | Ano: " + v.getAno());
            }

            System.out.println("\nRegistrando ordem de serviço...");
            OrdemServico ordemServico = ordemServicoController.registrarOrdemServico("Troca de óleo e filtro", new BigDecimal("50"), veiculo.getId());

            ordemServicoController.finalizarOrdemServico(ordemServico.getId());

            List<OrdemServico> ordensServicoDoVeiculo = ordemServicoController.buscarOrdensDeServicoPorVeiculo(veiculo.getId());

            System.out.println("Histórico de manuntenções do veículo:");

            for (OrdemServico os : ordensServicoDoVeiculo){
                System.out.println("Descrição: " + os.getDescricao() +
                        " | Valor: R$" + os.getValor() +
                        " | Status: " + os.getStatus());
            }

        } catch (SQLException e) {
            System.err.println("Erro: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
package br.com.atividade;

import br.com.atividade.controller.AnimalController;
import br.com.atividade.controller.ConsultaController;
import br.com.atividade.controller.EnderecoController;
import br.com.atividade.controller.TutorController;
import br.com.atividade.model.Animal;
import br.com.atividade.model.Consulta;
import br.com.atividade.model.Endereco;
import br.com.atividade.model.Tutor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        EnderecoController enderecoController = new EnderecoController();
        TutorController tutorController = new TutorController();
        AnimalController animalController = new AnimalController();
        ConsultaController consultaController = new ConsultaController();

        try {
            System.out.println("Salvando o tutor...");
            Endereco endereco = enderecoController.cadastrarEndereco("Rua Daniel C. Vianna", "123", "Conjunto Habitacional Jamile Dequech", "Londrina", "PR", "86044-736");
            Tutor tutor = tutorController.cadastrarTutor("Carlos Silva", endereco.getId(), "(11) 99999-8888");

            System.out.println("Salvando o animal...");
            Animal animal = animalController.cadastrarAnimal("Lulu", "Cachorro", "Pitbull", tutor.getId());

            System.out.println("\nRegistrando consulta...");
            Consulta consulta = consultaController.registrarConsulta(LocalDate.now(), "Rotina / Vacinação", new BigDecimal("150.00"), animal.getId());

            apresentarResultado(animalController.buscarAnimaisPorTutor(tutor.getId()), tutor);

            apresentarResultado(consultaController.buscarConsultasPorAnimal(animal.getId()), animal);

        } catch (Exception e) {
            System.err.println("Erro: " + e.getMessage());
        }
    }

    public static void apresentarResultado(List<Consulta> consultas, Animal animal){
        System.out.println("Histórico de consultas de " + animal.getNome() + ":");
        if (consultas.isEmpty()) {
            System.out.println("Nenhuma consulta cadastrada.");
            return;
        }
        for (Consulta c : consultas){
            System.out.println("Data do atendimento: " + c.getData() +
                    " | Motivo: " + c.getMotivo() +
                    " | Valor: R$" + c.getValor());
        }
    }

    public static void apresentarResultado(List<Animal> animais, Tutor tutor){
        System.out.println("Animais de " + tutor.getNome() + ":");
        if (animais.isEmpty()) {
            System.out.println("Nenhum animal cadastrado.");
            return;
        }
        for (Animal a : animais) {
            System.out.println("Nome: " + a.getNome() +
                    " | Espécie: " + a.getEspecie() +
                    " | Raça: " + a.getRaca());
        }
    }
}
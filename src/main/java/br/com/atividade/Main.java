package br.com.atividade;

import br.com.atividade.controller.AnimalController;
import br.com.atividade.controller.ConsultaController;
import br.com.atividade.model.Animal;
import br.com.atividade.model.Consulta;
import br.com.atividade.model.Endereco;
import br.com.atividade.model.Tutor;
import br.com.atividade.repository.AnimalRepository;
import br.com.atividade.repository.EnderecoRepository;
import br.com.atividade.repository.TutorRepository;
import br.com.atividade.service.AnimalService;
import br.com.atividade.service.ConsultaService;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        EnderecoRepository enderecoRepository = new EnderecoRepository();
        TutorRepository tutorRepository = new TutorRepository();
        AnimalRepository animalRepository = new AnimalRepository();
        AnimalService animalService = new AnimalService();
        AnimalController animalController = new AnimalController(animalService);
        ConsultaService consultaService = new ConsultaService();
        ConsultaController consultaController = new ConsultaController(consultaService);

        try {
            Endereco novoEndereco = new Endereco(null,
                    "Rua Daniel C. Vianna",
                    "123",
                    "Conjunto Habitacional Jamile Dequech",
                    "Londrina",
                    "PR",
                    "86044-736");

            Endereco enderecoSalvo = enderecoRepository.salvar(novoEndereco);

            Tutor novoTutor = new Tutor(null,
                    "Carlos Silva",
                    enderecoSalvo,
                    "(11) 99999-8888");

            System.out.println("Salvando o tutor...");
            Tutor tutorSalvo = tutorRepository.salvar(novoTutor);

            Animal novoAnimal = new Animal(null,
                    "Lulu",
                    "Cachorro",
                    "Pitbull",
                    tutorSalvo);

            System.out.println("Salvando o animal...");
            Animal animalSalvo = animalRepository.salvar(novoAnimal);

            List<Animal> animaisDoTutor = animalController.buscarAnimaisPorTutor(tutorSalvo.getId());

            System.out.println("Animais de " + tutorSalvo.getNome() + ":");
            for (Animal a : animaisDoTutor) {
                System.out.println("\nNome: " + a.getNome() +
                        "\nEspécie: " + a.getEspecie() +
                        "\nRaça: " + a.getRaca());
            }

            System.out.println("\nRegistrando consulta para o animal salvo...");
            Consulta consultaSalva = consultaController.registrarConsulta(
                    LocalDate.now(),
                    "Rotina / Vacinação",
                    new BigDecimal("150.00"),
                    animalSalvo
            );

            List<Consulta> consultasDoAnimal = consultaController.buscarConsultasPorAnimal(animalSalvo.getId());

            System.out.println("Histórico de consultas de " + animalSalvo.getNome() + " :");

            for (Consulta c : consultasDoAnimal){
                System.out.println("\nData do atendimento: " + c.getData() +
                        "\nMotivo: " + c.getMotivo() +
                        "\nValor: R$" + c.getValor());
            }

        } catch (SQLException e) {
            System.err.println("Erro: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
package br.com.atividade.controller;

import br.com.atividade.model.Tutor;
import br.com.atividade.repository.EnderecoRepository;
import br.com.atividade.repository.TutorRepository;
import br.com.atividade.service.TutorService;

import java.sql.SQLException;

public class TutorController {
    private final TutorService tutorService;

    public TutorController() {
        TutorRepository tutorRepository = new TutorRepository();
        EnderecoRepository enderecoRepository = new EnderecoRepository();
        this.tutorService = new TutorService(tutorRepository, enderecoRepository);
    }

    public Tutor cadastrarTutor(String nome, Long idEndereco, String telefone){
        try{
            return tutorService.cadastrarTutor(nome, idEndereco, telefone);

        } catch (IllegalArgumentException e) {
            System.out.println("Erro de validação: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Erro ao salvar tutor: " + e.getMessage());
        }
        return null;
    }
}

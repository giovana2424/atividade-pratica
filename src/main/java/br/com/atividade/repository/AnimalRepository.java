package br.com.atividade.repository;

import br.com.atividade.model.Animal;
import br.com.atividade.model.Tutor;
import br.com.atividade.util.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AnimalRepository {
    public Animal salvar(Animal animal) throws SQLException {
        String sql = "INSERT INTO animal (nome, especie, raca, tutor_id) VALUES (?, ?, ?, ?) RETURNING id";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, animal.getNome());
            stmt.setString(2, animal.getEspecie());
            stmt.setString(3, animal.getRaca());
            stmt.setLong(4, animal.getTutor().getId());

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Animal(rs.getLong("id"),
                            animal.getNome(),
                            animal.getEspecie(),
                            animal.getRaca(),
                            animal.getTutor());
                }
                throw new SQLException("Erro ao obter o ID do animal inserido.");
            }
        }
    }

    public Optional<Animal> buscarPorId(Long id) throws SQLException {
        String sql = "SELECT * FROM animal WHERE id = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() ? Optional.of(mapear(rs)) : Optional.empty();
            }
        }
    }

    public List<Animal> buscarPorTutor(Long idTutor) throws SQLException {
        String sql = """
            SELECT a.*
            FROM animal a
            JOIN tutor t ON a.tutor_id = t.id
            WHERE a.tutor_id = ?
            ORDER BY a.nome
        """;

        List<Animal> animais = new ArrayList<>();

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, idTutor);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Tutor tutor = new Tutor();
                    tutor.setId(rs.getLong("tutor_id"));

                    Animal animal = new Animal(
                            rs.getLong("id"),
                            rs.getString("nome"),
                            rs.getString("especie"),
                            rs.getString("raca"),
                            tutor
                    );
                    animais.add(animal);
                }
            }
        }
        return animais;
    }

    public List<Animal> listarTodos() throws SQLException {
        String sql = "SELECT * FROM animal ORDER BY nome";
        List<Animal> lista = new ArrayList<>();
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) lista.add(mapear(rs));
        }
        return lista;
    }

    public void atualizar(Animal animal) throws SQLException {
        String sql = "UPDATE animal SET nome = ?, especie = ?, raca = ?, tutor_id = ? WHERE id = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, animal.getNome());
            stmt.setString(2, animal.getEspecie());
            stmt.setString(3, animal.getRaca());
            stmt.setLong(4, animal.getTutor().getId());
            stmt.setLong(5, animal.getId());
            stmt.executeUpdate();
        }
    }

    public void deletar(Long id) throws SQLException {
        String sql = "DELETE FROM animal WHERE id = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        }
    }

    private Animal mapear(ResultSet rs) throws SQLException {
        Tutor tutor = new Tutor();
        tutor.setId(rs.getLong("tutor_id"));

        return new Animal(
                rs.getLong("id"),
                rs.getString("nome"),
                rs.getString("especie"),
                rs.getString("raca"),
                tutor
        );
    }
}

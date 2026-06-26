package br.com.atividade.repository;

import br.com.atividade.model.Endereco;
import br.com.atividade.model.Tutor;
import br.com.atividade.util.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TutorRepository {
    public Tutor salvar(Tutor tutor) throws SQLException {
        String sql = "INSERT INTO tutor (nome, endereco_id, telefone) VALUES (?, ?, ?) RETURNING id";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, tutor.getNome());
            stmt.setLong(2, tutor.getEndereco().getId());
            stmt.setString(3, tutor.getTelefone());

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Tutor(rs.getLong("id"),
                            tutor.getNome(),
                            tutor.getEndereco(),
                            tutor.getTelefone());
                }
                throw new SQLException("Erro ao obter o ID do tutor inserido.");
            }
        }
    }

    public Optional<Tutor> buscarPorId(Long id) throws SQLException {
        String sql = "SELECT * FROM tutor WHERE id = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() ? Optional.of(mapear(rs)) : Optional.empty();
            }
        }
    }

    public List<Tutor> listarTodos() throws SQLException {
        String sql = "SELECT * FROM tutor ORDER BY nome";
        List<Tutor> lista = new ArrayList<>();
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) lista.add(mapear(rs));
        }
        return lista;
    }

    public void atualizar(Tutor tutor) throws SQLException {
        String sql = "UPDATE tutor SET nome = ?, endereco_id = ?, telefone = ? WHERE id = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, tutor.getNome());
            stmt.setLong(2, tutor.getEndereco().getId());
            stmt.setString(3, tutor.getTelefone());
            stmt.setLong(4, tutor.getId());
            stmt.executeUpdate();
        }
    }

    public void deletar(Long id) throws SQLException {
        String sql = "DELETE FROM tutor WHERE id = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        }
    }

    private Tutor mapear(ResultSet rs) throws SQLException {
        Endereco endereco = new Endereco();
        endereco.setId(rs.getLong("endereco_id"));

        return new Tutor(
                rs.getLong("id"),
                rs.getString("nome"),
                endereco,
                rs.getString("telefone")
        );
    }
}

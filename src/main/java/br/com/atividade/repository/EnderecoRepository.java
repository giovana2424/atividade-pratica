package br.com.atividade.repository;

import br.com.atividade.model.Endereco;
import br.com.atividade.util.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class EnderecoRepository {
    public Endereco salvar(Endereco endereco) throws SQLException {
        String sql = "INSERT INTO endereco (logradouro, numero, bairro, cidade, uf, cep) VALUES (?, ?, ?, ?, ?, ?) RETURNING id";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, endereco.getLogradouro());
            stmt.setString(2, endereco.getNumero());
            stmt.setString(3, endereco.getBairro());
            stmt.setString(4, endereco.getCidade());
            stmt.setString(5, endereco.getUf());
            stmt.setString(6, endereco.getCep());

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Endereco(rs.getLong("id"),
                            endereco.getLogradouro(),
                            endereco.getNumero(),
                            endereco.getBairro(),
                            endereco.getCidade(),
                            endereco.getUf(),
                            endereco.getCep());
                }
                throw new SQLException("Erro ao obter o ID do endereço inserido.");
            }
        }
    }

    public Optional<Endereco> buscarPorId(Long id) throws SQLException {
        String sql = "SELECT * FROM endereco WHERE id = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() ? Optional.of(mapear(rs)) : Optional.empty();
            }
        }
    }

    public void atualizar(Endereco endereco) throws SQLException {
        String sql = "UPDATE endereco SET logradouro = ?, numero = ?, bairro = ?, cidade = ?, uf = ?, cep = ? WHERE id = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, endereco.getLogradouro());
            stmt.setString(2, endereco.getNumero());
            stmt.setString(3, endereco.getBairro());
            stmt.setString(4, endereco.getCidade());
            stmt.setString(5, endereco.getUf());
            stmt.setString(6, endereco.getCep());
            stmt.setLong(7, endereco.getId());
            stmt.executeUpdate();
        }
    }

    public void deletar(Long id) throws SQLException {
        String sql = "DELETE FROM endereco WHERE id = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        }
    }

    private Endereco mapear(ResultSet rs) throws SQLException {
        return new Endereco(
                rs.getLong("id"),
                rs.getString("logradouro"),
                rs.getString("numero"),
                rs.getString("bairro"),
                rs.getString("cidade"),
                rs.getString("uf"),
                rs.getString("cep"));
    }
}

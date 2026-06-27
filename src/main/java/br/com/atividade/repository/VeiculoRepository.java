package br.com.atividade.repository;

import br.com.atividade.model.Cliente;
import br.com.atividade.model.Veiculo;
import br.com.atividade.util.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class VeiculoRepository {
    public Veiculo salvar(Veiculo veiculo) throws SQLException {
        String sql = "INSERT INTO veiculo (placa, modelo, ano, cliente_id) VALUES (?, ?, ?, ?) RETURNING id";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, veiculo.getPlaca());
            stmt.setString(2, veiculo.getModelo());
            stmt.setInt(3, veiculo.getAno());
            stmt.setLong(4, veiculo.getCliente().getId());

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Veiculo(rs.getLong("id"),
                            veiculo.getPlaca(),
                            veiculo.getModelo(),
                            veiculo.getAno(),
                            veiculo.getCliente());
                }
                throw new SQLException("Erro ao obter o ID do veículo inserido.");
            }
        }
    }

    public Optional<Veiculo> buscarPorId(Long id) throws SQLException {
        String sql = "SELECT * FROM veiculo WHERE id = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() ? Optional.of(mapear(rs)) : Optional.empty();
            }
        }
    }

    public List<Veiculo> buscarPorCliente(Long idCliente) throws SQLException {
        String sql = """
            SELECT v.*,
                   c.nome AS cliente_nome
            FROM veiculo v
            JOIN cliente c ON v.cliente_id = c.id
            WHERE v.cliente_id = ?
        """;

        List<Veiculo> veiculos = new ArrayList<>();

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, idCliente);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Cliente cliente = new Cliente();
                    cliente.setId(rs.getLong("cliente_id"));
                    cliente.setNome(rs.getString("cliente_nome"));

                    Veiculo veiculo = new Veiculo(
                            rs.getLong("id"),
                            rs.getString("placa"),
                            rs.getString("modelo"),
                            rs.getInt("ano"),
                            cliente
                    );
                    veiculos.add(veiculo);
                }
            }
        }
        return veiculos;
    }

    public List<Veiculo> listarTodos() throws SQLException {
        String sql = "SELECT * FROM veiculo";
        List<Veiculo> lista = new ArrayList<>();
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) lista.add(mapear(rs));
        }
        return lista;
    }

    public void atualizar(Veiculo veiculo) throws SQLException {
        String sql = "UPDATE veiculo SET placa = ?, modelo = ?, ano = ?, cliente_id = ? WHERE id = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, veiculo.getPlaca());
            stmt.setString(2, veiculo.getModelo());
            stmt.setInt(3, veiculo.getAno());
            stmt.setLong(4, veiculo.getCliente().getId());
            stmt.setLong(5,veiculo.getId());
            stmt.executeUpdate();
        }
    }

    public void deletar(Long id) throws SQLException {
        String sql = "DELETE FROM veiculo WHERE id = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        }
    }

    private Veiculo mapear(ResultSet rs) throws SQLException {
        Cliente cliente = new Cliente();
        cliente.setId(rs.getLong("cliente_id"));

        return new Veiculo(
                rs.getLong("id"),
                rs.getString("placa"),
                rs.getString("modelo"),
                rs.getInt("ano"),
                cliente
        );
    }
}

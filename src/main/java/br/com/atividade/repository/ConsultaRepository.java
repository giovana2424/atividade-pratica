package br.com.atividade.repository;

import br.com.atividade.model.Animal;
import br.com.atividade.model.Consulta;
import br.com.atividade.util.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ConsultaRepository {
    public Consulta salvar(Consulta consulta) throws SQLException {
        String sql = "INSERT INTO consulta (data_atendimento, motivo, valor, animal_id) VALUES (?, ?, ?, ?) RETURNING id";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setObject(1, consulta.getData());
            stmt.setString(2, consulta.getMotivo());
            stmt.setBigDecimal(3, consulta.getValor());
            stmt.setLong(4, consulta.getAnimal().getId());

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Consulta(rs.getLong("id"),
                            consulta.getAnimal(),
                            consulta.getData(),
                            consulta.getMotivo(),
                            consulta.getValor());
                }
                throw new SQLException("Erro ao obter o ID do animal inserido.");
            }
        }
    }

    public Optional<Consulta> buscarPorId(Long id) throws SQLException {
        String sql = "SELECT * FROM consulta WHERE id = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() ? Optional.of(mapear(rs)) : Optional.empty();
            }
        }
    }

    public List<Consulta> buscarPorAnimal(Long idAnimal) throws SQLException {
        String sql = """
            SELECT c.*,
                   a.nome AS animal_nome
            FROM consulta c
            JOIN animal a ON c.animal_id = a.id
            WHERE c.animal_id = ?
            ORDER BY c.data_atendimento
        """;

        List<Consulta> lista = new ArrayList<>();

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, idAnimal);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapear(rs));
                }
            }
        }
        return lista;
    }

    public List<Consulta> listarTodos() throws SQLException {
        String sql = "SELECT * FROM consulta ORDER BY data_agendamento";
        List<Consulta> lista = new ArrayList<>();
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) lista.add(mapear(rs));
        }
        return lista;
    }

    public void atualizar(Consulta consulta) throws SQLException {
        String sql = "UPDATE consulta SET data_andamento = ?, motivo = ?, valor = ?, animal_id = ? WHERE id = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setObject(1, consulta.getData());
            stmt.setString(2, consulta.getMotivo());
            stmt.setBigDecimal(3, consulta.getValor());
            stmt.setLong(4, consulta.getAnimal().getId());
            stmt.setLong(5, consulta.getId());
            stmt.executeUpdate();
        }
    }

    public void deletar(Long id) throws SQLException {
        String sql = "DELETE FROM consulta WHERE id = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        }
    }

    private Consulta mapear(ResultSet rs) throws SQLException {
        Animal animal = new Animal();
        animal.setId(rs.getLong("animal_id"));

        return new Consulta(
                rs.getLong("id"),
                animal,
                rs.getObject("data_atendimento", java.time.LocalDate.class),
                rs.getString("motivo"),
                rs.getBigDecimal("valor")
        );
    }
}

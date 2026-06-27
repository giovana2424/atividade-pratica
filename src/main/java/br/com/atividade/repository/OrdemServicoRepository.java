package br.com.atividade.repository;

import br.com.atividade.model.OrdemServico;
import br.com.atividade.model.Veiculo;
import br.com.atividade.util.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrdemServicoRepository {
    public OrdemServico salvar(OrdemServico ordemServico) throws SQLException {
        String sql = "INSERT INTO ordem_servico (descricao_problema, valor_servico, status, veiculo_id) VALUES (?, ?, ?, ?) RETURNING id";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, ordemServico.getDescricao());
            stmt.setBigDecimal(2, ordemServico.getValor());
            stmt.setString(3, ordemServico.getStatus());
            stmt.setLong(4, ordemServico.getVeiculo().getId());

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new OrdemServico(rs.getLong("id"),
                            ordemServico.getDescricao(),
                            ordemServico.getValor(),
                            ordemServico.getStatus(),
                            ordemServico.getVeiculo());
                }
                throw new SQLException("Erro ao obter o ID da ordem de serviço inserido.");
            }
        }
    }

    public Optional<OrdemServico> buscarPorId(Long id) throws SQLException {
        String sql = "SELECT * FROM ordem_servico WHERE id = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() ? Optional.of(mapear(rs)) : Optional.empty();
            }
        }
    }

    public List<OrdemServico> buscarPorVeiculo(Long idVeiculo) throws SQLException {
        String sql = """
            SELECT os.*,
                   v.placa AS placa
            FROM ordem_servico os
            JOIN veiculo v ON os.veiculo_id = v.id
            WHERE os.veiculo_id = ?
        """;

        List<OrdemServico> ordensServico = new ArrayList<>();

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, idVeiculo);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Veiculo veiculo = new Veiculo();
                    veiculo.setId(rs.getLong("veiculo_id"));
                    veiculo.setPlaca(rs.getString("placa"));

                    OrdemServico ordemServico = new OrdemServico(
                            rs.getLong("id"),
                            rs.getString("descricao_problema"),
                            rs.getBigDecimal("valor_servico"),
                            rs.getString("status"),
                            veiculo
                    );
                    ordensServico.add(ordemServico);
                }
            }
        }
        return ordensServico;
    }

    public List<OrdemServico> listarTodos() throws SQLException {
        String sql = "SELECT * FROM ordem_servico";
        List<OrdemServico> lista = new ArrayList<>();
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) lista.add(mapear(rs));
        }
        return lista;
    }

    public void atualizar(OrdemServico ordemServico) throws SQLException {
        String sql = "UPDATE ordem_servico SET descricao_problema = ?, valor_servico = ?, status = ?, veiculo_id = ? WHERE id = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, ordemServico.getDescricao());
            stmt.setBigDecimal(2, ordemServico.getValor());
            stmt.setString(3, ordemServico.getStatus());
            stmt.setLong(4, ordemServico.getVeiculo().getId());
            stmt.setLong(5,ordemServico.getId());
            stmt.executeUpdate();
        }
    }

    public void deletar(Long id) throws SQLException {
        String sql = "DELETE FROM ordem_servico WHERE id = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        }
    }

    private OrdemServico mapear(ResultSet rs) throws SQLException {
        Veiculo veiculo = new Veiculo();
        veiculo.setId(rs.getLong("veiculo_id"));

            return new OrdemServico(
                rs.getLong("id"),
                rs.getString("descricao_problema"),
                rs.getBigDecimal("valor_servico"),
                rs.getString("status"),
                veiculo
        );
    }
}

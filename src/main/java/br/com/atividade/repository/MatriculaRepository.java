package br.com.atividade.repository;

import br.com.atividade.model.Aluno;
import br.com.atividade.model.Curso;
import br.com.atividade.model.Matricula;
import br.com.atividade.util.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MatriculaRepository {
    public Matricula salvar(Matricula matricula) throws SQLException {
        String sqlMatricula = """
            INSERT INTO matricula (data_matricula, valor, aluno_id, curso_id)
            VALUES (?, ?, ?, ?) RETURNING id
        """;

        String sqlCursoVagas = """
            UPDATE curso SET vagas_disponiveis = vagas_disponiveis - 1
            WHERE id = ?
        """;

        Connection conn = Conexao.getConnection();
        try {
            conn.setAutoCommit(false);

            PreparedStatement stmtMatricula = conn.prepareStatement(sqlMatricula);
            stmtMatricula.setDate(1, java.sql.Date.valueOf(matricula.getDataMatricula()));
            stmtMatricula.setBigDecimal(2, matricula.getValor());
            stmtMatricula.setLong(3, matricula.getAluno().getId());
            stmtMatricula.setLong(4, matricula.getCurso().getId());

            ResultSet rs = stmtMatricula.executeQuery();
            rs.next();
            Long idMatricula = rs.getLong("id");
            matricula.setId(idMatricula);

            PreparedStatement stmtCurso = conn.prepareStatement(sqlCursoVagas);
            stmtCurso.setLong(1, matricula.getCurso().getId());
            stmtCurso.executeUpdate();

            conn.commit();
            return matricula;

        } catch (SQLException e) {
            conn.rollback();
            throw e;
        } finally {
            conn.close();
        }
    }

    public Optional<Matricula> buscarPorId(Long id) throws SQLException {
        String sql = "SELECT * FROM matriculoa WHERE id = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() ? Optional.of(mapear(rs)) : Optional.empty();
            }
        }
    }

    public List<Aluno> buscarAlunosPorCurso(Long idCurso) throws SQLException {
        String sql = """
            SELECT a.*
            FROM aluno a
            JOIN matricula m ON a.id = m.aluno_id
            WHERE m.curso_id = ?
        """;
        List<Aluno> lista = new ArrayList<>();
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, idCurso);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    lista.add(new Aluno(
                            rs.getLong("id"),
                            rs.getString("nome"),
                            rs.getString("email"),
                            rs.getString("telefone")
                    ));
                }
            }
        }
        return lista;
    }

    public List<Curso> buscarCursosPorAluno(Long idAluno) throws SQLException {
        String sql = """
            SELECT c.*
            FROM curso c
            JOIN matricula m ON c.id = m.curso_id
            WHERE m.aluno_id = ?
        """;
        List<Curso> lista = new ArrayList<>();
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, idAluno);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    lista.add(new Curso(
                            rs.getLong("id"),
                            rs.getString("nome"),
                            rs.getString("descricao"),
                            rs.getInt("carga_horaria"),
                            rs.getInt("vagas_totais"),
                            rs.getInt("vagas_disponiveis")
                    ));
                }
            }
        }
        return lista;
    }

    public boolean existeMatricula(Long idAluno, Long idCurso) throws SQLException {
        String sql = "SELECT COUNT(*) FROM matricula WHERE aluno_id = ? AND curso_id = ?";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, idAluno);
            stmt.setLong(2, idCurso);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    public List<Matricula> listarTodos() throws SQLException {
        String sql = """
             SELECT m.id AS matricula_id,
                    m.data_matricula,
                    m.valor AS matricula_valor,
                    a.id AS aluno_id,
                    a.nome AS aluno_nome,
                    c.id AS curso_id,
                    c.nome AS curso_nome,
                    c.carga_horaria AS curso_carga
             FROM matricula m
             JOIN aluno a ON m.aluno_id = a.id
             JOIN curso c ON m.curso_id = c.id;
        """;

        List<Matricula> lista = new ArrayList<>();
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) lista.add(mapear(rs));
        }
        return lista;
    }

    public void atualizar(Matricula matricula) throws SQLException {
        String sql = "UPDATE matricula SET data_matricula = ?, valor = ?, aluno_id = ?, curso_id = ? WHERE id = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setObject(1, matricula.getDataMatricula());
            stmt.setBigDecimal(2, matricula.getValor());
            stmt.setLong(3, matricula.getAluno().getId());
            stmt.setLong(4, matricula.getCurso().getId());
            stmt.setLong(5, matricula.getId());
            stmt.executeUpdate();
        }
    }

    public void deletar(Long id) throws SQLException {
        String sql = "DELETE FROM matricula WHERE id = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        }
    }

    private Matricula mapear(ResultSet rs) throws SQLException {
        Aluno aluno = new Aluno();
        aluno.setId(rs.getLong("id"));
        aluno.setNome(rs.getString("nome"));

        Curso curso = new Curso();
        curso.setId(rs.getLong("id"));
        curso.setNome(rs.getString("nome"));
        curso.setCargaHoraria(rs.getInt("carga_horaria"));

        return new Matricula(
                rs.getLong("id"),
                rs.getObject("data_atendimento", java.time.LocalDate.class),
                rs.getBigDecimal("valor"),
                aluno,
                curso
        );
    }
}

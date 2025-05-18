package br.com.balistech.balistech.dao.saldo;

import br.com.balistech.balistech.dao.ConnectionManager;
import br.com.balistech.balistech.exception.DBException;
import br.com.balistech.balistech.model.saldo.Saldo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class OracleSaldoDao implements SaldoDao {
    private static final Logger logger = LoggerFactory.getLogger(ConnectionManager.class);
    private Connection conexao;

    @Override
    public void inserir(Saldo saldo) throws DBException {
        try {
            conexao = ConnectionManager.getInstance().getConnection();
            String sql = "INSERT INTO SALDO (ID_USUARIO, VALOR, ULTIMA_ATUALIZACAO) VALUES (?, ?, SYSDATE)";
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setLong(1, saldo.getIdUsuario());
            stmt.setDouble(2, saldo.getValor());
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            throw new DBException("Erro ao inserir saldo.", e);
        } finally {
            try {
                conexao.close();
            } catch (SQLException e) {
                logger.error("Erro ao fechar conexão", e);
            }
        }
    }

    @Override
    public void atualizar(Saldo saldo) throws DBException {
        try {
            conexao = ConnectionManager.getInstance().getConnection();
            String sql = "UPDATE SALDO SET VALOR = ?, ULTIMA_ATUALIZACAO = SYSDATE WHERE ID_USUARIO = ?";
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setDouble(1, saldo.getValor());
            stmt.setLong(2, saldo.getIdUsuario());
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            throw new DBException("Erro ao atualizar saldo.", e);
        } finally {
            try {
                conexao.close();
            } catch (SQLException e) {
                logger.error("Erro ao fechar conexão", e);
            }
        }
    }

    @Override
    public Saldo buscarPorUsuario(Long idUsuario) {
        Saldo saldo = null;
        try {
            conexao = ConnectionManager.getInstance().getConnection();
            String sql = "SELECT * FROM SALDO WHERE ID_USUARIO = ?";
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setLong(1, idUsuario);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                saldo = new Saldo(
                        rs.getLong("ID_SALDO"),
                        rs.getLong("ID_USUARIO"),
                        rs.getDouble("VALOR"),
                        rs.getDate("ULTIMA_ATUALIZACAO").toLocalDate()
                );
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            logger.error("Erro ao buscar saldo por usuário", e);
        } finally {
            try {
                conexao.close();
            } catch (SQLException e) {
                logger.error("Erro ao fechar conexão", e);
            }
        }
        return saldo;
    }
}
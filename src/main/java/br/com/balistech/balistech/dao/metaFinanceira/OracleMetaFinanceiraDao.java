package br.com.balistech.balistech.dao.metaFinanceira;

import br.com.balistech.balistech.dao.ConnectionManager;
import br.com.balistech.balistech.exception.DBException;
import br.com.balistech.balistech.model.metaFinanceira.MetaFinanceira;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OracleMetaFinanceiraDao implements MetaFinanceiraDao {

    @Override
    public void cadastrar(MetaFinanceira meta) throws DBException {
        try (Connection conexao = ConnectionManager.getInstance().getConnection()) {
            String sql = "INSERT INTO META_FINANCEIRA (ID_USUARIO, DESCRICAO, VALOR_META, DATA_LIMITE, VALOR_ATUAL) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = conexao.prepareStatement(sql);

            stmt.setLong(1, meta.getIdUsuario());
            stmt.setString(2, meta.getDescricao());
            stmt.setDouble(3, meta.getValorMeta());
            stmt.setDate(4, Date.valueOf(meta.getDataLimite()));
            stmt.setDouble(5, meta.getValorAtual());

            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            throw new DBException("Erro ao cadastrar meta financeira.", e);
        }
    }

    @Override
    public void atualizar(MetaFinanceira meta) throws DBException {
        try (Connection conexao = ConnectionManager.getInstance().getConnection()) {
            String sql = "UPDATE META_FINANCEIRA SET DESCRICAO = ?, VALOR_META = ?, DATA_LIMITE = ?, VALOR_ATUAL = ? WHERE ID_META = ?";
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setString(1, meta.getDescricao());
            stmt.setDouble(2, meta.getValorMeta());
            stmt.setDate(3, Date.valueOf(meta.getDataLimite()));
            stmt.setDouble(4, meta.getValorAtual());
            stmt.setLong(5, meta.getId());
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            throw new DBException("Erro ao atualizar meta financeira.", e);
        }
    }

    @Override
    public void remover(Long id) throws DBException {
        try (Connection conexao = ConnectionManager.getInstance().getConnection()) {
            String sql = "DELETE FROM META_FINANCEIRA WHERE ID_META = ?";
            PreparedStatement stmt = conexao.prepareStatement(sql);

            stmt.setLong(1, id);
            stmt.executeUpdate();

            stmt.close();
        } catch (SQLException e) {
            throw new DBException("Erro ao remover meta financeira.", e);
        }
    }

    @Override
    public List<MetaFinanceira> buscarPorUsuario(Long idUsuario) {
        List<MetaFinanceira> lista = new ArrayList<>();
        try (Connection conexao = ConnectionManager.getInstance().getConnection()) {
            String sql = "SELECT * FROM META_FINANCEIRA WHERE ID_USUARIO = ?";
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setLong(1, idUsuario);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                MetaFinanceira meta = new MetaFinanceira();
                meta.setId(rs.getLong("ID_META"));
                meta.setIdUsuario(rs.getLong("ID_USUARIO"));
                meta.setDescricao(rs.getString("DESCRICAO"));
                meta.setValorMeta(rs.getDouble("VALOR_META"));
                meta.setDataLimite(rs.getDate("DATA_LIMITE").toLocalDate());
                meta.setValorAtual(rs.getDouble("VALOR_ATUAL"));
                lista.add(meta);
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public MetaFinanceira buscarPorId(Long id) {
        MetaFinanceira meta = null;
        try (Connection conexao = ConnectionManager.getInstance().getConnection()) {
            String sql = "SELECT * FROM META_FINANCEIRA WHERE ID_META = ?";
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                meta = new MetaFinanceira();
                meta.setId(rs.getLong("ID_META"));
                meta.setIdUsuario(rs.getLong("ID_USUARIO"));
                meta.setDescricao(rs.getString("DESCRICAO"));
                meta.setValorMeta(rs.getDouble("VALOR_META"));
                meta.setDataLimite(rs.getDate("DATA_LIMITE").toLocalDate());
                meta.setValorAtual(rs.getDouble("VALOR_ATUAL"));
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return meta;
    }
}

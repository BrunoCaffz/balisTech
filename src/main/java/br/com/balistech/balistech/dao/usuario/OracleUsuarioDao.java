package br.com.balistech.balistech.dao.usuario;

import br.com.balistech.balistech.dao.ConnectionManager;
import br.com.balistech.balistech.exception.DBException;
import br.com.balistech.balistech.model.usuario.Usuario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OracleUsuarioDao implements UsuarioDao{
    private static final Logger logger = LoggerFactory.getLogger(ConnectionManager.class);
    private Connection conexao;

    @Override
    public void cadastrar(Usuario usuario) throws DBException {
        try {
            conexao = ConnectionManager.getInstance().getConnection();
            String sql = "INSERT INTO USUARIO (NOME, EMAIL, SENHA, TIPO_USUARIO, DATA_CADASTRO) " +
                    "VALUES (?, ?, ?, ?, SYSDATE)";
            PreparedStatement stmt = conexao.prepareStatement(sql);

            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getEmail());
            stmt.setString(3, usuario.getSenha());
            stmt.setString(4, usuario.getTipoUsuario());

            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            throw new DBException("Erro ao cadastrar usuário.", e);
        } finally {
            try {
                conexao.close();
            } catch (SQLException e) {
                logger.error("Erro ao cadastrar usuário", e);
            }
        }
    }

    @Override
    public Usuario buscarPorEmailSenha(String email, String senha) {
        Usuario usuario = null;

        try {
            conexao = ConnectionManager.getInstance().getConnection();
            String sql = "SELECT * FROM USUARIO WHERE EMAIL = ? AND SENHA = ?";
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setString(1, email);
            stmt.setString(2, senha);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                usuario = new Usuario(
                        rs.getLong("ID_USUARIO"),
                        rs.getString("NOME"),
                        rs.getString("EMAIL"),
                        rs.getString("SENHA"),
                        rs.getString("TIPO_USUARIO"),
                        rs.getDate("DATA_CADASTRO").toLocalDate()
                );
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                conexao.close();
            } catch (SQLException e) {
                logger.error("Não encontrou nenhum e-mail e senha", e);
            }
        }
        return usuario;
    }

    @Override
    public Usuario buscarPorId(Long id) {
        Usuario usuario = null;

        try {
            conexao = ConnectionManager.getInstance().getConnection();
            String sql = "SELECT * FROM USUARIO WHERE ID_USUARIO = ?";
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setLong(1, id);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                usuario = new Usuario(
                        rs.getLong("ID_USUARIO"),
                        rs.getString("NOME"),
                        rs.getString("EMAIL"),
                        rs.getString("SENHA"),
                        rs.getString("TIPO_USUARIO"),
                        rs.getDate("DATA_CADASTRO").toLocalDate()
                );
            }
            rs.close();
            stmt.close();

        } catch (SQLException e) {
            logger.error("Erro ao buscar por ID", e);
        } finally {
            try {
                conexao.close();
            } catch (SQLException e) {
                logger.error("Erro geral", e);
            }
        }
        return usuario;
    }

    @Override
    public List<Usuario> listar() {
        List<Usuario> usuarios = new ArrayList<>();
        try {
            conexao = ConnectionManager.getInstance().getConnection();

            String sql = "SELECT * FROM USUARIO";
            PreparedStatement stmt = conexao.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                Usuario usuario = new Usuario(
                        rs.getLong("ID_USUARIO"),
                        rs.getString("NOME"),
                        rs.getString("EMAIL"),
                        rs.getString("SENHA"),
                        rs.getString("TIPO_USUARIO"),
                        rs.getDate("DATA_CADASTRO").toLocalDate()
                );
                usuarios.add(usuario);
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            logger.error("Erro ao listar usuarios", e);
        } finally {
            try {
                conexao.close();
            } catch (SQLException e) {
                logger.error("Erro geral", e);
            }
        }
        return usuarios;
    }

    @Override
    public void atualizar(Usuario usuario) throws DBException {
        try {
            conexao = ConnectionManager.getInstance().getConnection();
            String sql = "UPDATE USUARIO SET NOME = ?, EMAIL = ?, SENHA = ?, TIPO_USUARIO = ? WHERE ID_USUARIO = ?";
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getEmail());
            stmt.setString(3, usuario.getSenha());
            stmt.setString(4, usuario.getTipoUsuario());
            stmt.setLong(5, usuario.getId());
            stmt.executeUpdate();
            stmt.close();

        } catch (SQLException e) {
            throw new DBException("Erro ao atualizar usuário.", e);
        } finally {
            try {
                conexao.close();
            } catch (SQLException e) {
                logger.error("Erro ao atualizar ", e);
            }
        }
    }

    @Override
    public void remover(Long id) throws DBException {
        try {
            conexao = ConnectionManager.getInstance().getConnection();
            String sql = "DELETE FROM USUARIO WHERE ID_USUARIO = ?";
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setLong(1, id);
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            throw new DBException("Erro ao remover usuário.", e);
        } finally {
            try {
                conexao.close();
            } catch (SQLException e) {
                logger.error("Erro ao remover usuário ", e);
            }
        }
    }
}

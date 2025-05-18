package br.com.balistech.balistech.dao.usuario;

import br.com.balistech.balistech.exception.DBException;
import br.com.balistech.balistech.model.usuario.Usuario;

import java.util.List;

public interface UsuarioDao {
    void cadastrar(Usuario usuario) throws DBException;

    Usuario buscarPorEmailSenha(String email, String senha);

    Usuario buscarPorId(Long id);

    List<Usuario> listar();

    void atualizar(Usuario usuario) throws DBException;

    void remover(Long id) throws DBException;
}

package br.com.balistech.balistech.dao.saldo;

import br.com.balistech.balistech.exception.DBException;
import br.com.balistech.balistech.model.saldo.Saldo;

public interface SaldoDao {
    void inserir(Saldo saldo) throws DBException;
    void atualizar(Saldo saldo) throws DBException;
    Saldo buscarPorUsuario(Long idUsuario);
}
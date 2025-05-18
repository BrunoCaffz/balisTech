package br.com.balistech.balistech.dao;

import br.com.balistech.balistech.dao.metaFinanceira.MetaFinanceiraDao;
import br.com.balistech.balistech.dao.metaFinanceira.OracleMetaFinanceiraDao;
import br.com.balistech.balistech.dao.usuario.OracleUsuarioDao;
import br.com.balistech.balistech.dao.usuario.UsuarioDao;
import br.com.balistech.balistech.dao.saldo.OracleSaldoDao;
import br.com.balistech.balistech.dao.saldo.SaldoDao;

public class DaoFactory {
    public static UsuarioDao getUsuarioDao() {
        return new OracleUsuarioDao();
    }

    public static SaldoDao getSaldoDao() {
        return new OracleSaldoDao();
    }

    public static MetaFinanceiraDao getMetaFinanceiraDao() {
        return new OracleMetaFinanceiraDao();
    }
}

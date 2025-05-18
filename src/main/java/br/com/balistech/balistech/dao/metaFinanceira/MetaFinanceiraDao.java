package br.com.balistech.balistech.dao.metaFinanceira;

import br.com.balistech.balistech.exception.DBException;
import br.com.balistech.balistech.model.metaFinanceira.MetaFinanceira;

import java.util.List;

public interface MetaFinanceiraDao {
    void cadastrar(MetaFinanceira meta) throws DBException;
    void atualizar(MetaFinanceira meta) throws DBException;
    void remover(Long id) throws DBException;
    List<MetaFinanceira> buscarPorUsuario(Long idUsuario);
    MetaFinanceira buscarPorId(Long id);
}
package br.com.balistech.balistech.dao;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;

@Slf4j
public class ConnectionManager {
    private static final Logger logger = LoggerFactory.getLogger(ConnectionManager.class);
    private static ConnectionManager connectionManager;

    public ConnectionManager() {
    }

    public static ConnectionManager getInstance() {
        if (connectionManager == null) {
            connectionManager = new ConnectionManager();
        }
        return connectionManager;
    }

    public Connection getConnection() {
        Connection connection = null;

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            connection = DriverManager.getConnection("jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCL","rm560021","040803");
        }catch (Exception e) {
            logger.error("Erro ao conectar ao banco de dados Oracle", e);
        }
        return connection;
    }
}

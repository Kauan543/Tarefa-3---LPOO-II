package com.mycompany.questao3_atividade_3;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

    public static Connection getConnection() throws SQLException{
        return DriverManager.getConnection("jdbc:mysql://localhost/livro", "root", "senha");

    }
}

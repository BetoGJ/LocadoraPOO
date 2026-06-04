package Sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;

public class BD{
    static private String url = "jdbc:mysql://localhost:3306/locadora";
    static private String usuario = "root";
    static private String senha = "root";
    public static Connection conectar() throws SQLException {
        Connection conexao = DriverManager.getConnection(url, usuario, senha);
        return conexao;
    }

}


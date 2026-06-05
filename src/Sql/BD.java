package Sql;

import java.sql.*;

public class BD{
    static private String url = "jdbc:mysql://localhost:3306/locadora";
    static private String usuario = "root";
    static private String senha = "root";
    static private Connection conexao = null;

    public static Connection conectar(){
        try{
            conexao = DriverManager.getConnection(url, usuario, senha);
            System.out.println("Conexão ao banco de dados com sucesso! ");
            return conexao;
        }
        catch (SQLException e){
            System.out.println("Erro ao conectar ao banco de dados!");
            e.printStackTrace();
            return null;
        }
    }

    public static Connection getConexao() {
        return conexao;
    }

    public static void novaLocadoraSQL(String CNPJ, String nome, String cidade) {
        String sql = "INSERT INTO Locadora (CNPJ, Nome, Cidade) VALUES (?, ?, ?)";

        try (PreparedStatement st = conexao.prepareStatement(sql)) {
            st.setString(1, CNPJ);
            st.setString(2, nome);
            st.setString(3, cidade);
            st.executeUpdate();
        }
        catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("Locadora já cadastrada!\n");
        }
        catch (SQLException e) {
            // MySQL: 1062 = duplicate entry (registro repetido)
            if (e.getErrorCode() == 1062) {
                System.out.println("Locadora já cadastrada!");
            } else {
                System.out.println("Erro ao inserir locadora. ");
                e.printStackTrace();
            }
        }
    }
}


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class bd {

    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/meu_banco";
        String usuario = "seu_usuario";
        String senha = "sua_senha";

        try {
            Connection conexao = DriverManager.getConnection(url, usuario, senha);
            System.out.println("Conexão estabelecida com sucesso!");

            conexao.close();
            System.out.println("Conexão fechada.");

        } catch (SQLException e) {
            System.out.println("Erro ao conectar ao banco de dados:");
            e.printStackTrace();
        }
    }
}

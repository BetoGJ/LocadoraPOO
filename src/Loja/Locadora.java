package Loja;
import java.sql.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Vector;
import Sql.BD;
import Loja.Generos.*;
import Programa.Menu;


public class Locadora {
    private String nome;
    private String CNPJ;
    private String cidade;
    private Vector<Filme> filmes = new Vector<>();
    private Vector<Cliente> clientes = new Vector<>();
    private Vector<Vendedor> vendedores = new Vector<>();
    private static Scanner sc = new Scanner(System.in);
    private Connection bd;
    public Locadora() {
        bd = BD.getConexao();
        updateFromSQL();
        System.out.print("Locadora: " + nome + "\nNome: " + nome + "\nCidade: " + cidade + "\n");
        System.out.println("Número de clientes: " + clientes.toArray().length);

    }

    public void updateFromSQL(){
        String sql = "SELECT CNPJ, Nome, Cidade FROM Locadora WHERE CNPJ='12.345.678/0001-95'";
        // LOCADORA
        try(PreparedStatement st = bd.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            ){
            if(rs.next()){
                this.CNPJ = rs.getString("CNPJ");
                this.nome = rs.getString("Nome");
                this.cidade = rs.getString("Cidade");
            }
        }catch(SQLException e){
            System.out.println("Erro SQL");
            e.printStackTrace();
        }
        sql = "SELECT CPF, Nome, Data_de_nascimento, Salario, AdminStatus, Senha from Vendedor WHERE Locadora_CNPJ='12.345.678/0001-95'";
        // VENDEDORES
        try(PreparedStatement st = bd.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
        ){
            while(rs.next()){
                Vendedor novoVendedor = new Vendedor(rs.getString("Nome"), rs.getString("CPF"), rs.getInt("Senha"), rs.getDate("Data_de_nascimento").toLocalDate(), rs.getFloat("Salario"), rs.getBoolean("AdminStatus"));
                vendedores.add(novoVendedor);
            }
        }catch(SQLException e){
            System.out.println("Erro SQL");
            e.printStackTrace();
        }
        // CLIENTES
        sql = "SELECT CPF, Nome, Data_de_nascimento, Senha from Cliente";
        try (
                PreparedStatement st = bd.prepareStatement(sql);
                ResultSet rs = st.executeQuery()
        ) {

            while (rs.next()) {

                Cliente novoCliente = new Cliente(
                        rs.getString("Nome"),
                        rs.getString("CPF"),
                        rs.getInt("Senha"),
                        rs.getDate("Data_de_nascimento").toLocalDate());

                clientes.add(novoCliente);
            }

        } catch (SQLException e) {
            System.out.println("Erro SQL");
            e.printStackTrace();
        }
        sql = "SELECT Id,Titulo,Ano,Diretor,Genero,Classificacao,Quantidade,Disponivel,Locadora_CNPJ FROM Filme WHERE Locadora_CNPJ='12.345.678/0001-95'";
        // Filmes
        try (PreparedStatement st = bd.prepareStatement(sql);
             ResultSet rs = st.executeQuery()
        ) {
            while (rs.next()) {
                Filme filme;
                switch (rs.getString("Genero").toLowerCase()) {
                    case "acao":
                        filme = new FilmeAcao(
                                rs.getString("Titulo"),
                                rs.getString("Classificacao"),
                                rs.getString("Diretor"),
                                rs.getInt("Ano"),
                                rs.getInt("Quantidade"),
                                rs.getInt("Disponivel")
                        );
                        break;
                    case "comedia":
                        filme = new FilmeComedia(
                                rs.getString("Titulo"),
                                rs.getString("Classificacao"),
                                rs.getString("Diretor"),
                                rs.getInt("Ano"),
                                rs.getInt("Quantidade"),
                                rs.getInt("Disponivel")
                        );
                        break;
                    case "suspense":
                        filme = new FilmeSuspense(
                                rs.getString("Titulo"),
                                rs.getString("Classificacao"),
                                rs.getString("Diretor"),
                                rs.getInt("Ano"),
                                rs.getInt("Quantidade"),
                                rs.getInt("Disponivel")
                        );
                        break;
                    case "romance":
                        filme = new FilmeRomance(
                                rs.getString("Titulo"),
                                rs.getString("Classificacao"),
                                rs.getString("Diretor"),
                                rs.getInt("Ano"),
                                rs.getInt("Quantidade"),
                                rs.getInt("Disponivel")
                        );
                        break;
                    case "terror":
                        filme = new FilmeTerror(
                                rs.getString("Titulo"),
                                rs.getString("Classificacao"),
                                rs.getString("Diretor"),
                                rs.getInt("Ano"),
                                rs.getInt("Quantidade"),
                                rs.getInt("Disponivel")
                        );
                        break;
                    default:
                        System.out.println("Gênero desconhecido: " + rs.getString("Genero"));
                        continue; // pula para o próximo filme
                }
                filme.setIdFilme(rs.getInt("Id"));
                filmes.add(filme);
            }
        } catch (SQLException e) {
            System.out.println("Erro SQL ao carregar filmes!");
            e.printStackTrace();
        }

    }


    public boolean login(int tipo){
        while(tipo == 1){
            Menu.reset();
            Menu.addOption("Cadastro");  // ---------Todos os 4 trocaram por métodos de uma interface
            Menu.addOption("Login");
            Menu.verificarOption();

            if(Menu.getOption() == 1){
                System.out.print("Digite o nome: ");
                String nomeNovo = sc.nextLine();

                while (nomeNovo.isBlank()) {
                    System.out.print("Nome inválido! Insira novamente: ");
                    nomeNovo = sc.nextLine();
                }

                String cpfNovo = Menu.scanCPF();

                while(buscarConta(clientes, cpfNovo) != null){
                    System.out.println("CPF já cadastrado!");
                    cpfNovo = Menu.scanCPF();
                }

                System.out.print("Digite a senha: ");
                String senhaNova = sc.nextLine();

                while (senhaNova.isBlank()) {
                    System.out.print("Senha inválida! Insira novamente: ");
                    senhaNova = sc.nextLine();
                }

                LocalDate dataNova = Menu.scanData();
                Cliente clienteNovo = new Cliente(nomeNovo, cpfNovo, senhaNova, dataNova);
                clientes.add(clienteNovo);
            }
            else{
                break;
            }
            System.out.println();
        }

        System.out.println("--Insira os dados da conta--");
        String cpfLogin = Menu.scanCPF();
        Conta contaAtual = null;
        int tentativas = 0;

        while(true) {
            try {
                if (tipo == 1) {
                    contaAtual = buscarConta(clientes, cpfLogin);
                }
                else if (tipo == 2) {
                    contaAtual = buscarConta(vendedores, cpfLogin);
                }

                System.out.print("Digite a senha: ");
                String senhaLogin = sc.nextLine();

                contaAtual.logar(cpfLogin, senhaLogin);

                if (tipo == 1 && contaAtual.isLogado()) {
                    Menu.menuCliente((Cliente) contaAtual);
                    return true;
                }
                else if (tipo == 2 && contaAtual.isLogado()) {
                    Menu.menuVendedor((Vendedor) contaAtual);
                    return true;
                }
            } catch (NullPointerException e) {
                System.out.println("Conta não encontrada!");
            }

            tentativas++;

            if (tentativas == 3) {
                System.out.println("Você errou 3 vezes consecutivas.");
                return false;
            }
        }
    }

    private Conta buscarConta(Vector<? extends Conta> contas, String cpf){
        for(Conta c : contas){
            if(c.getCpf().equals(cpf)){
                return c;
            }
        }
        return null;
    }

    public void addCliente(Cliente clienteNovo){

        clientes.add(clienteNovo);

        String sql =
                "INSERT INTO Cliente (CPF, Nome, Data_de_nascimento, Senha) " +
                        "VALUES (?, ?, ?, ?)";

        try(
                PreparedStatement st = bd.prepareStatement(sql)
        ){

            st.setString(1, Menu.limparCpf(clienteNovo.getCpf()));
            st.setString(2, clienteNovo.getNome());
            st.setDate(3,
                    java.sql.Date.valueOf(clienteNovo.getDataDeNascimento()));
            st.setInt(4, clienteNovo.getHashSenha());

            st.executeUpdate();

            System.out.println("Cliente inserido com sucesso!");
            }
        catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("Cliente já cadastrado!");
        }
        catch (SQLException e) {
            if (e.getErrorCode() == 1062) {
                System.out.println("Cliente já cadastrado! ");
            }
            else {
                System.out.println("Erro ao inserir cliente!");
            }
            e.printStackTrace();
        }
    }


    public void addVendedor(){
        try {
            System.out.print("Nome do vendedor: ");
            String nomeNovo = sc.nextLine();

            while (nomeNovo.isBlank()) {
                System.out.print("Nome inválido! Insira novamente:");
                nomeNovo = sc.nextLine();
            }

            System.out.print("CPF do vendedor. ");
            String cpfNovo = Menu.scanCPF();

            while (buscarConta(vendedores, cpfNovo) != null) {
                System.out.println("CPF já cadastrado!");
                cpfNovo = Menu.scanCPF();
            }

            System.out.print("Senha do vendedor: ");
            String senhaNova = sc.nextLine();

            while (senhaNova.isBlank()) {
                System.out.print("Senha inválida! Insira novamente: ");
                senhaNova = sc.nextLine();
            }

            System.out.print("Sobre o aniversario do vendedor. ");
            LocalDate dataNova = Menu.scanData();

            System.out.print("Salario do vendedor: ");
            float salarioNovo = sc.nextFloat();

            while (salarioNovo < 0) {
                System.out.print("Salário inválido! Insira novamente: ");         // --------Arrumar inserção de Float e de status
                salarioNovo = sc.nextFloat();
            }

            System.out.println("O vendedor tem acesso de administrador?");
            boolean statusNovo = false;
            Menu.reset(false);
            Menu.addOption("Sim");
            Menu.addOption("Não");
            Menu.verificarOption();
            sc.nextLine();

            if (Menu.getOption() == 1) {
                statusNovo = true;
            }

            Vendedor vendedorNovo = new Vendedor(nomeNovo, cpfNovo, senhaNova, dataNova, salarioNovo, statusNovo);

            vendedores.add(vendedorNovo);
            System.out.println("Vendedor inserido com sucesso!\n");

        } catch (InputMismatchException e) {
            System.out.println("Valor inválido insirido!");
            sc.nextLine();
        }
    }

    public void addVendedor(Vendedor vendedorNovo){

        vendedores.add(vendedorNovo);

        String sql =
                "INSERT INTO Vendedor " +
                        "(CPF, Nome, Salario, Data_de_nascimento, Senha, Locadora_CNPJ, AdminStatus) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try(
                PreparedStatement st = bd.prepareStatement(sql)
        ){

            st.setString(1, Menu.limparCpf(vendedorNovo.getCpf()));
            st.setString(2, vendedorNovo.getNome());
            st.setFloat(3, vendedorNovo.getSalario());
            st.setDate(4,
                    java.sql.Date.valueOf(vendedorNovo.getDataDeNascimento()));
            st.setInt(5, vendedorNovo.getHashSenha());
            st.setString(6, CNPJ);
            st.setBoolean(7, vendedorNovo.isAdmin());
            st.executeUpdate();

            System.out.println("Vendedor inserido com sucesso!");

        }
        catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("Vendedor já cadastrado!");
        }
        catch (SQLException e) {
            if (e.getErrorCode() == 1062) {
                System.out.println("Vendedor já cadastrado!");
            }
            else {
                System.out.println("Erro ao inserir vendedor!");
            }
            e.printStackTrace();
        }
    }




    public void promoverVendedor(){
        Vendedor vendedorAtual = null;
        while(true){
            vendedorAtual = (Vendedor) buscarConta(vendedores, Menu.scanCPF());
            if(vendedorAtual==null){
                System.out.println("CPF não encontrado! ");
            }
            else{
                break;
            }
        }
        Menu.reset(false);
        if(vendedorAtual.isAdmin()){
            System.out.println("O vendedor já é admin, deseja remover o admin?");

            Menu.addOption("Sim");
            Menu.addOption("Não");
        }
        else{
            System.out.println("O vendedor não é admin, deseja promovê-lo a admin?");
            Menu.addOption("Sim");
            Menu.addOption("Não");
        }
        Menu.verificarOption();
        if(Menu.getOption()==1) {
            vendedorAtual.setAdmin(!vendedorAtual.isAdmin());
            String sql = "UPDATE Vendedor SET AdminStatus = ? WHERE CPF = ?";
            try (PreparedStatement st = bd.prepareStatement(sql)) {
                st.setBoolean(1, vendedorAtual.isAdmin());
                st.setString(2, Menu.limparCpf(vendedorAtual.getCpf()));
                st.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Status do admin ["+vendedorAtual.isAdmin()+"]: "+vendedorAtual.isAdmin());
        }
    }

    public void addFilme(){
        try {
            System.out.print("Título do filme: ");
            String titulo = sc.nextLine();

            while (titulo.isBlank()) {
                System.out.print("Título inválido! Insira novamente: ");
                titulo = sc.nextLine();
            }

            System.out.print("Classificação do filme: ");
            String classificacao = sc.nextLine();

            while (classificacao.isBlank()) {
                System.out.print("Classificação inválida! Insira novamente: ");
                classificacao = sc.nextLine();
            }

            System.out.print("Diretor do filme: ");
            String diretor = sc.nextLine();

            while (diretor.isBlank()) {
                System.out.print("Diretor inválido! Insira novamente: ");
                diretor = sc.nextLine();
            }

            System.out.print("Gênero (acao, comedia, suspense,terror,romance): ");
            String genero = sc.nextLine().toLowerCase();

            while (!genero.equals("acao") && !genero.equals("comedia") && !genero.equals("suspense") && !genero.equals("terror") && !genero.equals("romance")) {
                System.out.print("Gênero inválido! Insira novamente: ");
                genero = sc.nextLine();
            }

            System.out.print("Ano de lançamento do filme: ");
            int ano = sc.nextInt();

            System.out.print("Quantidade de cópias do filme: ");
            int quantidade = sc.nextInt();
            sc.nextLine();

            Filme filme;

            switch (genero) {
                case "acao":
                    filme = new FilmeAcao(titulo, classificacao, diretor, ano, quantidade, quantidade);
                    break;
                case "comedia":
                    filme = new FilmeComedia(titulo, classificacao, diretor, ano, quantidade, quantidade);
                    break;
                case "suspense":
                    filme = new FilmeSuspense(titulo, classificacao, diretor, ano, quantidade, quantidade);
                    break;
                case "romance":
                    filme = new FilmeRomance(titulo, classificacao, diretor, ano, quantidade, quantidade);
                    break;
                case "terror":
                    filme = new FilmeTerror(titulo, classificacao, diretor, ano, quantidade, quantidade);
                    break;
                default:
                    System.out.println("Gênero inválido!");
                    return;
            }



            String sql =
                    "INSERT INTO Filme " +
                            "(Titulo,Ano, Diretor, Genero, Classificacao, Quantidade, Disponivel, Locadora_CNPJ) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

            try(
                    PreparedStatement st = bd.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
            ){

                st.setString(1, filme.getTitulo());
                st.setInt(2, filme.getAnoLancamento());
                st.setString(3, filme.getDiretor());
                st.setString(4, genero);
                st.setString(5, filme.getClassificacao());
                st.setInt(6, filme.getQuantidade());
                st.setInt(7, filme.getDisponivel());
                st.setString(8, CNPJ);
                st.executeUpdate();

                try (ResultSet keys = st.getGeneratedKeys()) {
                    if (keys.next()) {
                        filme.setIdFilme(keys.getInt(1));
                    }
                }

                filmes.add(filme);
                System.out.println("Filme inserido com sucesso!");
            }
            catch (SQLException e) {
                System.out.println("Erro ao inserir vendedor!");
                e.printStackTrace();
            }
        } catch (InputMismatchException e) {
            System.out.println("Ano e quantidade devem ser números!\n");
            sc.nextLine();
        }

    }

    public void RemoverFilme() {
        for (Filme filme : filmes) {
            System.out.println("ID do filme      : " + filme.getIdFilme());
            System.out.println("Título           : " + filme.getTitulo());
            System.out.println("Classificação    : " + filme.getClassificacao());
            System.out.println("Diretor          : " + filme.getDiretor());
            System.out.println("Ano de lançamento: " + filme.getAnoLancamento());
            System.out.println("Quantidade total : " + filme.getQuantidade());
            System.out.println("Disponíveis      : " + filme.getDisponivel());
            filme.descricaoGenero();
        }

        try {
            System.out.print("Informe o id do filme: ");
            int busca = sc.nextInt();
            sc.nextLine();

            for (int i = 0; i < filmes.size(); i++) {
                if (filmes.get(i).getIdFilme() == busca) {
                    System.out.println("Filme removido!\n");
                    String sql = "DELETE FROM Filme WHERE Id = ?";
                    try (PreparedStatement st = bd.prepareStatement(sql)) {
                        st.setInt(1, filmes.get(i).getIdFilme());
                        st.executeUpdate();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    filmes.remove(i);
                    return;
                }
            }
            System.out.println("Filme não encontrado!\n");
        } catch (InputMismatchException e) {
            System.out.println("Insira apenas numeros no id!\n");
        }
    }

    public void RemoverVendedor(Vendedor vendedorAtual) {
        for (Vendedor v : vendedores) {
            System.out.println("Nome do vendedor: " + v.getNome());
            System.out.println("CPF             : " + v.getCpf());
            System.out.println("Salário         : " + v.getSalario());
            System.out.println("Status de admin : " + v.isAdmin() + "\n");
        }

        try {
            System.out.print("Informe o CPF do vendedor. ");
            String busca = Menu.scanCPF();

            if (vendedorAtual.getCpf().equals(busca)) {
                System.out.println("Apenas seus superiores podem excluir sua conta. \n");
                return;
            }

            for (int i = 0; i < vendedores.size(); i++) {
                if (vendedores.get(i).getCpf().equals(busca)) {
                    String sql = "DELETE FROM Vendedor WHERE CPF = ?";
                    try (PreparedStatement st = bd.prepareStatement(sql)) {
                        st.setString(1, Menu.limparCpf(vendedores.get(i).getCpf()));
                        st.executeUpdate();
                    } catch (SQLException e) {
                        System.out.println("Erro ao remover vendedor do banco!");
                        e.printStackTrace();
                        return;
                    }
                    vendedores.remove(i);
                    System.out.println("Vendedor removido!\n");
                    return;
                }
            }

            System.out.println("Vendedor não encontrado!\n");
        } catch (InputMismatchException e) {
            System.out.println("Valor inválido inserido!\n");
        }


    }

    public Vector<Filme> getFilmes() {
        return filmes;
    }

    public void verificaMultas() {
        for (Cliente cliente : clientes) {

            for (Emprestimo emp : cliente.getEmprestimos()) {

                if (emp.getDevolvido() == null && LocalDate.now().isAfter(emp.getDevolucao())) {

                    float valor = ChronoUnit.DAYS.between(emp.getDevolucao(), LocalDate.now());
                    Multa multaExistente = null;

                    for (Multa mul : cliente.getMultas()) {
                        if (mul.getIdEmprestimo() == emp.getIdEmprestimo()) {
                            multaExistente = mul;
                            break;
                        }
                    }

                    if (multaExistente != null && multaExistente.getDataDePagamento() == null) {
                        multaExistente.setValor(valor);
                    } else if(multaExistente == null){
                        cliente.addMulta(new Multa(emp.getIdEmprestimo(), valor, LocalDate.now(),cliente.getCpf()));
                    }
                }
            }
        }
    }

    public String getNome() {
        return nome;
    }
}

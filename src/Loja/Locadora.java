package Loja;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Vector;
import Sql.BD;
import Loja.Generos.*;
import Programa.Menu;


public class Locadora {
    private final String nome;
    private String CNPJ;
    private String cidade;
    private Vector<Filme> filmes = new Vector<>();
    private Vector<Cliente> clientes = new Vector<>();
    private Vector<Vendedor> vendedores = new Vector<>();
    private static Scanner sc = new Scanner(System.in);
    private String sql;
    private Connection bd;
    public Locadora(String nome, String CNPJ, String cidade) {
        this.nome = nome;
        this.CNPJ = CNPJ;
        this.cidade = cidade;
        try{
            bd = BD.conectar();
            System.out.println("Conexão ao banco de dados com sucesso! ");
            novaLocadoraSQL();
        }
        catch (SQLException e){
            System.out.println("Erro ao conectar ao banco de dados!");
            e.printStackTrace();
        }
    }

    public void novaLocadoraSQL() {
        String sql = "INSERT INTO Locadora (CNPJ, Nome, Cidade) VALUES (?, ?, ?)";

        try (PreparedStatement st = bd.prepareStatement(sql)) {

            st.setString(1, CNPJ);
            st.setString(2, nome);
            st.setString(3, cidade);

            st.executeUpdate();

        }
        catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("Locadora já cadastrada!");
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

    public boolean login(int tipo){
        while(tipo == 1){
            Menu.reset();
            Menu.addOption("Cadastro");  // ---------Todos os 4 trocaram por métodos de uma interfaçe
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
                        "(CPF, Nome, Salario, Data_de_nascimento, Senha, Locadora_CNPJ) " +
                        "VALUES (?, ?, ?, ?, ?, ?)";

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

            filmes.add(filme);
            System.out.println("Filme inserido com sucesso!\n");

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
                    filmes.remove(i);
                    System.out.println("Filme removido!\n");
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

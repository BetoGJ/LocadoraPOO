package Loja;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Vector;

import Loja.Generos.*;
import Programa.Menu;


public class Locadora {
    private final String nome;
    private Vector<Filme> filmes = new Vector<>();
    private Vector<Cliente> clientes = new Vector<>();
    private Vector<Vendedor> vendedores = new Vector<>();
    private static Scanner sc = new Scanner(System.in);

    public Locadora(String nome) {
        this.nome = nome;
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
                    System.out.println("Senha inválida! Iniciando novamente: ");
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
                }
                else if (tipo == 2 && contaAtual.isLogado()) {
                    Menu.menuVendedor((Vendedor) contaAtual);
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

            while(buscarConta(vendedores, cpfNovo) != null){
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

            System.out.print("O vendedor tem acesso de administrador? (true ou false) ");
            boolean statusNovo = sc.nextBoolean();
            sc.nextLine();

            Vendedor vendedorNovo = new Vendedor(nomeNovo, cpfNovo, senhaNova, dataNova, salarioNovo, statusNovo);

            vendedores.add(vendedorNovo);
            System.out.println("Vendedor inserido com sucesso!\n");

        } catch (InputMismatchException e) {
            System.out.println("Valor inválido insirido!");
            sc.nextLine();
        }
    }

    public void addVendedor(Vendedor vendedorNovo){ // Usado pra facilitar a criação do primeiro vendedor
        vendedores.add(vendedorNovo);
        System.out.println("Vendedor inserido com sucesso!\n");
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

            if (titulo.isBlank()) {
                System.out.println("Título inválido!");
                return;
            }

            System.out.print("Classificação do filme: ");
            String classificacao = sc.nextLine();

            System.out.print("Diretor do filme: ");
            String diretor = sc.nextLine();

            System.out.print("Gênero (acao, comedia, suspense,terror,romance): ");
            String genero = sc.nextLine().toLowerCase();

            System.out.print("Ano de lançamento do filme: ");
            int ano = sc.nextInt();

            System.out.print("Quantidade de cópias do filme: ");
            int quantidade = sc.nextInt();
            sc.nextLine();

            Filme filme;

            switch (genero) {
                case "acao":
                    filme = new FilmeAcao(titulo, classificacao, diretor, ano, quantidade, quantidade, genero);
                    break;
                case "comedia":
                    filme = new FilmeComedia(titulo, classificacao, diretor, ano, quantidade, quantidade, genero);
                    break;
                case "suspense":
                    filme = new FilmeSuspense(titulo, classificacao, diretor, ano, quantidade, quantidade, genero);
                    break;
                case "romance":
                    filme = new FilmeRomance(titulo, classificacao, diretor, ano, quantidade, quantidade, genero);
                    break;
                case "terror":
                    filme = new FilmeTerror(titulo, classificacao, diretor, ano, quantidade, quantidade, genero);
                    break;
                default:
                    System.out.println("Gênero inválido!");
                    return;
            }

            filmes.add(filme);
            System.out.println("Filme inserido com sucesso!\n");

        } catch (InputMismatchException e) {
            System.out.println("Ano e quantidade devem ser números!");
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

    public void RemoverVendedor() {
        for (Vendedor vendedor : vendedores) {
            System.out.println("Nome do vendedor: " + vendedor.getNome());
            System.out.println("CPF             : " + vendedor.getCpf());
            System.out.println("Salário         : " + vendedor.getSalario());
        }

        try {
            System.out.print("\nInforme o CPF do Vendedor: ");
            String busca = Menu.scanCPF();

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

    public Vector<Vendedor> getVendedores(Vendedor acesso) {
        if(acesso.isAdmin()) {
            return vendedores;
        }
        else{
            return null;
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

                    if (multaExistente != null) {
                        multaExistente.setValor(valor);
                    } else {
                        cliente.addMulta(new Multa(emp.getIdEmprestimo(), valor, LocalDate.now()));
                    }
                }
            }
        }
    }
}

package Programa;

import Loja.*;
import Loja.Generos.FilmeAcao;
import Loja.Generos.FilmeComedia;
import Loja.Generos.FilmeSuspense;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Menu {
    private static int optionQuant = 0;
    private static int option;
    private static Scanner sc = new Scanner(System.in);
    private static Locadora locadoraAtual = new Locadora("Locafilmes");

    public static void start(){
        locadoraAtual.addCliente(new Cliente("Cliente1", "123.123.123-12", "senhasegura", LocalDate.parse("2000-03-02")));
        locadoraAtual.addVendedor(new Vendedor("Vendedor1", "321.321.321-32", "senhasegura", LocalDate.parse("2001-04-04"), 1900.00f, true));
        System.out.println("--Escolha um tipo de conta--"); // ----------Colocar isso dentre de reset quando estiver na interfaçe para variar de Menu e Locadora

        reset();
        addOption("Cliente");
        addOption("Vendedor");
        verificarOption();

        locadoraAtual.login(option);  // menu de login fica em Locadora
    }

    public static void reset(){ // ---------------Metodo tbm da interface
        optionQuant = 0;
        System.out.println("[0] - Sair do Banco de Dados");
    }

    public static void addOption(String option){ // ------------Adicionar na interfaçe tbm
        System.out.printf("[%d] - %s\n", ++optionQuant, option);
    }

    public static void verificarOption(){ // ---------------Colocar esse metodo numa interfaçe
        while(true) {
            try {
                System.out.print("Escolha uma opção: ");
                option = sc.nextInt();
                sc.nextLine();

                if (option == 0) {
                    sc.close();
                    System.exit(0);
                }
                else if (option > 0 && option <= optionQuant) {
                    System.out.println();
                    break;
                }

                System.out.println("Escolha uma opção válida!");

            } catch (InputMismatchException e) {
                System.out.println("Espirito de porco detectado. Insira um valor inteiro (pelo menos)!");
                sc.nextLine();
            }
        }
    }

    public static String scanCPF() {
        while (true) {
            System.out.print("Digite o CPF no formato (xxx.xxx.xxx-yy): ");
            String scannedCPF = sc.nextLine();

            if (scannedCPF.matches("\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}")) { // Regex obrigando a sequência de 3 algarismos e ponto literal e hífen
                return scannedCPF;
            }

            System.out.println("CPF inválido.");
        }
    }

    public static LocalDate scanData() {
        while (true) {

            try {
                System.out.print("Digite a data no formato AAAA-MM-DD: ");
                return LocalDate.parse(sc.nextLine());
            } catch (DateTimeParseException e) {
                System.out.println("Data inválida.");
            }

        }
    }

    public static void menuCliente(Cliente clienteAtual){
        reset();
        addOption("Alugar filme");
        addOption("Devolver filme");
        addOption("Pagar / conferir multa");
        verificarOption();

        switch (option){
            case 1:
                if(clienteAtual.getMultas().isEmpty()) {
                    String filmePesquisa;
                    System.out.println("Filme disponíveis : ");

                    for (Filme filme : locadoraAtual.getFilmes()) {
                        System.out.println(filme.getTitulo());
                    }

                    System.out.println("Qual o título do filme que deverá ser alugado?");
                    filmePesquisa = sc.nextLine();
                    for (Filme filme : locadoraAtual.getFilmes()) {
                        if (filme.getTitulo().equals(filmePesquisa)) {
                            Emprestimo emprestimoPlaceholder = new Emprestimo(locadoraAtual, filmePesquisa);
                            clienteAtual.addEmprestimo(emprestimoPlaceholder);

                        }
                    }
                }
                else{
                    System.out.println("Existem multas não pagas!");
                }
                break;
            case 2:
                if(clienteAtual.getEmprestimos().size() != 0){
                    System.out.println("Qual filme você deseja devolver?");
                    for(Emprestimo emprestimo : clienteAtual.getEmprestimos()){
                        System.out.println("ID do emprestimo : " + emprestimo.getIdEmprestimo());
                        System.out.println("Filme emprestado : " + emprestimo.getNomeFilme());
                        System.out.println("Data do emprestimo : " + emprestimo.getIdEmprestimo());
                        System.out.println("Data limite : " + emprestimo.getDevolucao());
                    }
                }
                break;

            case 3:
                if (clienteAtual.getMultas().size() != 0){
                    System.out.println("As seguintes multas devem ser pagas antes de qualquer empréstimo :");
                    for(Multa multas : clienteAtual.getMultas()){
                        System.out.println("ID da multa : " + multas.getId());
                        System.out.println("Valor da multa : " + multas.getValor());
                        System.out.println("Data de inicio da multa : " + multas.getDataDeInicio());
                    }
                    System.out.println("Qual multa você deseja quitar?");
                    int multaVerifica;
                    multaVerifica = sc.nextInt();
                    System.out.println("meu pix : 35992348448");

                }
                else{
                    System.out.println("Sem despesas!");
                }
        }

    }

    public static void menuVendedor(Vendedor vendedorAtual){
        while(true) {
            reset();
            addOption("Adicionar filme");
            addOption("Remover filme");
            if (vendedorAtual.isAdmin()) {
                addOption("Adicionar vendedor");
                addOption("Remover vendedor");
                addOption("Tornar vendedor admin / remover admin");
                System.out.print("Lista de vendedores: ");
                for (Vendedor v : locadoraAtual.getVendedores(vendedorAtual)) {
                    System.out.println(v.getNome());
                }
            }
            verificarOption();

            switch (option) {
                case 1:
                    adicionarFilme();
                    break;
                case 2:
                    removerFilme();
                    break;
                default:
            }
        }
    }

    private static void adicionarFilme() {
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

            System.out.print("Gênero (acao, comedia, suspense): ");
            String genero = sc.nextLine().toLowerCase();

            System.out.print("Ano de lançamento do filme: ");
            int ano = sc.nextInt();

            System.out.print("Quantidade de cópias do filme: ");
            int quantidade = sc.nextInt();
            sc.nextLine();

            Filme filme = null;

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
                default:
                    System.out.println("Gênero inválido!");
                    return;
            }

            locadoraAtual.addFilme(filme);
            System.out.println("Filme inserido com sucesso!");

        } catch (InputMismatchException e) {
            System.out.println("Ano e quantidade devem ser números!");
            sc.nextLine();
        }
    }

    private static void removerFilme() {
        System.out.print("Informe o título do filme: ");
        String titulo = sc.nextLine();

        if (titulo.isBlank()) {
            System.out.println("Título inválido!");
            return;
        }

        locadoraAtual.RemoveFilme(titulo);
        System.out.println("Remoção concluída!");
    }

    public static int getOption() {
        return option;
    }
}

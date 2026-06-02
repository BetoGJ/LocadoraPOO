package Programa;

import java.util.Scanner;

import Loja.*;

public class Menu {
    private static int optionQuant = 0;
    private static Scanner sc = new Scanner(System.in);
    private static int option;
    private static Locadora locadoraAtual = new Locadora("Locafilmes");

    public static void start(){
        locadoraAtual.addCliente(new Cliente("Cliente1",  "123.123.123-12", "senhasegura", "02/03/2000"));
        locadoraAtual.addVendedor(new Vendedor("Vendedor1", "321.321.321-32", "senhasegura", "04/04/2001", 1900.00f, true));

        System.out.println("--Escolha um tipo de conta ou finalize--"); // ----------Colocar isso dentre de reset quando estiver na interfaçe para variar de Menu e Locadora

        reset();
        addOption("Cliente");
        addOption("Vendedor");
        verificarOption();

        locadoraAtual.login(option);  // menu de login fica em Locadora
    }

    public static void reset(){ // ---------------Metodo tbm da interface
        optionQuant = 0;
        System.out.println("[0] - Sair");
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
                else {
                    System.out.println("Escolha uma opção válida!");
                }
            } catch (java.util.InputMismatchException e) {
                System.out.println("Espirito de porco detectado. Insira um valor inteiro (pelo menos)!");
                sc.nextLine();
            }
        }
    }

    public static boolean isNumeric(String str) {
        return str != null && str.matches("\\d+");     // Regex possibilitando um ou mais algarismos
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

    public static String scanData() {
        while (true) {
            System.out.print("Digite a data no formato dd/mm/aaaa: ");
            String scannedDate = sc.nextLine();

            if (scannedDate.matches("\\d{2}/\\d{2}/\\d{4}")) { // Regex obrigando a sequência de 2 algarismos seguido por /
                return scannedDate;
            }

            System.out.println("Data inválida.");
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
                String filmePesquisa;
                System.out.println("Filme disponíveis : ");
                for(Filme filme : locadoraAtual.getFilmes()){
                    System.out.println(filme.getTitulo());
                }
                System.out.println("Qual o título do filme que deverá ser alugado?");
                filmePesquisa = sc.nextLine();
                for(Filme filme : locadoraAtual.getFilmes()){
                    if(filme.getTitulo().equals(filmePesquisa)){
                        Emprestimo emprestimoPlaceholder = new Emprestimo();
                        clienteAtual.addEmprestimo(emprestimoPlaceholder);
                    }
                }

        }

    }

    public static void menuVendedor(Vendedor vendedorAtual){
        reset();
        addOption("Adicionar filme");
        addOption("Remover filme");
        if(vendedorAtual.isAdmin()){
            addOption("Adicionar vendedor");
            addOption("Remover vendedor");
            addOption("Tornar vendedor admin / remover admin");
            System.out.print("Lista de vendedores: ");
            for(Vendedor v: locadoraAtual.getVendedores(vendedorAtual)){
                System.out.println(v.getNome());
            }
        }
        verificarOption();

        switch(option){
            case 1:
                String tituloPlaceholder;
                String classificacaoPlaceholder;
                String diretorPlaceholder;
                int anoPlaceholder;
                int quantidadePlaceholder;

                System.out.println("Informe o título do filme : ");
                tituloPlaceholder = sc.nextLine();
                System.out.println("Informe a classificação do filme");
                classificacaoPlaceholder = sc.nextLine();
                System.out.println("Informe o diretor do filme");
                diretorPlaceholder = sc.nextLine();
                System.out.println("Informe o ano de lançamento do filme");
                anoPlaceholder = sc.nextInt();
                System.out.println("Informe a quantitade de cópias do filme");
                quantidadePlaceholder = sc.nextInt();
                Filme filme1 = new Filme(tituloPlaceholder,classificacaoPlaceholder,diretorPlaceholder,anoPlaceholder,quantidadePlaceholder,quantidadePlaceholder);
                locadoraAtual.addFilme(filme1);
                System.out.println("Filme : " + tituloPlaceholder + " Inserido!");
                break;

            case 2:
                System.out.println("Informe o titulo do filme que você deseja remover");
                tituloPlaceholder = sc.nextLine();
                locadoraAtual.RemoveFilme(tituloPlaceholder);
                break;

        }
    }

    public static int getOption() {
        return option;
    }
}

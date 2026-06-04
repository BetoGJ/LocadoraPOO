package Programa;

import Loja.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Menu {
    private static int optionQuant = 0;
    private static int option;
    private static Scanner sc = new Scanner(System.in);
    private static Locadora locadoraAtual;
    private static boolean enableOptionZero = true;

    public static void start(Locadora locadoraNova){
        locadoraAtual = locadoraNova;
        System.out.println("--- Bem vindo a " + locadoraAtual.getNome() + " ---\n");
        while(true){
            System.out.println("--Escolha um tipo de conta--");

            reset();
            addOption("Cliente");
            addOption("Vendedor");
            verificarOption();

            if(option == 0){
                break;
            }

            boolean loginConcluido = locadoraAtual.login(option);

            if(!loginConcluido){
                System.out.println("Reiniciando processo...\n");
            }
        }
    }

    public static void reset(){
        reset(true);
    }

    public static void reset(boolean optionZero){ // ---------------Metodo tbm da interface
        optionQuant = 0;
        enableOptionZero = optionZero;
        if(optionZero) System.out.println("[0] - Sair do Banco de Dados");
    }

    public static void addOption(String option){ // ------------Adicionar na interfaçe tbm
        System.out.printf("[%d] - %s\n", ++optionQuant, option);
    }

    public static String limparCpf(String cpf){
        return cpf.replaceAll("[.-]", "");
    }

    public static void verificarOption(){ // ---------------Colocar esse metodo numa interfaçe
        while(true) {
            try {
                System.out.print("Escolha uma opção: ");
                option = sc.nextInt();
                sc.nextLine();

                if (option == 0 && enableOptionZero) {
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
        meuLoop :
        while(true) {
            reset();
            addOption("Voltar para o menu principal");
            addOption("Alugar filme");
            addOption("Devolver filme");
            addOption("Pagar / conferir multa");
            verificarOption();

            switch (option) {
                case 1:
                    break meuLoop;
                case 2:
                    clienteAtual.alugarFilme(locadoraAtual);
                    break;
                case 3:
                    clienteAtual.devolverFilme(locadoraAtual);
                    break;
                case 4:
                    clienteAtual.conferirMulta(locadoraAtual);
                    break;
            }
        }
    }

    public static void menuVendedor(Vendedor vendedorAtual){
        meuLoop :
        while(true) {
            reset();
            addOption("Voltar para o menu principal");
            addOption("Adicionar filme");
            addOption("Remover filme");
            if (vendedorAtual.isAdmin()) {
                addOption("Adicionar vendedor");
                addOption("Remover vendedor");
                addOption("Tornar vendedor admin / remover admin");
            }
            verificarOption();

            switch (option) {
                case 1:
                    break meuLoop ;
                case 2:
                    locadoraAtual.addFilme();
                    break;
                case 3:
                    locadoraAtual.RemoverFilme();
                    break;
                case 4:
                    locadoraAtual.addVendedor();
                    break;
                case 5:
                    locadoraAtual.RemoverVendedor(vendedorAtual);
                    break;
                case 6:
                    locadoraAtual.promoverVendedor();
                    break;
            }
        }
    }

    public static int getOption() {
        return option;
    }
}

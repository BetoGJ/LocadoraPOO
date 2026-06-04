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

    public static void start(Locadora locadoraAtual){
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
                clienteAtual.alugarFilme(locadoraAtual);
                break;
            case 2:
                clienteAtual.devolverFilme(locadoraAtual);
                break;
            case 3:
                clienteAtual.conferirMulta(locadoraAtual);
                break;
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
                    locadoraAtual.addFilme();
                    break;
                case 2:
                    locadoraAtual.RemoverFilme();
                    break;
                case 3:
                    locadoraAtual.addVendedor();
                    break;
                case 4:
                    locadoraAtual.RemoverVendedor();
                    break;
                default:
            }
        }
    }

    public static int getOption() {
        return option;
    }
}

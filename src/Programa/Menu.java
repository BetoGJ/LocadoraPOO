package Programa;

import java.util.Scanner;
import Loja.Cliente;
import Loja.Locadora;
import Loja.Vendedor;

public class Menu {
    private static int optionQuant = 0;
    private static Scanner sc = new Scanner(System.in);
    private static int option;
    private static Locadora locadoraAtual = new Locadora("Locafilmes");
    public static void  clear(){
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static int getOption() {
        return option;
    }

    public static void  addOption(String option){
        System.out.printf("[%d] - %s\n", ++optionQuant, option);
    }
    public static void scanOption(){
        while(true) {
            try {
                System.out.print("Escolha uma opção: ");
                option = sc.nextInt();
                sc.nextLine();
                if (option == 0) {
                    sc.close();
                    System.exit(0);
                }
                if(option>0 && option<=optionQuant) break;
                else System.out.println("Escolha uma opção válida!");
            } catch (java.util.InputMismatchException e) {
                System.out.println("Escolha uma opção válida!");
                sc.nextLine();
            }
        }
    }

    public static void reset(){
        clear();
        optionQuant = 0;
        System.out.println("[0] Sair");
    }
    public static void start(){
        reset();

        locadoraAtual.addCliente(new Cliente("Cliente1",  "123", "senhasegura", "02/03/2000"));
        locadoraAtual.addVendedor(new Vendedor("Vendedor1", "321", "senhasegura", "04/04/2001", 1900.00f, true));
        System.out.println("Escolha um tipo de conta: ");
        addOption("Cliente");
        addOption("Vendedor");
        scanOption();
        // menu de login fica em Locadora
        locadoraAtual.login(option);
    }
    public static void menuCliente(Cliente clienteAtual){
        reset();
        addOption("Alugar filme");
        addOption("Devolver filme");
        addOption("Pagar / conferir multa");
        scanOption();

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
        scanOption();
    }
    public static boolean isNumeric(String str) {
        return str != null && str.matches("\\d+");
    }

    public static String scanCPF() {
        while (true) {
            System.out.print("Digite o CPF no formato (xxx.xxx.xxx-yy): ");
            String scannedCPF = sc.nextLine();

            if (scannedCPF.matches("\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}")) {
                return scannedCPF;
            }

            System.out.println("CPF inválido.");
        }
    }
    public static String scanDate() {
        while (true) {
            System.out.print("Digite a data no formato (dd/mm/aaaa): ");
            String scannedDate = sc.nextLine();

            if (scannedDate.matches("\\d{2}/\\d{2}/\\d{4}")) {
                return scannedDate;
            }

            System.out.println("Data inválida.");
        }
    }

}

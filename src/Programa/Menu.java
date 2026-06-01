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
                if(option>0 && option<optionQuant) break;
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

        locadoraAtual.addCliente(new Cliente("Cliente1",  "123", "senhasegura", "2000-03-02"));
        locadoraAtual.addVendedor(new Vendedor("Vendedor1", "321", "senhasegura", "2000-03-02", 1900.00f, true));
        System.out.println("Escolha um tipo de conta: ");
        addOption("Cliente");
        addOption("Vendedor");
        scanOption();
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
            addOption("Tornar vendedor admin");
            System.out.print("Lista de vendedores: ");
            for(Vendedor v: locadoraAtual.getVendedores(vendedorAtual)){
                System.out.println(v.getNome());
            }
        }
        scanOption();
    }


}

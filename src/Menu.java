import java.util.Scanner;

public class Menu {
    private static int optionQuant = 0;
    private static Scanner sc = new Scanner(System.in);
    public static void  clear(){
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
    public static void  addOption(String option){
        System.out.printf("[%d] - %s\n", ++optionQuant, option);
    }
    public static int  getOption(){
        System.out.print("Escolha uma opção: ");
        int option=sc.nextInt();
        return option;
    }
    public static void  reset(){
        clear();
        optionQuant = 0;
    }

}

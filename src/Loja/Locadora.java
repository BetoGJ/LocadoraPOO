package Loja;
import java.util.Scanner;
import java.util.Vector;
import Programa.Menu;


public class Locadora {
    private String nome;
    private Vector<Filme> filmes = new Vector<>();
    private Vector<Cliente> clientes = new Vector<>();
    private Vector<Vendedor> vendedores = new Vector<>();
    private static Scanner sc = new Scanner(System.in);

    public Locadora(String nome) {
        this.nome = nome;
    }

    public void login(int tipo){
        while(tipo == 1){
            Menu.reset();
            Menu.addOption("Cadastro");  // ---------Todos os 4 trocaram por métodos de uma interfaçe
            Menu.addOption("Login");
            Menu.verificarOption();

            if(Menu.getOption() == 1){
                System.out.print("Digite o nome: ");
                String nomeNovo = sc.nextLine();
                String cpfNovo = Menu.scanCPF();

                while(buscarConta(clientes, cpfNovo) != null){
                    System.out.println("CPF já cadastrado!");
                    cpfNovo = Menu.scanCPF();
                }

                System.out.print("Digite a senha: ");
                String senhaNova = sc.nextLine();
                String dataNova = Menu.scanData();
                Cliente clienteNovo = new Cliente(nomeNovo, cpfNovo, senhaNova, dataNova);
                clientes.add(clienteNovo);
            }
            else{
                break;
            }
        }

        String cpfLogin = Menu.scanCPF();
        Conta contaAtual=null;

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

                if (tipo == 1) {
                    Menu.menuCliente((Cliente) contaAtual);
                }
                else if (tipo == 2) {
                    Menu.menuVendedor((Vendedor) contaAtual);
                }
                break;
            } catch (NullPointerException e) {
                System.out.println("Conta não encontrada!");
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

    public void addVendedor(Vendedor vendedorNovo){
        vendedores.add(vendedorNovo);
    }

    public void addFilme(Filme filmeNovo){
        filmes.add(filmeNovo);
    }

    public void RemoveFilme(String titulo){

        for(int i = 0; i < filmes.size(); i++){
            if(filmes.get(i).getTitulo().equals(titulo)){
                filmes.remove(i);
                System.out.println("Filme removido!");
                return;
            }
        }
        System.out.println("Filme não encontrado!");
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
}

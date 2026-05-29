package Loja;

import java.util.Scanner;
import java.util.Vector;
public class Locadora {
    private String nome;
    private Vector<Filme> filmes = new Vector<>();
    private Vector<Cliente> clientes = new Vector<>();
    private Vector<Vendedor> vendedores = new Vector<>();
    private static Scanner sc = new Scanner(System.in);
    public Locadora(String nome) {
        this.nome = nome;
    }
    public void addFilme(Filme filmeNovo){
        filmes.add(filmeNovo);
    }
    public void addCliente(Cliente clienteNovo){
        clientes.add(clienteNovo);
    }
    public void addVendedor(Vendedor vendedorNovo){
        vendedores.add(vendedorNovo);
    }
    private static Conta buscarConta(Vector<? extends Conta> contas, String cpf){
        for(Conta c : contas){
            if(c.getCpf().equals(cpf)){
                return c;
            }
        }
        return null;
    }


    public void login(int tipo){
        System.out.print("Digite o CPF: ");
        String cpfLogin = sc.nextLine();
        Conta contaAtual=null;
        try {
            if (tipo == 1) contaAtual = buscarConta(clientes, cpfLogin);
            else if (tipo == 2) contaAtual = buscarConta(vendedores, cpfLogin);
            System.out.print("Digite a senha: ");
            String senhaLogin = sc.nextLine();
            contaAtual.logar(cpfLogin, senhaLogin);
        }
        catch(NullPointerException e){
            System.out.println("Conta não encontrada!");
            login(tipo);
        }
    }
}

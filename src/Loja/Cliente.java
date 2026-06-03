package Loja;

import java.time.LocalDate;
import java.util.Scanner;
import java.util.Vector;

public class Cliente extends Conta {
    private Vector<Emprestimo> emprestimos = new Vector<>();
    private Vector<Multa> multas = new Vector<>();

    public Cliente(String nome, String cpf, String senha, LocalDate dataDeNascimento) {
        super(nome, cpf, senha, dataDeNascimento);
    }

    public void addEmprestimo(Emprestimo emprestimo){
        emprestimos.add(emprestimo);
    }

    public void addMulta(Multa multa){
        multas.add(multa);
    }

    public Vector<Emprestimo> getEmprestimos() {
        return emprestimos;
    }

    public Vector<Multa> getMultas() {
        return multas;
    }

    public void pagaMulta(int id){
        for(int i = 0; i < multas.size(); i++){
            if(multas.get(i).getId() == id){
                multas.remove(i);
                System.out.println("Multa removida!");
                return;
            }
        }
        System.out.println("Multa não encontrada!");
    }

    public void alugarFilme(Locadora locadoraAtual){
        if(this.getMultas().isEmpty()) {
            String filmePesquisa;
            System.out.println("Filme disponíveis : ");

            for (Filme filme : locadoraAtual.getFilmes()) {
                System.out.println(filme.getTitulo());
            }

            System.out.println("Qual o título do filme que deverá ser alugado?");
            Scanner sc = new Scanner(System.in);
            filmePesquisa = sc.nextLine();
            for (Filme filme : locadoraAtual.getFilmes()) {
                if (filme.getTitulo().equals(filmePesquisa)) {
                    Emprestimo emprestimoPlaceholder = new Emprestimo(locadoraAtual, filmePesquisa);
                    this.addEmprestimo(emprestimoPlaceholder);

                }
            }
        }
        else{
            System.out.println("Existem multas não pagas!");
        }
    }

    public void devolverFilme(Locadora locadoraAtual){
        if(!this.getEmprestimos().isEmpty()){
            System.out.println("Qual filme você deseja devolver?");
            for(Emprestimo emprestimo : this.getEmprestimos()){
                System.out.println("ID do emprestimo : " + emprestimo.getIdEmprestimo());
                System.out.println("Filme emprestado : " + emprestimo.getNomeFilme());
                System.out.println("Data do emprestimo : " + emprestimo.getIdEmprestimo());
                System.out.println("Data limite : " + emprestimo.getDevolucao());
            }
        }
    }

    public void conferirMulta(Locadora locadoraAtual){
        if (!this.getMultas().isEmpty()){
            System.out.println("As seguintes multas devem ser pagas antes de qualquer empréstimo :");
            for(Multa multas : this.getMultas()){
                System.out.println("ID da multa : " + multas.getId());
                System.out.println("Valor da multa : " + multas.getValor());
                System.out.println("Data de inicio da multa : " + multas.getDataDeInicio());
            }
            System.out.println("Qual multa você deseja quitar?");
            int multaVerifica;
            Scanner sc = new Scanner(System.in);
            multaVerifica = sc.nextInt();
            pagaMulta(multaVerifica);
            System.out.println("meu pix : 35992348448");

        }
        else{
            System.out.println("Sem despesas!");
        }
    }

}

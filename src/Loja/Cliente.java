package Loja;

import java.time.LocalDate;
import java.util.Scanner;
import java.util.Vector;

public class Cliente extends Conta {
    private Vector<Emprestimo> emprestimos = new Vector<>();
    private Vector<Multa> multas = new Vector<>();
    Scanner sc = new Scanner(System.in);

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
            if(multas.get(i).getId() == id && multas.get(i).getDataDePagamento() == null){
                multas.get(i).setDataDePagamento(LocalDate.now());
                System.out.println("Multa removida!");
                return;
            }
        }
        System.out.println("Multa não encontrada!");
    }

    public void alugarFilme(Locadora locadoraAtual){

        boolean possuiMultaPendente = false;

        for (Multa multa : multas) {
            if (multa.getDataDePagamento() == null) {
                possuiMultaPendente = true;
                break;
            }
        }

        if (!possuiMultaPendente) {
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
                    Emprestimo emprestimoPlaceholder = new Emprestimo(locadoraAtual, filmePesquisa,this.getCpf());
                    this.addEmprestimo(emprestimoPlaceholder);
                }
            }
        }
        else {
            System.out.println("Existem multas não pagas!");
        }

    }

    public void devolverFilme(Locadora locadoraAtual){
        int verificaFilme;
        if(!this.getEmprestimos().isEmpty()){
            System.out.println("Qual filme você deseja devolver?");
            for(int i = 0; i < emprestimos.size(); i++){
                if(emprestimos.get(i).getDevolvido() == null) {
                    System.out.println("ID do emprestimo : " + emprestimos.get(i).getIdEmprestimo());
                    System.out.println("Filme emprestado : " + emprestimos.get(i).getNomeFilme());
                    System.out.println("Data do emprestimo : " + emprestimos.get(i).getData());
                    System.out.println("Data limite : " + emprestimos.get(i).getDevolucao());
                }
            }
            verificaFilme = sc.nextInt();

            for(Emprestimo emp : emprestimos){
                if(emp.getIdEmprestimo() == verificaFilme){

                    emp.setDevolvido(LocalDate.now());

                    for(Filme filme : locadoraAtual.getFilmes()){
                        if(filme.getTitulo().equals(emp.getNomeFilme())){
                            filme.setDisponivel(filme.getDisponivel() + 1);
                            break;
                        }
                    }

                    System.out.println("Filme devolvido!");
                    return;
                }
            }
        }
    }

    public void conferirMulta(Locadora locadoraAtual){
        for(Multa multa : this.getMultas()) {
            if (multa.getDataDePagamento() == null) {
                System.out.println("As seguintes multas devem ser pagas antes de qualquer empréstimo :");
                for (Multa multas : this.getMultas()) {
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

            } else {
                System.out.println("Sem despesas!");
            }
        }
    }



}

package Loja;

import Programa.Exibir;

import java.time.LocalDate;

public class Emprestimo implements Exibir {
    private static int numEmprestimo = 0;
    private int idEmprestimo;
    private LocalDate data;
    private LocalDate devolucao;
    private LocalDate devolvido;
    private String nomeFilme;
    private String cpfUsuario;

    public Emprestimo(Locadora locadora, String nome,String cpfUsuario) {

        for(Filme filme : locadora.getFilmes()){
            if(filme.getTitulo().equals(nome)){
                if(filme.getDisponivel() > 0){
                    filme.setDisponivel(filme.getDisponivel() - 1);
                    numEmprestimo++;
                    this.idEmprestimo = numEmprestimo;
                    this.data = LocalDate.now();
                    this.devolucao = this.data.plusDays(7);
                    this.devolvido = null;
                    this.nomeFilme = nome;
                    this.cpfUsuario = cpfUsuario;

                }
                else{
                    System.out.println("Filme indisponível!");
                }
                break;
            }
        }
        numEmprestimo++;
    }

    public int getIdEmprestimo() {
        return idEmprestimo;
    }

    public LocalDate getData() {
        return data;
    }

    public LocalDate getDevolucao() {
        return devolucao;
    }

    public LocalDate getDevolvido() {
        return devolvido;
    }

    public void setDevolvido(LocalDate devolvido) {
        this.devolvido = devolvido;
    }

    public String getNomeFilme() {
        return nomeFilme;
    }

    public String getCpfUsuario() {
        return cpfUsuario;
    }

    @Override
    public void mostra() {
        System.out.println("ID do emprestimo : " + this.idEmprestimo);
        System.out.println("Nome do filme : " + this.nomeFilme);
        System.out.println("Data do emprestimo : " + this.data);
        System.out.println("Limite de devolucao : " + this.devolucao);
        if(this.devolvido != null){
            System.out.println("Data de devolução : " + this.devolvido);
        }
    }
}


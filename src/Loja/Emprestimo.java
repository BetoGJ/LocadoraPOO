package Loja;

import java.time.LocalDate;

public class Emprestimo {
    private static int numEmprestimo;
    private int idEmprestimo;
    private LocalDate data;
    private LocalDate devolucao;
    private LocalDate devolvido;
    private String nomeFilme;


    public Emprestimo(Locadora locadora, String nome) {

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

                }
                else{
                    System.out.println("Filme indisponível!");
                }
                break;
            }
        }

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

    public String getNomeFilme() {
        return nomeFilme;
    }
}
package Loja;

import Programa.Exibir;

import java.time.LocalDate;
import java.util.Date;

public class Emprestimo implements Exibir {
    private int idEmprestimo;
    private LocalDate data;
    private LocalDate devolucao;
    private LocalDate devolvido;
    private String nomeFilme;
    private String cpfUsuario;
    private int idFilme;

    public Emprestimo(Locadora locadora, String nome,String cpfUsuario) {

        for(Filme filme : locadora.getFilmes()){
            if(filme.getTitulo().equals(nome)){
                if(filme.getDisponivel() > 0){
                    filme.setDisponivel(filme.getDisponivel() - 1);
                    this.idEmprestimo = -1;
                    this.data = LocalDate.now();
                    this.devolucao = this.data.plusDays(7);
                    this.devolvido = null;
                    this.nomeFilme = nome;
                    this.cpfUsuario = cpfUsuario;
                    this.idFilme = filme.getIdFilme();

                }
                else{
                    System.out.println("Filme indisponível!");
                }
                break;
            }
        }
    }

    public Emprestimo(
            int idEmprestimo,
            LocalDate data,
            LocalDate devolucao,
            LocalDate devolvido,
            String cpfUsuario,
            int idFilme
    ) {
        this.idEmprestimo = idEmprestimo;
        this.data = data;
        this.devolucao = devolucao;
        this.devolvido = devolvido;
        this.cpfUsuario = cpfUsuario;
        this.idFilme = idFilme;
    }

    public void setIdEmprestimo(int idEmprestimo) {
        this.idEmprestimo = idEmprestimo;
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


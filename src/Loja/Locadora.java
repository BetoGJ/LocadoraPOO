package Loja;

import java.util.Vector;
public class Locadora {
    private String nome;
    private Vector<Filme> filmes = new Vector<>();

    public void addFilme(Filme filme_novo){
        filmes.add(filme_novo);
    }
}

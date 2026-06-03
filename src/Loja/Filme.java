package Loja;

import Loja.Generos.FilmeAcao;
import Loja.Generos.FilmeComedia;
import Loja.Generos.FilmeSuspense;

import java.util.InputMismatchException;
import java.util.Scanner;

public abstract class Filme {
    private static int numFilme = 0;
    private int idFilme;
    private String titulo;
    private String classificacao;
    private String diretor;
    private int anoLancamento;
    private int quantidade;
    private int disponivel;

    public Filme(String titulo, String classificacao, String diretor, int anoLancamento, int quantidade, int disponivel) {
        this.idFilme = numFilme;
        numFilme++;
        this.titulo = titulo;
        this.classificacao = classificacao;
        this.diretor = diretor;
        this.anoLancamento = anoLancamento;
        this.quantidade = quantidade;
        this.disponivel = disponivel;
    }

    public int getIdFilme() {
        return idFilme;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getClassificacao() {
        return classificacao;
    }

    public String getDiretor() {
        return diretor;
    }

    public int getAnoLancamento() {
        return anoLancamento;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public int getDisponivel() {
        return disponivel;
    }

    public void setDisponivel(int disponivel) {
        this.disponivel = disponivel;
    }

    public abstract void descricaoGenero();

}
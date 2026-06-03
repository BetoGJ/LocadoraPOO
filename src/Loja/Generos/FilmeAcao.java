package Loja.Generos;

import Loja.Filme;

public class FilmeAcao extends Filme {
    private String genero;

    public FilmeAcao(String titulo, String classificacao, String diretor, int anoLancamento, int quantidade, int disponivel, String genero) {
        super(titulo, classificacao, diretor, anoLancamento, quantidade, disponivel);
        this.genero = genero;
    }

    @Override
    public void descricaoGenero() {
        System.out.println("Tem fortes laços com as narrativas clássicas de conflito e luta encontradas em diversas formas de arte e literatura. Com alguns dos primeiros exemplos remontando a épicos de guerra históricos e representações simples de assaltos a trens, os filmes de ação são populares entre o público desde os primórdios do cinema.");
    }
}
package Loja.Generos;

import Loja.Filme;

public class FilmeComedia extends Filme {
    private String genero;

    public FilmeComedia(String titulo, String classificacao, String diretor, int anoLancamento, int quantidade, int disponivel, String genero) {
        super(titulo, classificacao, diretor, anoLancamento, quantidade, disponivel);
        this.genero = genero;
    }
}

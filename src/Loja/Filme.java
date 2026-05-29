package Loja;

public class Filme {
    private String titulo;
    private String classificacao;
    private String diretor;
    private int anoLancamento;
    private int quantidade;
    private int disponivel;
    public Filme(String titulo, String classificacao, String diretor, int anoLancamento, int quantidade, int disponivel) {
        this.titulo = titulo;
        this.classificacao = classificacao;
        this.diretor = diretor;
        this.anoLancamento = anoLancamento;
        this.quantidade = quantidade;
        this.disponivel = disponivel;
    }
}

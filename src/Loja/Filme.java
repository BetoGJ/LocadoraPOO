package Loja;

public class Filme {
    private String titulo;
    private String classificacao;
    private String diretor;
    private int ano_lancamento;
    private int quantidade;
    private int disponivel;
    public Filme(String titulo, String classificacao, String diretor, int ano_lancamento, int quantidade, int disponivel) {
        this.titulo = titulo;
        this.classificacao = classificacao;
        this.diretor = diretor;
        this.ano_lancamento = ano_lancamento;
        this.quantidade = quantidade;
        this.disponivel = disponivel;
    }
}

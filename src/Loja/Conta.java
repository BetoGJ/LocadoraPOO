package Loja;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public abstract class Conta {
    private String nome;
    private String cpf;
    private int hashSenha;
    private LocalDate dataDeNascimento;
    private boolean logado = false;

    public Conta(String nome, String cpf, String senha, LocalDate dataDeNascimento) {
        this.nome = nome;
        this.cpf = cpf;
        this.hashSenha = senha.hashCode();
        this.dataDeNascimento = dataDeNascimento;
    }

    public void logar(String cpfLogin, String senhaLogin){
        if (this.cpf.equals(cpfLogin) && this.hashSenha == senhaLogin.hashCode()){
            logado = true;
            System.out.printf("\n--Logado com sucesso! Seja bem-vindo [%s]--\n", nome);
        }
        else System.out.println("Falha no login!");
    }

    public void deslogar(){
        if(logado){
            logado = false;
        }
    }

    public LocalDate getDataDeNascimento() {
        return dataDeNascimento;
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public boolean isLogado(){
        return logado;
    }
}

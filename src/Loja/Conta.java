package Loja;

import java.util.Scanner;
import java.time.LocalDate;
public abstract class Conta {
    private String nome;
    private String cpf;
    private int hash_senha;
    private LocalDate data_de_nascimento;
    private boolean logado = false;

    public Conta(String nome, String cpf, String senha, String data_de_nascimento) {
        this.nome = nome;
        this.cpf = cpf;
        this.hash_senha = senha.hashCode();
        this.data_de_nascimento = LocalDate.parse(data_de_nascimento);
    }

    public void logar(String cpfLogin, String senhaLogin){
        if (this.cpf.equals(cpfLogin) && this.hash_senha==senhaLogin.hashCode()){
            logado = true;
            System.out.printf("Logado com sucesso! Seja bem-vindo [%s]\n", nome);
        }
        else System.out.println("Falha no login!");
    }
    public void deslogar(){
        if(logado==true){
            logado = false;
        }
    }

    public LocalDate getData_de_nascimento() {
        return data_de_nascimento;
    }

    public void setData_de_nascimento(LocalDate data_de_nascimento) {
        this.data_de_nascimento = data_de_nascimento;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getCpf() {
        return cpf;
    }
    public boolean isLogado(){
        return logado;
    }
}

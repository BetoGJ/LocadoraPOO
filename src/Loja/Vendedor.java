package Loja;

import java.time.LocalDate;

public class Vendedor extends Conta{
    private Float salario;
    boolean isAdmin=false;
    public Vendedor(String nome, String cpf, String senha, String dataDeNascimento, Float salario) {
        super(nome, cpf, senha, dataDeNascimento);
        this.salario = salario;
    }
}
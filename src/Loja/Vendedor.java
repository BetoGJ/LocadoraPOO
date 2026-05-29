package Loja;

import java.time.LocalDate;

public class Vendedor extends Conta{
    private Float salario;

    public Vendedor(String nome, String cpf, String senha, String data_de_nascimento, Float salario) {
        super(nome, cpf, senha, data_de_nascimento);
        this.salario = salario;
    }
}
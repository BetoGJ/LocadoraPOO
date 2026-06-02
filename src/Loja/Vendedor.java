package Loja;

import java.time.LocalDate;

public class Vendedor extends Conta{
    private Float salario;
    private boolean adminStatus=false;
    public Vendedor(String nome, String cpf, String senha, LocalDate dataDeNascimento, Float salario, boolean adminStatus) {
        super(nome, cpf, senha, dataDeNascimento);
        this.salario = salario;
        this.adminStatus = adminStatus;
    }
    public boolean isAdmin(){
        return adminStatus;
    }
    public void setAdmin(){
        adminStatus = true;
    }
}
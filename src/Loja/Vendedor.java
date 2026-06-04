package Loja;

import java.time.LocalDate;

public class Vendedor extends Conta{
    private float salario;
    private boolean adminStatus = false;

    public Vendedor(String nome, String cpf, String senha, LocalDate dataDeNascimento, float salario, boolean adminStatus) {
        super(nome, cpf, senha, dataDeNascimento);
        this.salario = salario;
        this.adminStatus = adminStatus;
    }

    public boolean isAdmin(){
        return adminStatus;
    }

    public float getSalario() {
        return salario;
    }

    public boolean isAdminStatus() {
        return adminStatus;
    }

    public void setAdmin(){
        adminStatus = true;
    }
}
package Loja;

import java.util.Vector;

public class Cliente extends Conta {
    private Vector<Emprestimo> emprestimos = new Vector<>();

    public Cliente(String nome, String cpf, String senha, String dataDeNascimento) {
        super(nome, cpf, senha, dataDeNascimento);
    }
    public void addEmprestimo(Emprestimo emprestimo){
        emprestimos.add(emprestimo);
    }
}

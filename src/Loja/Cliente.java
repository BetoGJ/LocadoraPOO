package Loja;

import java.util.Vector;

public class Cliente extends Conta {
    private Vector<Emprestimo> emprestimos = new Vector<>();
    private Vector<Multa> multas = new Vector<>();

    public Cliente(String nome, String cpf, String senha, String dataDeNascimento) {
        super(nome, cpf, senha, dataDeNascimento);
    }

    public void addEmprestimo(Emprestimo emprestimo){
        emprestimos.add(emprestimo);
    }

    public Vector<Emprestimo> getEmprestimos() {
        return emprestimos;
    }

    public Vector<Multa> getMultas() {
        return multas;
    }

    public void pagaMulta(int id){
        for(int i = 0; i < multas.size(); i++){
            if(multas.get(i).getId() == id){
                multas.remove(i);
                System.out.println("Multa removida!");
                return;
            }
        }
        System.out.println("Multa não encontrada!");
    }
}

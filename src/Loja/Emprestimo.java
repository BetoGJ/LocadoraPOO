package Loja;

import java.time.LocalDate;
import java.util.Date;

public class Emprestimo {
    private static int idEmprestimo;
    private LocalDate data;
    private LocalDate devolucao;
    private LocalDate devolvido;

    public Emprestimo() {
        idEmprestimo++;
        this.idEmprestimo = getIdEmprestimo();
        this.data = LocalDate.now();
        this.devolucao = this.data.plusDays(7);
        this.devolvido = null;
    }

    public int getIdEmprestimo() {
        return idEmprestimo;
    }

    public LocalDate getData() {
        return data;
    }

    public LocalDate getDevolucao() {
        return devolucao;
    }

    public LocalDate getDevolvido() {
        return devolvido;
    }
}
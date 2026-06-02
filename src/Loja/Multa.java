package Loja;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Multa {
    private static int numMulta;
    private int id;
    private int idEmprestimo;
    private float valor;
    private LocalDate dataDeInicio;

    public void setValor(float valor) {
        this.valor = valor;
    }

    public int getIdEmprestimo() {
        return idEmprestimo;
    }

    public int getId() {
        return id;
    }

    public float getValor() {
        return valor;
    }

    public LocalDate getDataDeInicio() {
        return dataDeInicio;
    }

    public Multa(LocalDate dataDeInicio) {
        this.dataDeInicio = dataDeInicio;
    }

    public Multa(int idEmprestimo, float valor, LocalDate data) {
        this.id = numMulta;
        this.idEmprestimo = idEmprestimo;
        this.valor = ChronoUnit.DAYS.between(data, LocalDate.now());
        this.dataDeInicio = data;
    }
}
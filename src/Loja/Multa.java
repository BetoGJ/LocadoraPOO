package Loja;

import Programa.Exibir;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Multa implements Exibir {
    private int id;
    private int idEmprestimo;
    private float valor;
    private LocalDate dataDeInicio;
    private LocalDate dataDePagamento;
    private String cpfUsuario;

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

    public Multa(int idEmprestimo, float valor, LocalDate data,String cpfUsuario) {
        this.id = -1;
        this.idEmprestimo = idEmprestimo;
        this.valor = ChronoUnit.DAYS.between(data, LocalDate.now());
        this.dataDeInicio = data;
        this.cpfUsuario = cpfUsuario;
    }

    @Override
    public void mostra() {
        System.out.println("ID da multa : " + this.id);
        System.out.println("ID do emprestimo : " + this.idEmprestimo);
        System.out.println("Valor da multa : " + this.valor);
        System.out.println("Data de inicio : " + this.dataDeInicio);
        System.out.println("CPF do usuario : " + this.cpfUsuario);
        if(this.dataDePagamento != null){
            System.out.println("Data de pagamento : " + this.dataDePagamento);
        }
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDataDePagamento() {
        return dataDePagamento;
    }

    public void setDataDePagamento(LocalDate dataDePagamento) {
        this.dataDePagamento = dataDePagamento;
    }
}
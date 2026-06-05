package Loja;

import Programa.Menu;

import java.sql.*;
import java.time.LocalDate;
import java.util.Scanner;
import java.util.Vector;


public class Cliente extends Conta {
    private Vector<Emprestimo> emprestimos = new Vector<>();
    private Vector<Multa> multas = new Vector<>();
    Scanner sc = new Scanner(System.in);

    public Cliente(String nome, String cpf, String senha, LocalDate dataDeNascimento) {
        super(nome, cpf, senha, dataDeNascimento);
    }
    public Cliente(String nome, String cpf, int hashsenha, LocalDate dataDeNascimento) {
        super(nome, cpf, hashsenha, dataDeNascimento);
    }


    public void addEmprestimo(Emprestimo emprestimo){
        emprestimos.add(emprestimo);
    }

    public void addMulta(Multa multa){
        multas.add(multa);
    }

    public Vector<Emprestimo> getEmprestimos() {
        return emprestimos;
    }

    public Vector<Multa> getMultas() {
        return multas;
    }

    public void pagaMulta(int id, Connection bd) {
        for (int i = 0; i < multas.size(); i++) {
            if (multas.get(i).getId() == id && multas.get(i).getDataDePagamento() == null) {

                String sql = "UPDATE Multa SET DataPagamento = ? WHERE Id = ?";
                try (PreparedStatement st = bd.prepareStatement(sql)) {
                    st.setDate(1, java.sql.Date.valueOf(LocalDate.now()));
                    st.setInt(2, multas.get(i).getId());
                    st.executeUpdate();
                } catch (SQLException e) {
                    System.out.println("Erro ao pagar multa!");
                    return;
                }

                multas.get(i).setDataDePagamento(LocalDate.now());
                System.out.println("Multa paga!");
                return;
            }
        }
        System.out.println("Multa não encontrada!");
    }

    public void alugarFilme(Locadora locadoraAtual, Connection bd) {

        boolean possuiMultaPendente = false;

        for (Multa multa : multas) {
            if (multa.getDataDePagamento() == null) {
                possuiMultaPendente = true;
                break;
            }
        }

        if (!possuiMultaPendente) {
            String filmePesquisa;
            System.out.println("Filme disponíveis : ");

            for (Filme filme : locadoraAtual.getFilmes()) {
                System.out.println(filme.getTitulo());
            }

            System.out.println("Qual o título do filme que deverá ser alugado?");
            Scanner sc = new Scanner(System.in);
            filmePesquisa = sc.nextLine();

            for (Filme filme : locadoraAtual.getFilmes()) {
                if (filme.getTitulo().equals(filmePesquisa)) {

                    Emprestimo emprestimoPlaceholder = new Emprestimo(locadoraAtual, filmePesquisa, this.getCpf());

                    String sql = "INSERT INTO Emprestimo (Data, Devolucao, Cliente_CPF, Locadora_CNPJ, Filme_Id, NomeFilme) " +
                            "VALUES (?, ?, ?, ?, ?, ?)";
                    try (PreparedStatement st = bd.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                        st.setDate(1, java.sql.Date.valueOf(emprestimoPlaceholder.getData()));
                        st.setDate(2, java.sql.Date.valueOf(emprestimoPlaceholder.getDevolucao()));
                        st.setString(3, Menu.limparCpf(this.getCpf()));
                        st.setString(4, locadoraAtual.getCNPJ());
                        st.setInt(5, filme.getIdFilme());
                        st.setString(6, filmePesquisa);

                        st.executeUpdate();

                        try (ResultSet keys = st.getGeneratedKeys()) {
                            if (keys.next()) {
                                emprestimoPlaceholder.setIdEmprestimo(keys.getInt(1));
                            }
                        }
                    } catch (SQLException e) {
                        System.out.println("Erro ao inserir empréstimo!");
                        return;

                    }

                    this.addEmprestimo(emprestimoPlaceholder);
                    System.out.println("Filme alugado com sucesso!");
                    return;
                }
            }
            System.out.println("Filme não encontrado!");
        } else {
            System.out.println("Existem multas não pagas!");
        }
    }

    public void devolverFilme(Locadora locadoraAtual, Connection bd) {
        int verificaFilme;
        if (!this.getEmprestimos().isEmpty()) {
            System.out.println("Qual filme você deseja devolver?");
            for (int i = 0; i < emprestimos.size(); i++) {
                if (emprestimos.get(i).getDevolvido() == null) {
                    emprestimos.get(i).mostra();
                }
            }
            verificaFilme = sc.nextInt();

            for (Emprestimo emp : emprestimos) {
                if (emp.getIdEmprestimo() == verificaFilme) {

                    String sql = "UPDATE Emprestimo SET Devolvido = ? WHERE Id = ?";
                    try (PreparedStatement st = bd.prepareStatement(sql)) {
                        st.setDate(1, java.sql.Date.valueOf(LocalDate.now()));
                        st.setInt(2, emp.getIdEmprestimo());
                        st.executeUpdate();
                    } catch (SQLException e) {
                        System.out.println("Erro ao devolver filme!");
                        return;
                    }

                    for (Filme filme : locadoraAtual.getFilmes()) {
                        if (filme.getTitulo().equals(emp.getNomeFilme())) {
                            sql = "UPDATE Filme SET Disponivel = ? WHERE Id = ?";
                            try (PreparedStatement st = bd.prepareStatement(sql)) {
                                st.setInt(1, filme.getDisponivel() + 1); // ← valor correto
                                st.setInt(2, filme.getIdFilme());         // ← faltava
                                st.executeUpdate();
                            } catch (SQLException e) {
                                System.out.println("Erro ao atualizar disponibilidade!");
                                return;
                            }
                            filme.setDisponivel(filme.getDisponivel() + 1); // memória
                            break;
                        }
                    }

                    emp.setDevolvido(LocalDate.now()); // memória
                    System.out.println("Filme devolvido!");
                    return;
                }
            }
        }
    }

    public void conferirMulta(Locadora locadoraAtual) {
        boolean possuiMulta = false;
        for (Multa multa : multas) {
            if (multa.getDataDePagamento() == null) {
                possuiMulta = true;
                break;
            }
        }
        if (possuiMulta) {
            for (Multa multa : multas) {
                if (multa.getDataDePagamento() == null) multa.mostra();
            }
            System.out.println("Qual multa deseja quitar?");
            int id = new Scanner(System.in).nextInt();
            pagaMulta(id, locadoraAtual.bd);
        } else {
            System.out.println("Sem despesas!");
        }
    }



}

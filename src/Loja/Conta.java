package Loja;

public class Conta {
    private String cpf;
    private String senha;
    boolean logado = false;
    public void logar(String cpfLogin, String senhaLogin){
        if (this.cpf.hashCode() == cpfLogin.hashCode() && this.senha.hashCode()==senhaLogin.hashCode()){
            logado = true;
        }
    }
    public void deslogar(){
        if(logado==true){
            logado = false;
        }
    }
}

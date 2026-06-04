import Loja.Cliente;
import Loja.Vendedor;
import Loja.Locadora;
import Programa.Menu;

void main() {
    Locadora locadoraAtual = new Locadora("Locafilmes");
    locadoraAtual.addCliente(new Cliente("Cliente1", "123.123.123-12", "senhasegura", LocalDate.parse("2000-03-02")));
    locadoraAtual.addVendedor(new Vendedor("Vendedor1", "321.321.321-32", "senhasegura", LocalDate.parse("2001-04-04"), 1900.00f, true));
    Menu.start(locadoraAtual);
}

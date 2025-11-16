import java.io.Serializable;

public class Contatinho implements Serializable, Comparable<Contatinho> {
    private String nome;
    private String email;
    private String telefone;
    private String categoria;

    // Construtor
    public Contatinho(String nome, String email, String telefone, String categoria) {
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.categoria = categoria;
    }

    // Getters
    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getCategoria() {
        return categoria;
    }

    // Método para formatar o Contatinho para salvar no arquivo
    @Override
    public String toString() {
        // Formato: Nome#Email#Telefone#Categoria
        return nome + "#" + email + "#" + telefone + "#" + categoria;
    }

    // Implementação da interface Comparable para ordenação por nome
    @Override
    public int compareTo(Contatinho outroContatinho) {
        // Compara os nomes em ordem alfabética (case-insensitive)
        return this.nome.compareToIgnoreCase(outroContatinho.nome);
    }
}
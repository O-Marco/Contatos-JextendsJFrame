import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Agenda {
    private List<Contatinho> listaContatinhos;
    private static final String NOME_ARQUIVO = "agenda.txt";

    // Construtor
    public Agenda() {
        this.listaContatinhos = new ArrayList<>();
        // Chamada crucial: Carrega os dados existentes assim que a Agenda é criada.
        carregarLista(); 
    }

    /**
     * Adiciona um novo Contatinho à lista.
     * @param contatinho O Contatinho a ser adicionado.
     */
    public void addContatinho(Contatinho contatinho) {
        this.listaContatinhos.add(contatinho);
        System.out.println("✅ Contatinho de " + contatinho.getNome() + " adicionado.");
    }

    /**
     * Ordena a lista de Contatinhos pelo nome.
     */
    public void ordenarLista() {
        // Usa o Collections.sort() que utiliza o compareTo implementado em Contatinho
        Collections.sort(this.listaContatinhos);
        System.out.println("📝 Lista de Contatinhos ordenada por nome.");
    }

    /**
     * Tenta carregar os Contatinhos existentes no arquivo para a lista em memória.
     */
    public void carregarLista() {
        try (BufferedReader reader = new BufferedReader(new FileReader(NOME_ARQUIVO))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                // A linha está no formato: Nome#Email#Telefone#Categoria
                String[] dados = linha.split("#");
                if (dados.length == 4) {
                    // Adiciona o contato carregado diretamente à lista
                    // O Contatinho precisa ter o construtor: public Contatinho(String nome, String email, String telefone, String categoria)
                    this.listaContatinhos.add(new Contatinho(dados[0], dados[1], dados[2], dados[3]));
                }
            }
            if (this.listaContatinhos.size() > 0) {
                 System.out.println("✅ " + this.listaContatinhos.size() + " Contatinhos carregados do arquivo " + NOME_ARQUIVO);
            }
           
        } catch (IOException e) {
            // Se o arquivo não existir (primeira execução) ou houver erro de leitura, a lista apenas inicia vazia.
            System.out.println("ℹ️ Arquivo " + NOME_ARQUIVO + " não encontrado ou vazio. Iniciando agenda vazia.");
        }
    }

    /**
     * Salva a lista de Contatinhos (ordenada) em um arquivo de texto, SOBRESCREVENDO o anterior.
     */
    public void salvarLista() {
        // FileWriter SEM 'true' no segundo argumento sobrescreve o arquivo, 
        // mas isso é correto aqui, pois queremos salvar o estado completo e atual da lista.
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(NOME_ARQUIVO))) {
            
            // Garante que a lista esteja ordenada antes de salvar
            ordenarLista(); 
            
            for (Contatinho c : listaContatinhos) {
                // Escreve o Contatinho no formato Nome#Email#Telefone#Categoria
                writer.write(c.toString());
                writer.newLine(); // Garante que cada Contatinho fique em uma nova linha
            }
            System.out.println("\n💾 Lista de Contatinhos salva com sucesso no arquivo: " + NOME_ARQUIVO);
            
        } catch (IOException e) {
            System.err.println("❌ Erro ao salvar a lista no arquivo: " + e.getMessage());
        }
    }
    
    // Método auxiliar para exibir a lista (opcional)
    public void exibirLista() {
        if (listaContatinhos.isEmpty()) {
            System.out.println("A agenda está vazia.");
            return;
        }
        System.out.println("\n--- Lista de Contatinhos Atual ---");
        for (Contatinho c : listaContatinhos) {
            System.out.println(c.toString());
        }
        System.out.println("----------------------------------");
    }
    /**
     * Busca um Contatinho na lista através do nome.
     * @param nomeBuscado O nome do Contatinho a ser encontrado.
     * @return Um objeto Contatinho se encontrado, ou null caso contrário.
     */
    public Contatinho buscarContatinhoPorNome(String nomeBuscado) {
        // Garantimos que a lista em memória está carregada (útil caso não tenha sido carregada no construtor)
        if (listaContatinhos.isEmpty()) {
            carregarLista();
        }
        
        // Remove espaços em branco desnecessários e padroniza a busca
        String nomeNormalizado = nomeBuscado.trim();

        // Itera sobre a lista de contatinhos em memória
        for (Contatinho c : listaContatinhos) {
            // Compara o nome do Contatinho com o nome buscado, ignorando maiúsculas/minúsculas
            if (c.getNome().trim().equalsIgnoreCase(nomeNormalizado)) {
                return c; // Contatinho encontrado
            }
        }
        
        return null; // Contatinho não encontrado
    }
}
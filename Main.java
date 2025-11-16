import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        // 1. A Agenda é criada.
        // No construtor da Agenda, a lista de contatos é carregada do arquivo (agenda.txt).
        Agenda minhaAgenda = new Agenda();
        
        // 2. Inicia a interface gráfica (JFrame).
        // A partir deste ponto, todas as operações (adicionar, buscar, salvar) 
        // serão feitas pelos botões e campos do AgendaFrame.
        
        System.out.println("Iniciando interface gráfica da Agenda...");
        
        SwingUtilities.invokeLater(() -> {
            new AgendaFrame(minhaAgenda);
        });
        
        // O restante do código (Scanner, adicionarContatinhosInterativamente, 
        // exibirLista, salvarLista e scanner.close()) foi removido pois 
        // agora é manipulado pela interface gráfica.
    }
}
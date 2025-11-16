import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;

public class AgendaFrame extends JFrame {
    private Agenda minhaAgenda;
    
    // Componentes para Adicionar Contato
    // ATENÇÃO: campoAddTelefone agora é JFormattedTextField
    private JTextField campoAddNome, campoAddEmail, campoAddCategoria;
    private JFormattedTextField campoAddTelefone; // <-- MUDANÇA AQUI

    // Componentes para Buscar Contato
    private JTextField campoBuscaNome;
    private JTextArea areaResultadoBusca;

    public AgendaFrame(Agenda agenda) {
        super("Agenda de Contatinhos Completa (GUI)");
        this.minhaAgenda = agenda;
        
        // Configurações básicas da janela
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(550, 400);
        this.setLocationRelativeTo(null); // Centraliza a janela
        
        // Define uma cor de fundo para a janela
        this.getContentPane().setBackground(new Color(240, 245, 255));

        // Cria o painel de abas para organizar as funcionalidades
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setBackground(new Color(255, 255, 255)); // Fundo branco para as abas
        
        // Adiciona as abas
        tabbedPane.addTab("Adicionar Contato", criarPainelAdicionar());
        tabbedPane.addTab("Buscar Contato", criarPainelBuscar());
        
        this.add(tabbedPane);
        this.setVisible(true);
    }
    
    // -------------------------------------------------------------------
    // --- PAINEL DE ADIÇÃO ---
    // -------------------------------------------------------------------
    private JPanel criarPainelAdicionar() {
        JPanel painel = new JPanel(new GridLayout(6, 2, 10, 15)); // Aumentado o espaçamento vertical
        
        // Adiciona borda estilizada e título
        TitledBorder titulo = BorderFactory.createTitledBorder("Novo Contato");
        titulo.setTitleFont(new Font("Arial", Font.BOLD, 16));
        titulo.setTitleColor(new Color(0, 100, 150));
        painel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createEmptyBorder(10, 20, 20, 20), titulo));
        
        // 1. Nome
        painel.add(criarLabel("Nome:"));
        campoAddNome = new JTextField();
        painel.add(campoAddNome);

        // 2. Email
        painel.add(criarLabel("Email:"));
        campoAddEmail = new JTextField();
        painel.add(campoAddEmail);

        // 3. Telefone (Com Máscara)
        painel.add(criarLabel("Telefone (Ex: 83 9xxxx-xxxx):"));
        try {
            // Máscara para celular com DDD: (XX) XXXXX-XXXX
            MaskFormatter mascaraTelefone = new MaskFormatter("(##) #####-####");
            mascaraTelefone.setPlaceholderCharacter('_'); 
            campoAddTelefone = new JFormattedTextField(mascaraTelefone);
            painel.add(campoAddTelefone);
        } catch (java.text.ParseException e) {
            // Se houver erro na máscara, usa um JTextField normal (fallback)
            campoAddTelefone = new JFormattedTextField(); 
            painel.add(campoAddTelefone);
        }

        // 4. Categoria
        painel.add(criarLabel("Categoria:"));
        campoAddCategoria = new JTextField();
        painel.add(campoAddCategoria);

        // 5. Botão de Adicionar
        painel.add(new JLabel()); // Espaço vazio na grade
        JButton botaoAdicionar = new JButton("Salvar Novo Contato");
        botaoAdicionar.setBackground(new Color(50, 200, 100)); // Cor verde para salvar
        botaoAdicionar.setForeground(Color.WHITE);
        botaoAdicionar.setFont(new Font("Arial", Font.BOLD, 14));
        botaoAdicionar.addActionListener(this::executarAdicao); 
        painel.add(botaoAdicionar);
        
        return painel;
    }
    
    // Método auxiliar para criar labels estilizadas
    private JLabel criarLabel(String texto) {
        JLabel label = new JLabel(texto);
        label.setFont(new Font("Arial", Font.PLAIN, 12));
        return label;
    }

    private void executarAdicao(ActionEvent e) {
        // Agora, campoAddTelefone é um JFormattedTextField, mas getText() funciona da mesma forma.
        String nome = campoAddNome.getText().trim();
        String email = campoAddEmail.getText().trim();
        String telefone = campoAddTelefone.getText().trim();
        String categoria = campoAddCategoria.getText().trim();
        
        // Verifica se há caracteres de preenchimento da máscara (se não foi preenchido)
        if (telefone.contains("_")) {
             JOptionPane.showMessageDialog(this, "Por favor, preencha o campo Telefone corretamente.", 
                                          "Erro de Entrada", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (nome.isEmpty() || email.isEmpty() || categoria.isEmpty() || telefone.isEmpty()) {
             JOptionPane.showMessageDialog(this, "Todos os campos devem ser preenchidos.", 
                                          "Erro de Entrada", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Cria e adiciona o Contatinho à lista em memória
        Contatinho novoContatinho = new Contatinho(nome, email, telefone, categoria);
        minhaAgenda.addContatinho(novoContatinho);
        
        // Salva a lista (agora atualizada e ordenada) no arquivo
        minhaAgenda.salvarLista(); 

        JOptionPane.showMessageDialog(this, "Contatinho de " + nome + " adicionado e lista salva!", 
                                      "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                                      
        // Limpa os campos após a adição, exceto o telefone que precisa ser resetado corretamente
        campoAddNome.setText("");
        campoAddEmail.setText("");
        campoAddCategoria.setText("");
        campoAddTelefone.setValue(null); // Reseta o JFormattedTextField
    }

    // -------------------------------------------------------------------
    // --- PAINEL DE BUSCA ---
    // -------------------------------------------------------------------
    private JPanel criarPainelBuscar() {
        JPanel painelBusca = new JPanel(new BorderLayout(10, 10));
        
        // Adiciona borda estilizada e título
        TitledBorder titulo = BorderFactory.createTitledBorder("Pesquisar Contato por Nome");
        titulo.setTitleFont(new Font("Arial", Font.BOLD, 16));
        titulo.setTitleColor(new Color(0, 100, 150));
        painelBusca.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createEmptyBorder(10, 10, 10, 10), titulo));
        
        // Painel de entrada de busca
        JPanel painelEntrada = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5)); // Espaçamento
        painelEntrada.add(criarLabel("Nome:"));
        
        campoBuscaNome = new JTextField(20);
        painelEntrada.add(campoBuscaNome);
        
        JButton botaoBuscar = new JButton("🔍 Buscar Contato");
        botaoBuscar.setBackground(new Color(0, 150, 200)); // Cor azul para busca
        botaoBuscar.setForeground(Color.WHITE);
        botaoBuscar.addActionListener(this::executarBusca); 
        painelEntrada.add(botaoBuscar);
        
        painelBusca.add(painelEntrada, BorderLayout.NORTH);

        // Área de resultado
        areaResultadoBusca = new JTextArea("Detalhes do Contatinho aparecerão aqui após a busca.");
        areaResultadoBusca.setEditable(false);
        areaResultadoBusca.setFont(new Font("Monospaced", Font.PLAIN, 12)); // Fonte monoespaçada para clareza
        painelBusca.add(new JScrollPane(areaResultadoBusca), BorderLayout.CENTER);
        
        return painelBusca;
    }

    private void executarBusca(ActionEvent e) {
        String nomeBuscado = campoBuscaNome.getText().trim();
        areaResultadoBusca.setText(""); // Limpa o resultado anterior

        if (nomeBuscado.isEmpty()) {
            areaResultadoBusca.setText("❌ Por favor, digite um nome para buscar.");
            return;
        }

        // Chama o método que você implementou na Agenda.java
        Contatinho encontrado = minhaAgenda.buscarContatinhoPorNome(nomeBuscado);

        if (encontrado != null) {
            // Contatinho encontrado! Exibe os detalhes
            String detalhes = "✅ Contatinho Encontrado:\n" + 
                              "Nome:      " + encontrado.getNome() + "\n" +
                              "Email:     " + encontrado.getEmail() + "\n" +
                              "Telefone:  " + encontrado.getTelefone() + "\n" +
                              "Categoria: " + encontrado.getCategoria();
            areaResultadoBusca.setText(detalhes);
        } else {
            // Contatinho não encontrado
            areaResultadoBusca.setText("❌ Contatinho '" + nomeBuscado + "' não encontrado na agenda.");
        }
    }
}
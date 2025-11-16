# Sistema de Agenda de Contatinhos em Java

Este é um projeto simples desenvolvido em Java que simula um sistema de gerenciamento de contatos. Ele utiliza a biblioteca Swing para fornecer uma **Interface Gráfica (GUI)** completa, permitindo ao 
usuário adicionar, buscar e persistir contatos em um arquivo de texto.

---

## ✨ Funcionalidades

O sistema é dividido em lógica de negócios (classes `Agenda` e `Contatinho`) e a interface gráfica (`AgendaFrame`).

* **Persistência de Dados:** Os contatos são salvos e carregados automaticamente de um arquivo de texto (`agenda.txt`).
* **Adição de Contatos (GUI):** Possibilidade de cadastrar novos contatos (Nome, Email, Telefone, Categoria) através de um formulário interativo.
* **Formatação de Telefone:** O campo de telefone utiliza máscara (`JFormattedTextField`) para garantir que o formato `(XX) XXXXX-XXXX` seja digitado corretamente.
* **Busca de Contatos (GUI):** Permite pesquisar um contato existente pelo seu nome.
* **Ordenação:** A lista de contatos é automaticamente ordenada pelo nome antes de ser salva no arquivo.

---

## 💻 Estrutura do Projeto

O projeto é composto pelas seguintes classes principais:

| Arquivo | Descrição |
| :--- | :--- |
| `Contatinho.java` | Classe modelo que representa um contato com seus atributos (nome, email, telefone, categoria). |
| `Agenda.java` | Classe responsável pela lógica de negócios: `addContatinho()`, `ordenarLista()`, `salvarLista()` e `carregarLista()`. |
| `AgendaFrame.java` | **A Interface Gráfica (GUI):** Extende `JFrame` e contém as abas para adicionar e buscar contatos. |
| `Main.java` | Classe principal que inicia o objeto `Agenda` e a interface `AgendaFrame`. |

---

## 🛠️ Como Executar

1.  **Compilar e Executar:**
    * Abra o projeto em sua IDE (IntelliJ, Eclipse, VS Code).
    * Execute a classe `Main.java`.
2.  **Uso:**
    * Ao iniciar, a agenda tentará carregar os dados de `agenda.txt`.
    * Use a aba **"Adicionar Contato"** para inserir novos dados e salvá-los automaticamente.
    * Use a aba **"Buscar Contato"** para pesquisar um nome na sua agenda.

---

import controle.ControladorTarefa;
import entidade.Tarefa;
import visão.VisãoTarefa;
//import view.TarefaView;
import entidade.Tarefa.Status;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        VisãoTarefa visãoTarefa = new VisãoTarefa();
        ControladorTarefa controladorTarefa = new ControladorTarefa(visãoTarefa);

        while (true) {
            System.out.println("\n=== MENU ===");
            System.out.println("1. Adicionar tarefa");
            System.out.println("2. Listar tarefas");
            System.out.println("3. Remover tarefa");
            System.out.println("4. Alterar Status");
            System.out.println("5. Ordenar por prioridade");
            System.out.println("6. Filtrar por status");
            System.out.println("7. Filtrar por categoria");
            System.out.println("8. Filtrar por prazo");
            System.out.println("9. Sair");
            System.out.print("Escolha: ");
            int opc = Integer.parseInt(scanner.nextLine());
            switch (opc) {
                case 1 -> {
                    System.out.print("Título: ");
                    String titulo = scanner.nextLine();
                    System.out.print("Descrição: ");
                    String descricao = scanner.nextLine();
                    System.out.print("Prioridade (1-5): ");
                    int prioridade = Integer.parseInt(scanner.nextLine());
                    System.out.print("Categoria: ");
                    String categoria = scanner.nextLine();
                    System.out.print("Prazo (YYYY-MM-DD): ");
                    LocalDate prazo = null;
                    try {
                        prazo = LocalDate.parse(scanner.nextLine());
                        controladorTarefa.adicionarTarefa(titulo, descricao, prioridade, categoria, prazo);
                    } catch (DateTimeParseException e) {
                        System.out.println("Formato de data inválido! Use YYYY-MM-DD.");
                    }
                }
                case 2 -> controladorTarefa.listarTarefas();
                case 3 -> {
                    controladorTarefa.listarTarefas();
                    System.out.print("Número da tarefa para remover: ");
                    controladorTarefa.removerTarefa(Integer.parseInt(scanner.nextLine()) - 1);
                }
                case 4 -> {
                    controladorTarefa.listarTarefas();
                    System.out.print("Número da tarefa a ser alterada: ");
                    int idx;
                    idx = Integer.parseInt(scanner.nextLine()) - 1;
                    System.out.println("1 - TODO");
                    System.out.println("2 - DOING");
                    System.out.println("3 - DONE");
                    System.out.println("Escolha o novo status: ");
                    int opt;
                    opt = Integer.parseInt(scanner.nextLine()) - 1;
                    Status statusEscolhido = Status.values()[opt];
                    controladorTarefa.alterarStatus(idx, statusEscolhido);
                }
                case 5 -> controladorTarefa.ordenarPorPrioridade();
                case 6 -> {
                    System.out.println("1 - TODO");
                    System.out.println("2 - DOING");
                    System.out.println("3 - DONE");
                    System.out.println("Escolha o status: ");
                    int opt;
                    opt = Integer.parseInt(scanner.nextLine()) - 1;
                    Status statusEscolhido = Status.values()[opt];
                    controladorTarefa.filtrarPorStatus(statusEscolhido);
                }
                case 7 -> {
                    System.out.print("Categoria: ");
                    controladorTarefa.filtrarPorCategoria(scanner.nextLine());
                }
                case 8 -> {
                    System.out.print("Data limite (YYYY-MM-DD): ");
                    controladorTarefa.filtrarPorPrazo(LocalDate.parse(scanner.nextLine()));
                }
                case 9 -> {
                    System.out.println("Saindo...");
                    return;
                }
                default -> System.out.println("Opção inválida!");
            }
        }
    }
}

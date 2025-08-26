import controle.ControladorTarefa;
import entidade.Tarefa;
import visao.VisaoTarefa;
//import view.TarefaView;
import entidade.Tarefa.Status;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        VisaoTarefa visaoTarefa = new VisaoTarefa();
        ControladorTarefa controladorTarefa = new ControladorTarefa(visaoTarefa, true);

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
            System.out.println("9. Configurar Alarmes");
            System.out.println("10. Editar Tarefa");
            System.out.println("0. Sair");
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
                    controladorTarefa.configurarAlarmes();
                }
                case 10 -> {
                    controladorTarefa.listarTarefas();
                    System.out.print("Número da tarefa para editar: ");
                    int idx = Integer.parseInt(scanner.nextLine()) - 1;
                    if (idx < 0 || idx >= controladorTarefa.getListaTarefas().size()) {
                        System.out.println("Índice inválido!");
                        break;
                    Tarefa tarefa = controladorTarefa.getListaTarefas().get(idx);

                    System.out.print("Novo título (deixe vazio para não alterar): ");
                    String novoTitulo = scanner.nextLine();
                    if (novoTitulo.isEmpty()) novoTitulo = tarefa.getNome();

                    System.out.print("Nova descrição (deixe vazio para não alterar): ");
                    String novaDescricao = scanner.nextLine();
                    if (novaDescricao.isEmpty()) novaDescricao = tarefa.getDescricao();

                    System.out.print("Nova prioridade (1-5, deixe vazio para não alterar): ");
                    String prioridadeStr = scanner.nextLine();
                    int novaPrioridade = prioridadeStr.isEmpty() ? tarefa.getPrioridade() : Integer.parseInt(prioridadeStr);

                    System.out.print("Nova categoria (deixe vazio para não alterar): ");
                    String novaCategoria = scanner.nextLine();
                    if (novaCategoria.isEmpty()) novaCategoria = tarefa.getCategoria();

                    System.out.print("Novo prazo (YYYY-MM-DD, deixe vazio para não alterar): ");
                    String prazoStr = scanner.nextLine();
                    LocalDate novoPrazo;
                    if (prazoStr.isEmpty()) {
                        novoPrazo = tarefa.getDataDeTermino();
                    } else {
                        try {
                            novoPrazo = LocalDate.parse(prazoStr);
                        } catch (DateTimeParseException e) {
                            System.out.println("Formato de data inválido! Mantendo prazo antigo.");
                            novoPrazo = tarefa.getDataDeTermino();
                        }
                    }

                    controladorTarefa.editarTarefa(idx, novoTitulo, novaDescricao, novaPrioridade, novaCategoria, novoPrazo);
                }
                case 0 -> {
                    return;
                }
                default -> System.out.println("Opção inválida!");
            }
        }
    }
}

package visão;

import entidade.Tarefa;

import java.util.List;

public class VisãoTarefa {
    public void mostrarTarefas(List<Tarefa> tarefas) {
        if (tarefas.isEmpty()) {
            System.out.println("Nenhuma tarefa encontrada.");
            return;
        }
        int i = 1;
        for (Tarefa t : tarefas) {
            System.out.println(i++ + ". " + t);
        }
    }
}

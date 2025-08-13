package controle;

import entidade.Tarefa;
import visão.VisãoTarefa;
import entidade.Tarefa.Status;
import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class ControladorTarefa {
    private ArrayList<Tarefa> listaTarefas = new ArrayList<>();
    private final String caminho_arquivo = "tarefas.txt";
    private VisãoTarefa visãoTarefa;

    public ControladorTarefa(VisãoTarefa visãoTarefa) {
        this.carregarTarefas();
        this.visãoTarefa = visãoTarefa;
    }

    public void adicionarTarefa(String titulo, String descricao, int prioridade, String categoria, LocalDate prazo) {
        listaTarefas.add(new Tarefa(titulo, descricao, prioridade, prazo, categoria));
        salvarTarefas();
        System.out.println("Tarefa adicionada com sucesso!");
    }

    public void listarTarefas() {
        visãoTarefa.mostrarTarefas(listaTarefas);
    }

    public void removerTarefa(int index) {
        if (index >= 0 && index < listaTarefas.size()) {
            listaTarefas.remove(index);
            salvarTarefas();
            System.out.println("Tarefa removida.");
        } else {
            System.out.println("Índice inválido.");
        }
    }

    public void alterarStatus(int index, Status statusEscolhido) {
        if (index >= 0 && index < listaTarefas.size()) {
            listaTarefas.get(index).setStatus(statusEscolhido);
            salvarTarefas();
            System.out.println("Status da tarefa alterado para " + statusEscolhido);
        } else {
            System.out.println("Índice inválido.");
        }
    }

    public void ordenarPorPrioridade() {
        Collections.sort(listaTarefas);
        visãoTarefa.mostrarTarefas(listaTarefas);
    }

    public void filtrarPorStatus(Status status) {
        List<Tarefa> filtradas = new ArrayList<>();
        for (Tarefa tarefa : listaTarefas){
            if (tarefa.getStatus() == status)
                filtradas.add(tarefa);
        }
        visãoTarefa.mostrarTarefas(filtradas);
    }

    public void filtrarPorCategoria(String categoria) {
        List<Tarefa> filtradas = new ArrayList<>();
        for (Tarefa tarefa : listaTarefas){
            if (tarefa.getCategoria().equals(categoria))
                filtradas.add(tarefa);
        }
        visãoTarefa.mostrarTarefas(filtradas);
    }

    public void filtrarPorPrazo(LocalDate prazo) {
        List<Tarefa> filtradas = new ArrayList<>();
        for (Tarefa tarefa : listaTarefas){
            if (!tarefa.getDataDeTermino().isAfter(prazo))
                filtradas.add(tarefa);
        }
        visãoTarefa.mostrarTarefas(filtradas);
    }

    private void salvarTarefas() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(caminho_arquivo))) {
            for (Tarefa t : listaTarefas) {
                bw.write(t.toFileString());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar: " + e.getMessage());
        }
    }

    public void carregarTarefas(){
        try {
            File arquivo = new File(caminho_arquivo);
            Scanner leitor = new Scanner(arquivo);

            if (!arquivo.exists() || arquivo.length() == 0) {
                return;
            }

            while (leitor.hasNextLine()) {
                String linha = leitor.nextLine();
                Tarefa t = Tarefa.fromFileString(linha);
                listaTarefas.add(t);
            }
            leitor.close();
        } catch (FileNotFoundException e) {
            System.out.println("O arquivo não foi encontrado.");
        }
    }
}

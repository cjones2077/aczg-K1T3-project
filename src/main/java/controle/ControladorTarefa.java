package controle;

import entidade.Alarme;
import entidade.Tarefa;
import serviços.ServiçoEmail;
import visao.VisaoTarefa;
import entidade.Tarefa.Status;
import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ControladorTarefa {
    private ArrayList<Tarefa> listaTarefas = new ArrayList<>();
    private final String caminho_arquivo = "tarefas.txt";
    private VisaoTarefa visãoTarefa;
    private final ServiçoEmail serviçoEmail = new ServiçoEmail();
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private final List<Alarme> regrasNotificacao = new ArrayList<>();
    private String emailDestino = "";

    public ArrayList<Tarefa> getListaTarefas() {
        return listaTarefas;
    }

    public ControladorTarefa(VisaoTarefa visãoTarefa, boolean carregarArquivo) {
        if (carregarArquivo)
            this.carregarTarefas();
        this.visãoTarefa = visãoTarefa;
    }

    public void adicionarTarefa(String titulo, String descricao, int prioridade, String categoria, LocalDate prazo) {
        listaTarefas.add(new Tarefa(titulo, descricao, prioridade, prazo, categoria));
        salvarTarefas();
        System.out.println("Tarefa adicionada com sucesso!");
    }


    public void editarTarefa(int index, String novoTitulo, String novaDescricao,
    int novaPrioridade, String novaCategoria, LocalDate novoPrazo) {
        if (index >= 0 && index < listaTarefas.size()) {
            Tarefa tarefa = listaTarefas.get(index);

            tarefa.setNome(novoTitulo);
            tarefa.setDescricao(novaDescricao);
            tarefa.setPrioridade(novaPrioridade);
            tarefa.setCategoria(novaCategoria);
            tarefa.setDataDeTermino(novoPrazo);

            salvarTarefas();
            System.out.println("Tarefa editada com sucesso!");
        } else {
            System.out.println("Índice inválido.");
        }
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

    public void configurarAlarmes() {
        Scanner scanner = new Scanner(System.in);
        String resposta = "";

        do {
            if (emailDestino.isEmpty()){
                System.out.print("Digite seu email: ");
                emailDestino = scanner.nextLine();
            }
            System.out.print("Quantos dias antes do prazo deseja ser notificado? ");
            int dias = Integer.parseInt(scanner.nextLine());

            System.out.print("Deseja filtrar por status? (TODO/DOING/DONE ou vazio para ignorar): ");
            String statusStr = scanner.nextLine().trim().toUpperCase();
            Tarefa.Status statusFiltro = statusStr.isEmpty() ? null : Tarefa.Status.valueOf(statusStr);

            System.out.print("Deseja filtrar por prioridade mínima? (ou vazio para ignorar): ");
            String prioridadeStr = scanner.nextLine().trim();
            Integer prioridadeMinima = prioridadeStr.isEmpty() ? null : Integer.parseInt(prioridadeStr);

            regrasNotificacao.add(new Alarme(dias, statusFiltro, prioridadeMinima));

            System.out.print("Deseja adicionar outro gatilho? (s/n): ");
            resposta = scanner.nextLine().trim().toLowerCase();
        } while (resposta.equals("s"));

        if (!regrasNotificacao.isEmpty()) {
            iniciarScheduler();
        }
    }

    private void iniciarScheduler() {
        scheduler.scheduleAtFixedRate(() -> {
            LocalDate hoje = LocalDate.now();

            for (Tarefa t : listaTarefas) {
                for (Alarme regra : regrasNotificacao) {
                    if (regra.deveNotificar(t, hoje)) {
                        String assunto = "Lembrete de tarefa " + t.getNome();
                        String corpo = "Sua tarefa " + t.getNome() + "\nCom Status: " + t.getStatus() +
                                "\ne com Prioridade: " + t.getPrioridade() + 
                                "Expira no dia " + t.getDataDeTermino();
                        serviçoEmail.enviarEmail(emailDestino, assunto, corpo);
                    }
                }
            }
        }, 0, 1, TimeUnit.DAYS);
    }
}

package entidade;

import java.time.LocalDate;

public class Alarme {
    private final int diasAntes;
    private final Tarefa.Status statusFiltro;
    private final Integer prioridadeMinima;

    public Alarme(int diasAntes, Tarefa.Status statusFiltro, Integer prioridadeMinima) {
        this.diasAntes = diasAntes;
        this.statusFiltro = statusFiltro;
        this.prioridadeMinima = prioridadeMinima;
    }

    public boolean deveNotificar(Tarefa tarefa, LocalDate hoje) {
        boolean condPrazo = tarefa.getDataDeTermino().equals(hoje.plusDays(diasAntes));
        boolean condStatus = (statusFiltro == null) || tarefa.getStatus() == statusFiltro;
        boolean condPrioridade = (prioridadeMinima == null) || tarefa.getPrioridade() >= prioridadeMinima;

        return condPrazo && condStatus && condPrioridade;
    }

    public int getDiasAntes() {
        return diasAntes;
    }
}

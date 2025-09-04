package entidade;

import java.time.LocalDate;

public class Tarefa implements Comparable<Tarefa>{
    public enum Status { TODO, DOING, DONE}

    private String nome;
    private String descricao;
    private int prioridade;
    private Status status;
    private LocalDate dataDeTermino;
    private String categoria;


    public Tarefa(String nome, String descricao, int prioridade, LocalDate dataDeTermino, String categoria) {
        this.nome = nome;
        this.descricao = descricao;
        this.prioridade = prioridade;
        this.dataDeTermino = dataDeTermino;
        this.categoria = categoria;
        this.status = Status.TODO;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(int prioridade) {
        this.prioridade = prioridade;
    }

    public LocalDate getDataDeTermino() {
        return dataDeTermino;
    }

    public void setDataDeTermino(LocalDate dataDeTermino) {
        this.dataDeTermino = dataDeTermino;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString(){
        return (status == Status.DONE ? "[X] " : "[ ] ") +
                "Título: " + nome + "\n" +
                "Descrição: " + descricao + "\n" +
                "Prioridade: " + prioridade + "\n" +
                "Categoria: " + categoria + "\n" +
                "Prazo: " + dataDeTermino + "\n" +
                "Status: " + status;
    }

    @Override
    public int compareTo(Tarefa o){
        if (this.prioridade > o.prioridade)
                return -1;
        else if (this.prioridade < o.prioridade)
            return 1;
        else
            return 0;
    }

    public String toFileString() {
        return nome + ";" + descricao + ";" + prioridade + ";" + status + ";" + dataDeTermino + ";" + categoria;
    }

    public static Tarefa fromFileString(String linha) {
        String[] dados = linha.split(";");
        Tarefa t = new Tarefa(
                //String nome, String descricao, int prioridade, LocalDate dataDeTermino, String categoria
                dados[0],
                dados[1],
                Integer.parseInt(dados[2]),
                LocalDate.parse(dados[4]),
                dados[5]
        );
        t.setStatus(Status.valueOf(dados[3]));
        return t;
    }
}
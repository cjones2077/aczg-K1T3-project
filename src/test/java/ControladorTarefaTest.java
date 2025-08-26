import controle.ControladorTarefa;
import entidade.Tarefa;
import entidade.Tarefa.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import visao.VisaoTarefa;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ControladorTarefaTest {

    private ControladorTarefa controlador;
    private VisaoTarefa mockVisao;

    @BeforeEach
    public void setup() {
        mockVisao = mock(VisaoTarefa.class);
        controlador = new ControladorTarefa(mockVisao, false);
    }

    @Test
    public void testAdicionarTarefa() {
        System.out.println("Iniciando teste: adicionar tarefa");
        controlador.adicionarTarefa("Teste", "Descrição", 3, "Geral", LocalDate.now().plusDays(1));
        System.out.println("Criando tarefa " + controlador.getListaTarefas().get(0));
        assertEquals(1, controlador.getListaTarefas().size());
        assertEquals("Teste", controlador.getListaTarefas().get(0).getNome());
        System.out.println("Teste adicionar tarefa finalizado com sucesso\n");
    }

    @Test
    public void testRemoverTarefa() {
        System.out.println("Iniciando teste: remover tarefa");
        controlador.adicionarTarefa("T1", "D1", 1, "G1", LocalDate.now());
        System.out.println("Antes da remoção: " + controlador.getListaTarefas());
        controlador.removerTarefa(0);
        System.out.println("Depois da remoção: " + controlador.getListaTarefas());
        assertEquals(0, controlador.getListaTarefas().size());
        System.out.println("Teste remover tarefa finalizado com sucesso\n");
    }

    @Test
    public void testAlterarStatus() {
        System.out.println("Iniciando teste: alterar status");
        controlador.adicionarTarefa("Teste", "Descrição", 1, "Geral", LocalDate.now());
        System.out.println("Status antes: " + controlador.getListaTarefas().get(0).getStatus());
        controlador.alterarStatus(0, Status.DONE);
        System.out.println("Status depois: " + controlador.getListaTarefas().get(0).getStatus());
        assertEquals(Status.DONE, controlador.getListaTarefas().get(0).getStatus());
        System.out.println("Teste alterar status finalizado com sucesso\n");
    }

    @Test
    public void testEditarTarefa() {
        System.out.println("Iniciando teste: editar tarefa");
        controlador.adicionarTarefa("Teste", "Descrição", 1, "Geral", LocalDate.now());
        System.out.println("Antes da edição: " + controlador.getListaTarefas().get(0));
        controlador.editarTarefa(0, "Novo Título", "Nova Desc", 5, "Nova Cat", LocalDate.now().plusDays(5));
        Tarefa t = controlador.getListaTarefas().get(0);
        System.out.println("Depois da edição: " + t);
        assertEquals("Novo Título", t.getNome());
        assertEquals("Nova Desc", t.getDescricao());
        assertEquals(5, t.getPrioridade());
        assertEquals("Nova Cat", t.getCategoria());
        System.out.println("Teste editar tarefa finalizado com sucesso\n");
    }
}

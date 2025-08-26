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
        controlador.adicionarTarefa("Teste", "Descrição", 3, "Geral", LocalDate.now().plusDays(1));
        assertEquals(1, controlador.getListaTarefas().size());
        assertEquals("Teste", controlador.getListaTarefas().get(0).getNome());
    }

    @Test
    public void testRemoverTarefa() {
        controlador.adicionarTarefa("T1", "D1", 1, "G1", LocalDate.now());
        controlador.removerTarefa(0);
        assertEquals(0, controlador.getListaTarefas().size());
    }

    @Test
    public void testAlterarStatus() {
        controlador.adicionarTarefa("Teste", "Descrição", 1, "Geral", LocalDate.now());
        controlador.alterarStatus(0, Status.DONE);
        assertEquals(Status.DONE, controlador.getListaTarefas().get(0).getStatus());
    }

    @Test
    public void testEditarTarefa() {
        controlador.adicionarTarefa("Teste", "Descrição", 1, "Geral", LocalDate.now());
        controlador.editarTarefa(0, "Novo Título", "Nova Desc", 5, "Nova Cat", LocalDate.now().plusDays(5));

        Tarefa t = controlador.getListaTarefas().get(0);
        assertEquals("Novo Título", t.getNome());
        assertEquals("Nova Desc", t.getDescricao());
        assertEquals(5, t.getPrioridade());
        assertEquals("Nova Cat", t.getCategoria());
    }

}

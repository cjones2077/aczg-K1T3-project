const botaoAdicionar = document.getElementById("botao_adicionar");
const fecharModal = document.getElementById("fechar_modal");
const dialog = document.getElementById("modal_dialog");
const form = dialog.querySelector("form");
const lista = document.getElementById("lista_de_tarefas");
let tarefaEmEdicao = null;
const tituloModal = document.getElementById("titulo_modal");
const botaoSubmit = form.querySelector("button[type='submit']");

botaoAdicionar.onclick = function () {
  tarefaEmEdicao = null;
  form.reset();
  tituloModal.textContent = "Cadastrar Tarefa";
  botaoSubmit.textContent = "Criar";
  dialog.showModal();
};
fecharModal.onclick = function () {
  dialog.close();
};

document.addEventListener("click", (e) => {
  const targetEl = e.target;
  const li = targetEl.closest("li");

  if (targetEl.classList.contains("concluir_tarefa"))
    li.classList.toggle("done");

  if (targetEl.classList.contains("excluir_tarefa")) {
    li.remove();
  }

  if (targetEl.classList.contains("editar_tarefa")) {
    tituloModal.textContent = "Editar Tarefa";
    botaoSubmit.textContent = "Salvar";
    tarefaEmEdicao = li;
    form.nome.value = li.querySelector(".tarefa_nome").textContent;
    form.categoria.value = li.querySelector(".tarefa_categoria").textContent;
    form.descricao.value = li.querySelector(".tarefa_descricao").textContent;
    form.prioridade.value = li.querySelector(".tarefa_prioridade").textContent;
    form.data_de_termino.value = li.querySelector(".tarefa_data").textContent;
    dialog.showModal();
  }
});

document.addEventListener("change", (e) => {
  const targetEl = e.target;
  if (targetEl.classList.contains("status_tarefa")) {
    const li = targetEl.closest("li");

    if (targetEl.value === "done") {
      li.classList.add("done");
    } else {
      li.classList.remove("done");
    }
  }
});

form.onsubmit = function (e) {
  e.preventDefault();

  let nome = form.nome.value;
  let descricao = form.descricao.value;
  let prioridade = form.prioridade.value;
  let data_de_termino = form.data_de_termino.value;
  let categoria = form.categoria.value;
  let tarefa_li = "";

  let tarefa = {
    nome: nome,
    descricao: descricao,
    prioridade: prioridade,
    data_de_termino: data_de_termino,
    categoria: categoria,
  };
  if (tarefaEmEdicao) {
    tarefaEmEdicao.querySelector(".tarefa_nome").textContent = nome;
    tarefaEmEdicao.querySelector(".tarefa_categoria").textContent = categoria;
    tarefaEmEdicao.querySelector(".tarefa_descricao").textContent = descricao;
    tarefaEmEdicao.querySelector(".tarefa_prioridade").textContent = prioridade;
    tarefaEmEdicao.querySelector(".tarefa_data").textContent = data_de_termino;
  } else {
    tarefa_li = `<li>

            <div class="texto_tarefa">
              <div class="linha1">
                <span class="tarefa_nome">${tarefa.nome}</span>
                &nbsp;-&nbsp;
                <span class="tarefa_categoria">${tarefa.categoria}</span>
              </div>
              <div class="linha2">
                <span class="tarefa_descricao">${tarefa.descricao}</span>
              </div>
              <div class="linha3">
                Data de Término:&nbsp;
                <span class="tarefa_data"
                  >${tarefa.data_de_termino}</span
                >
                &nbsp;-&nbsp;Prioridade:&nbsp;
                <span class="tarefa_prioridade"
                  >${tarefa.prioridade}</span
                >
              </div>
            </div>
            <div class="botoes">
            <div class="linha_status">
                Status:
                <select class="botoes_acoes status_tarefa">
                  <option value="todo">Todo</option>
                  <option value="doing">Doing</option>
                  <option value="done">Done</option>
                </select>
              </div>
              <button class="botoes_acoes editar_tarefa">
                <i class="fa-solid fa-pen"></i>
              </button>
              <button class="botoes_acoes excluir_tarefa">
                <i class="fa-solid fa-xmark"></i>
              </button>
            </div>
          </li>`;
    lista.innerHTML += tarefa_li;
  }
  form.reset();
  dialog.close();
  tarefaEmEdicao = null;
};

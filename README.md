# Sistema de Alarmes e Notificações – ToDo

## Descrição
Esta feature adiciona notificações por e-mail para tarefas do ToDo.
O usuário pode configurar **gatilhos personalizados** com base em:

- Dias antes do prazo
- Status da tarefa (`TODO`, `DOING`, `DONE`)
- Prioridade mínima

---

## Funcionalidades

- **Ativação opcional:** usuário escolhe se quer receber notificações.
- **Gatilhos configuráveis:** múltiplas regras de notificação. personalizadas.
- **Envio via SendGrid**
- **Execução automática**

---

## Configuração

1. Coloque sua API Key do SendGrid em `SendGridConfig` ou em variável de ambiente.  
2. Configure o e-mail do remetente em `SendGridConfig.remetente`.  
3. No menu, o usuário pode ativar/desativar notificações e criar regras personalizadas.



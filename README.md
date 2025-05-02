# 🛡️ Sistema Data Guard

**Tecnologias:** React, Java, Spring Boot, MySQL
**Objetivo:** Automatizar a cobrança de aluguéis, alertar usuários sobre vencimentos, aplicar penalidades em caso de atraso e manter o controle financeiro do locador.

---

## 📂 1. Dados Armazenados

* **Nome do locatário**
* **CPF**
* **Número da residência**
* **Data de cobrança**
* **Valor do aluguel**
* **Multa e juros aplicáveis**
* **Status de pagamento**
* **Histórico de pagamentos** – Lista de pagamentos passados
* **Imóvel associado** – Dados do imóvel alugado
* **Documentos anexados** – Contrato, RG/CPF, comprovantes, etc.

---

## 🛠️ 2. Funcionamento Básico do Sistema

1. O locatário é cadastrado no sistema com os dados principais.
2. Diariamente, o sistema verifica se algum locatário tem vencimento no dia atual.
3. Quando a data de vencimento chega:

    * Um e-mail é enviado via Gmail (ou serviço externo como SendGrid).
    * Se o pagamento não for registrado:

        * Multa e juros são aplicados automaticamente ao valor do aluguel.
        * Notificações recorrentes são enviadas até a regularização.
4. Após o pagamento:

    * O valor é normalizado.
    * A data de vencimento é atualizada (+1 mês).
    * Um registro é adicionado ao histórico.

---

## 🚀 3. Funcionalidades Avançadas

### 📊 3.1. Controle e Gestão

* Histórico de pagamentos: datas, valores, multas, juros.
* Cadastro de imóveis: múltiplos imóveis por locador.
* **Multilocatários**: divisão do aluguel entre pagadores.
* Atualização anual do aluguel: com base no IPCA ou outro índice.

### 🔔 3.2. Notificações Inteligentes

* Lembretes antecipados (ex: 3 dias antes do vencimento).
* Notificações de atraso (recorrentes até pagamento).
* Mensagens personalizadas por tipo de alerta.

### 💼 3.3. Documentação e Legalidade

* Upload de documentos: RG, CPF, contrato, comprovantes.

### 🧾 3.4. Relatórios e Financeiro

* **Relatórios em PDF**:

    * Extrato mensal e anual
    * Inadimplência
    * Fluxo de pagamentos

* **Dashboard administrativo**:

    * Percentual de inadimplência
    * Valor total recebido
    * Imóveis com maior retorno

### 🧠 3.5. Inteligência e Previsões (Futuro)

* Análise de risco de inadimplência
* Sugestões automáticas de reajuste com base no mercado

---

## 🔐 4. Segurança

* Autenticação JWT ou OAuth2
* Criptografia de CPF e documentos
* Controle de acesso baseado em papéis (Locador, Locatário)
* **2FA** para contas administrativas

---

## 🎨 5. Usabilidade

* Interface responsiva (Web e Mobile)
* Calendário com status de vencimento
* Upload simplificado de arquivos
* Sistema multilíngue (PT, EN, ES)

---

## 🔗 6. Tecnologias e Integrações Sugeridas

* **Spring Boot** com:

    * Spring Data JPA
    * Spring Security
* **Banco de dados**: MySQL
* **Migrations**: Flyway
* **Documentação da API**: Swagger / OpenAPI
* **E-mails**: JavaMailSender / SendGrid
* **Containerização**: Docker
* **Automação CI/CD**: GitHub Actions

---

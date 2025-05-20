# 💸 baliSTech Fintech

Bem-vindo ao projeto **baliSTech Fintech** – uma aplicação web simples e moderna para gestão financeira pessoal! Desenvolvido com Java (Servlets + JSP + JDBC), Oracle Database e Bootstrap, este projeto foi pensado para entregar uma experiência clara, funcional e com uma interface bonita 💜.

---

## ⚙️ Funcionalidades

✅ Cadastro e login de usuários (com verificação de senha forte)  
✅ Edição de nome, email e senha via pop-ups modais  
✅ Cadastro de metas financeiras com valor, descrição e data  
✅ Listagem dinâmica das metas cadastradas  
✅ Edição e exclusão individual de cada meta financeira  
✅ Exibição do saldo atual com possibilidade de adicionar valor  
✅ Gráfico de pizza mostrando a distribuição das metas 🎯  
✅ Responsivo `#232946` + `#eebbc3` + `#b8c1ec`  
✅ Logout com destruição da sessão

---

## 🚀 Como rodar o projeto

### 🛠️ Requisitos
- IntelliJ IDEA (Community ou Ultimate)
- Apache Tomcat (versão 10+ ou compatível com Jakarta EE)
- Oracle Database (ou Oracle XE)
- JDK 17+
- Driver JDBC do Oracle (`ojdbc11.jar` ou similar)
- Maven (opcional, caso queira gerenciar dependências)

### 📦 Estrutura do Projeto
```
src/
├── controller/
│   └── UsuarioServletController.java
├── dao/
│   └── usuario/...
│   └── metaFinanceira/...
├── model/
│   └── usuario/...
│   └── metaFinanceira/...
├── resources/
└── webapp/
    ├── login.jsp
    ├── main.jsp
    ├── cadastro-usuario.jsp
    ├── user-page.jsp
    └── WEB-INF/web.xml
```

### 💻 IntelliJ IDEA Community

1. Abra o IntelliJ e selecione **"New Project from Existing Sources"**.
2. Selecione a pasta do projeto.
3. Marque como projeto Java (não Maven).
4. Configure o SDK Java (17+).
5. Configure um servidor Tomcat local:
   - Vá em `File > Settings > Application Servers`
   - Adicione o Tomcat apontando para a pasta de instalação
6. Clique com o botão direito no projeto > `Add Framework Support` > `Web`.
7. Crie uma configuração de execução:
   - Vá em `Run > Edit Configurations`
   - Novo > Tomcat Server > Local
   - Escolha o `artifact` gerado (geralmente `war exploded`)
   - Clique em Run ▶️

### 💻 IntelliJ IDEA Ultimate

1. Vá em `File > New > Project from Existing Sources`.
2. Importe o projeto como **Java Web / Maven (se configurado)**.
3. O IntelliJ Ultimate detecta automaticamente o projeto Web.
4. Vá em `Run > Edit Configurations`, adicione uma configuração do Tomcat e execute normalmente.

---

## 📌 Observações

- As conexões com o banco devem ser configuradas na `DaoFactory`, apontando para o Oracle correto.
- Certifique-se de que a base esteja criada e o driver do Oracle esteja importado.
- As tabelas utilizadas são: `USUARIO`, `META_FINANCEIRA`, `SALDO`.

---

## 📅 Última atualização
20/05/2025

---

Feito com 💜

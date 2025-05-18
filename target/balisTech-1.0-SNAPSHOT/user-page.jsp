<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="br.com.balistech.balistech.model.usuario.Usuario" %>
<%
    Usuario usuario = (Usuario) session.getAttribute("usuario");
    if (usuario == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <title>Minha Conta - baliSTech</title>
    <link href="resources/css/bootstrap.min.css" rel="stylesheet">
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

    <!-- Bootstrap Bundle JS (com Popper incluso) - no final do body -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <style>
        body, html {
            height: 100%;
            margin: 0;
            background-color: #232946;
            color: #ffffff;
        }

        .container-main {
            display: flex;
            height: 100vh;
        }

        .illustration-side {
            flex: 1;
            background-color: #121629;
            display: flex;
            justify-content: center;
            align-items: center;
            padding: 2rem;
        }

        .illustration-side h3 {
            color: #b8c1ec;
        }

        .form-side {
            flex: 1;
            display: flex;
            align-items: center;
            justify-content: center;
            padding: 2rem;
            background-color: #232946;
        }

        .form-box {
            width: 100%;
            max-width: 400px;
            text-align: center;
            background-color: #ffffff;
            padding: 30px;
            border-radius: 12px;
            color: #232946;
            box-shadow: 0 4px 12px rgba(0,0,0,0.3);
        }

        .form-box button {
            width: 100%;
            margin-bottom: 15px;
            background-color: #eebbc3;
            color: #232946;
            border: none;
        }

        .form-box button:hover {
            background-color: #dba4ae;
        }

        .alert {
            margin-bottom: 20px;
        }

        a {
            color: #b8c1ec;
        }

        a:hover {
            color: #ffffff;
        }

        .modal-content {
            background-color: #ffffff;
            color: #232946;
        }

        .modal-header {
            background-color: #b8c1ec;
            color: #232946;
        }

        .btn-primary {
            background-color: #eebbc3;
            border: none;
            color: #232946;
        }

        .btn-primary:hover {
            background-color: #dba4ae;
        }

        .btn-secondary {
            background-color: #b8c1ec;
            border: none;
            color: #232946;
        }

        .btn-secondary:hover {
            background-color: #a4aedb;
        }
    </style>
</head>
<body>

<div class="container-main">
    <!-- Ilustração -->
    <div class="illustration-side">
        <img src="images/password.svg" alt="Financeiro" width="600" height="300" class="img-margin">
    </div>

    <!-- Formulário -->
    <div class="form-side">
        <div class="form-box">
            <h3 class="mb-4">Minha Conta</h3>

            <c:if test="${not empty mensagem}">
                <div class="alert alert-success">${mensagem}</div>
            </c:if>
            <c:if test="${not empty erro}">
                <div class="alert alert-danger">${erro}</div>
            </c:if>

            <button class="btn btn-outline-primary" data-bs-toggle="modal" data-bs-target="#modalNome">Alterar Nome</button>
            <button class="btn btn-outline-primary" data-bs-toggle="modal" data-bs-target="#modalEmail">Alterar Email</button>
            <button class="btn btn-outline-primary" data-bs-toggle="modal" data-bs-target="#modalSenha">Alterar Senha</button>

            <div class="mt-4">
                <a href="main.jsp">Voltar</a>
            </div>
        </div>
    </div>
</div>

<!-- MODAL: Nome -->
<div class="modal fade" id="modalNome" tabindex="-1">
    <div class="modal-dialog">
        <div class="modal-content">
            <form action="usuario?acao=atualizar" method="post">
                <div class="modal-header">
                    <h5 class="modal-title">Alterar Nome</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                </div>
                <div class="modal-body">
                    <input type="text" class="form-control" name="nome" placeholder="Novo nome" required>
                    <input type="hidden" name="atualizarCampo" value="nome">
                </div>
                <div class="modal-footer">
                    <button type="submit" class="btn btn-primary">Salvar</button>
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
                </div>
            </form>
        </div>
    </div>
</div>

<!-- MODAL: Email -->
<div class="modal fade" id="modalEmail" tabindex="-1">
    <div class="modal-dialog">
        <div class="modal-content">
            <form action="usuario?acao=atualizar" method="post">
                <div class="modal-header">
                    <h5 class="modal-title">Alterar Email</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                </div>
                <div class="modal-body">
                    <input type="email" class="form-control" name="email" placeholder="Novo email" required>
                    <input type="hidden" name="atualizarCampo" value="email">
                </div>
                <div class="modal-footer">
                    <button type="submit" class="btn btn-primary">Salvar</button>
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
                </div>
            </form>
        </div>
    </div>
</div>

<!-- MODAL: Senha -->
<div class="modal fade" id="modalSenha" tabindex="-1">
    <div class="modal-dialog">
        <div class="modal-content">
            <form action="usuario?acao=atualizar" method="post">
                <div class="modal-header">
                    <h5 class="modal-title">Alterar Senha</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                </div>
                <div class="modal-body">
                    <input type="password" class="form-control mb-3" name="senha" placeholder="Nova senha" required>
                    <input type="password" class="form-control" name="confirmarSenha" placeholder="Confirmar nova senha" required>
                    <input type="hidden" name="atualizarCampo" value="senha">
                </div>
                <div class="modal-footer">
                    <button type="submit" class="btn btn-primary">Salvar</button>
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
                </div>
            </form>
        </div>
    </div>
</div>

<script>
    const formSenha = document.querySelector('form[action="usuario?acao=atualizar"]');
    if (formSenha) {
        formSenha.addEventListener("submit", function(event) {
            const senhaInput = formSenha.querySelector('input[name="senha"]');
            const confirmarInput = formSenha.querySelector('input[name="confirmarSenha"]');

            const senha = senhaInput.value;
            const confirmar = confirmarInput.value;
            const regex = /^(?=.*[A-Z])(?=.*\d)[A-Za-z\d]{8,}$/;

            if (!regex.test(senha)) {
                alert("A senha deve ter pelo menos 8 caracteres, incluindo uma letra maiúscula e um número.");
                event.preventDefault();
                return;
            }

            if (senha !== confirmar) {
                alert("As senhas não coincidem.");
                event.preventDefault();
            }
        });
    }
</script>

</body>
</html>

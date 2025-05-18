<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Cadastro - baliSTech Fintech</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet">
    <style>
        body, html {
            height: 100%;
            background-color: #232946;
            color: #ffffff;
            font-family: 'Segoe UI', sans-serif;
        }

        .register-container {
            height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
        }

        .register-box {
            background-color: #b8c1ec;
            padding: 40px;
            border-radius: 8px;
            box-shadow: 0 4px 15px rgba(0,0,0,0.3);
            max-width: 400px;
            width: 100%;
            color: #232946;
        }

        .text-muted {
            color: #121629 !important;
        }

        .form-control {
            background-color: #ffffff;
            color: #232946;
            border: 1px solid #ddd;
        }

        .form-control:focus {
            border-color: #eebbc3;
            box-shadow: 0 0 0 0.2rem rgba(238, 187, 195, 0.25);
        }

        .form-text {
            color: #232946;
        }

        .btn-primary {
            background-color: #eebbc3;
            border-color: #eebbc3;
            color: #232946;
        }

        .btn-primary:hover {
            background-color: #f5cbd0;
            border-color: #f5cbd0;
        }

        a {
            color: #232946;
            font-weight: bold;
        }

        a:hover {
            text-decoration: underline;
        }

        .alert-success {
            background-color: #d4edda;
            color: #155724;
            border: none;
        }

        .alert-danger {
            background-color: #f8d7da;
            color: #721c24;
            border: none;
        }

        .btn-close {
            filter: invert(1);
        }
    </style>

</head>
<body>
<div class="register-container">
    <div class="register-box">
        <h2 class="text-center">Register</h2>
        <p class="text-center text-muted">Preencha o formulário para registrar-se.</p>

        <c:if test="${not empty mensagem}">
            <div class="alert alert-success alert-dismissible fade show" role="alert">
                <i class="bi bi-check-circle-fill"></i> ${mensagem}
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>
        </c:if>
        <c:if test="${not empty erro}">
            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                <i class="bi bi-exclamation-triangle-fill"></i> ${erro}
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>
        </c:if>

        <form action="usuario?acao=cadastrar" method="post" onsubmit="return validarSenha()">
            <div class="mb-3">
                <label class="form-label">Nome</label>
                <input type="text" class="form-control" name="nome" required>
            </div>

            <div class="mb-3">
                <label class="form-label">Email</label>
                <input type="email" class="form-control" name="email" required>
            </div>

            <div class="mb-3">
                <label class="form-label">Senha</label>
                <input type="password" class="form-control" name="senha" required>
                <div class="form-text">A senha deve ter pelo menos 8 caracteres, incluindo uma letra maiúscula e um número.</div>
            </div>

            <button type="submit" class="btn btn-primary w-100">Cadastrar</button>
        </form>

        <div class="text-center mt-3">
            Já tem conta? <a href="login.jsp">Faça login</a>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script>
    function validarSenha() {
        const senha = document.querySelector('input[name="senha"]').value;
        const regex = /^(?=.*[A-Z])(?=.*\d)[A-Za-z\d]{8,}$/;

        if (!regex.test(senha)) {
            alert("A senha deve conter pelo menos 8 caracteres, incluindo uma letra maiúscula e um número.");
            return false;
        }

        return true;
    }
</script>
</body>
</html>

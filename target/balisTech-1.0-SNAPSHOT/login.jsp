<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    String emailSalvo = "";
    boolean lembrarMarcado = false;
    if (request.getCookies() != null) {
        for (Cookie cookie : request.getCookies()) {
            if ("email".equals(cookie.getName())) {
                emailSalvo = cookie.getValue();
                lembrarMarcado = true;
                break;
            }
        }
    }
%>
<!DOCTYPE html>
<html>
<head>
    <title>Login - baliSTech Fintech</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet">
    <style>
        body, html {
            height: 100%;
            margin: 0;
            background-color: #232946;
            color: #ffffff;
        }

        .login-container {
            display: flex;
            height: 100vh;
        }

        .image-side {
            flex: 1;
            background-color: #121629;
            display: flex;
            justify-content: center;
            align-items: center;
        }

        .form-side {
            flex: 1;
            display: flex;
            align-items: center;
            justify-content: center;
            background-color: #232946;
        }

        .form-box {
            width: 100%;
            max-width: 400px;
            background-color: #fff;
            padding: 30px;
            border-radius: 10px;
            color: #232946;
            box-shadow: 0 4px 12px rgba(0,0,0,0.2);
        }

        .btn-primary {
            background-color: #eebbc3;
            border-color: #eebbc3;
            color: #232946;
        }

        .btn-primary:hover {
            background-color: #dba4ae;
            border-color: #dba4ae;
        }

        a {
            color: #b8c1ec;
        }

        a:hover {
            color: #ffffff;
        }
    </style>
</head>
<body>
<div class="login-container">
    <div class="image-side">
        <img src="images/finance.svg" alt="Financeiro" width="600" height="300">
    </div>

    <div class="form-side">
        <div class="form-box">
            <h2 class="mb-4 text-center">Seja bem vindo :)</h2>
            <p class="text-muted text-center mb-4">Acesse sua conta com email e senha</p>

            <c:if test="${not empty erro}">
                <div class="alert alert-danger alert-dismissible fade show" role="alert">
                    <i class="bi bi-exclamation-triangle-fill"></i> ${erro}
                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                </div>
            </c:if>

            <form action="usuario?acao=login" method="post">
                <div class="mb-3">
                    <label class="form-label">Email</label>
                    <input type="email" class="form-control" name="email" required value="<%= emailSalvo %>">
                </div>
                <div class="mb-3">
                    <label class="form-label">Senha</label>
                    <input type="password" class="form-control" name="senha" required>
                </div>
                <div class="form-check mb-3">
                    <input type="checkbox" class="form-check-input" id="lembrar" name="lembrar" <%= lembrarMarcado ? "checked" : "" %>>
                    <label class="form-check-label" for="lembrar">Lembrar-me</label>
                </div>
                <button type="submit" class="btn btn-primary w-100">Login</button>
            </form>

            <div class="text-center mt-3">
                <a href="cadastro-usuario.jsp">Não possui uma conta? Cadastre-se aqui!</a>
            </div>
        </div>
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
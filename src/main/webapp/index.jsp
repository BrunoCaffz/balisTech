<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
    <title>Bem-vindo à Fintech</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet">

    <style>
        body {
            background-color: #232946;
            color: #ffffff;
            font-family: 'Segoe UI', sans-serif;
            height: 100vh;
            margin: 0;
        }

        .container {
            height: 100vh;
        }

        .card {
            background-color: #b8c1ec;
            color: #232946;
            border-radius: 12px;
            box-shadow: 0 6px 18px rgba(0, 0, 0, 0.3);
        }

        .btn-danger {
            background-color: #eebbc3;
            border-color: #eebbc3;
            color: #232946;
        }

        .btn-danger:hover {
            background-color: #f5cbd0;
            border-color: #f5cbd0;
            color: #232946;
        }

        .text-center strong {
            color: #121629;
        }
    </style>
</head>
<body>
<div class="container d-flex flex-column justify-content-center align-items-center">
    <div class="card shadow p-4" style="width: 100%; max-width: 500px;">
        <h2 class="mb-3 text-center">Olá, <%= usuario.getNome() %>!</h2>
        <p class="text-center">Você está logado como: <strong><%= usuario.getTipoUsuario() %></strong></p>
        <div class="d-grid mt-4">
            <a href="usuario?acao=logout" class="btn btn-danger">
                <i class="bi bi-box-arrow-right"></i> Sair
            </a>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>

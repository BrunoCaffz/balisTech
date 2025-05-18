<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="br.com.balistech.balistech.dao.saldo.SaldoDao" %>
<%@ page import="br.com.balistech.balistech.dao.DaoFactory" %>
<%@ page import="java.text.NumberFormat" %>
<%@ page import="br.com.balistech.balistech.model.usuario.Usuario" %>
<%@ page import="br.com.balistech.balistech.model.saldo.Saldo" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page pageEncoding="UTF-8" %>

<%
  request.setCharacterEncoding("UTF-8");
  Usuario usuario = (Usuario) session.getAttribute("usuario");
  if (usuario == null) {
    response.sendRedirect("login.jsp");
    return;
  }
  SaldoDao saldoDao = DaoFactory.getSaldoDao();
  Saldo saldo = saldoDao.buscarPorUsuario(usuario.getId());
  double valorSaldo = (saldo != null) ? saldo.getValor() : 0.0;
  NumberFormat formatador = NumberFormat.getCurrencyInstance(new java.util.Locale("pt", "BR"));
  String saldoFormatado = formatador.format(valorSaldo);
%>

<!DOCTYPE html>
<html>
<head>
  <title>Dashboard - baliSTech</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
  <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet">
  <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>

  <style>
    body {
      background-color: #232946;
      color: #ffffff;
      font-family: 'Segoe UI', sans-serif;
    }

    .header {
      background-color: #121629;
      color: #ffffff;
      padding: 15px 20px;
      display: flex;
      justify-content: space-between;
      align-items: center;
    }

    .dashboard {
      padding: 30px;
    }

    .card-saldo, .meta-card {
      background-color: #b8c1ec;
      color: #232946;
      padding: 25px;
      border-radius: 10px;
      box-shadow: 0 4px 10px rgba(0,0,0,0.2);
      margin-bottom: 20px;
    }

    .meta-card {
      width: 80%;
    }

    .popup-overlay {
      position: fixed;
      top: 0;
      left: 0;
      width: 100%;
      height: 100%;
      background-color: rgba(0,0,0,0.5);
      display: none;
      justify-content: center;
      align-items: center;
    }

    .popup {
      background-color: #ffffff;
      color: #232946;
      padding: 30px;
      border-radius: 10px;
      width: 400px;
      box-shadow: 0 4px 15px rgba(0,0,0,0.3);
    }

    .btn-custom-purple {
      background-color: #eebbc3;
      color: #232946;
      border: none;
    }

    .btn-custom-purple:hover {
      background-color: #f5cbd0;
    }

    .meta-actions {
      margin-top: 10px;
      display: flex;
      justify-content: flex-end;
      gap: 10px;
    }

    .meta-actions button {
      background-color: #eebbc3;
      border: none;
      border-radius: 50%;
      padding: 8px 12px;
      color: #232946;
      cursor: pointer;
      transition: background-color 0.3s;
    }

    .meta-actions button:hover {
      background-color: #f5cbd0;
    }

    .layout-flex {
      display: flex;
      flex-wrap: wrap;
      gap: 40px;
      align-items: flex-start;
    }

    .meta-container {
      flex: 2;
      display: flex;
      flex-direction: column;
      gap: 20px;
    }

    .grafico-container {
      flex: 1;
      max-width: 400px;
      background: #ffffff;
      border-radius: 10px;
      padding: 20px;
      box-shadow: 0 4px 10px rgba(0,0,0,0.1);
      margin-right: 90px;
    }
  </style>

</head>
<body>

<div class="header">
  <h4>$ Bem-vindo $, <%= usuario.getNome() %></h4>
  <div class="actions">
    <button class="btn btn-light" onclick="abrirMenu()"><i class="bi bi-gear"></i></button>
    <div id="menuOpcoes" style="display:none; position:absolute; right:0; background:white; padding:10px;">
      <a href="usuario?acao=logout" class="d-block mb-2"><i class="bi bi-box-arrow-right"></i> Sair</a>
      <a href="user-page.jsp" class="d-block"><i class="bi bi-person-circle"></i> Minha Conta</a>
    </div>
  </div>
</div>

<div class="dashboard">
  <div class="card-saldo" onclick="abrirPopup('popupSaldo')">
    <h5><i class="bi bi-wallet2"></i> Saldo</h5>
    <h2><%= saldoFormatado %></h2>
    <small>Toque para adicionar</small>
  </div>

  <button class="btn btn-custom-purple mb-3" onclick="abrirPopup('popupMeta')">
    <i class="bi bi-plus-circle"></i> Adicionar meta financeira
  </button>

  <div class="layout-flex">
    <div class="meta-container">
      <c:forEach var="meta" items="${metas}">
        <div class="meta-card">
          <h5><i class="bi bi-flag"></i> Meta financeira</h5>
          <p><strong>R$ ${meta.valorMeta}</strong></p>
          <p>Até ${meta.dataLimite}</p>
          <p>Descrição: ${meta.descricao}</p>
          <div class="meta-actions">
            <form action="meta" method="post">
              <input type="hidden" name="acao" value="remover" />
              <input type="hidden" name="id" value="${meta.id}" />
              <button type="submit" title="Excluir"><i class="bi bi-trash"></i></button>
            </form>
            <button type="button" onclick="abrirPopupEditar(this)"
                    data-id="${meta.id}"
                    data-descricao="${meta.descricao}"
                    data-valor="${meta.valorMeta}"
                    data-data="${meta.dataLimite}">
              <i class="bi bi-pencil"></i>
            </button>
          </div>
        </div>
      </c:forEach>
    </div>

    <div class="grafico-container">
      <canvas id="graficoMetas"></canvas>
    </div>
  </div>
</div>
<!-- Popups -->
<div class="popup-overlay" id="popupSaldo">
  <div class="popup">
    <h5 class="mb-4">Adicionar Valor</h5>
    <form action="saldo" method="post">
      <div class="mb-4">
        <label class="form-label">Valor</label>
        <input type="number" step="0.01" name="valor" class="form-control" required>
      </div>
      <div class="d-flex justify-content-between">
        <button type="submit" class="btn btn-primary">Salvar</button>
        <button type="button" class="btn btn-secondary" onclick="fecharPopup('popupSaldo')">Cancelar</button>
      </div>
    </form>
  </div>
</div>

<div class="popup-overlay" id="popupMeta">
  <div class="popup">
    <h5 class="mb-4">Nova Meta</h5>
    <form action="meta" method="post">
      <input type="hidden" name="acao" value="cadastrar" />
      <div class="mb-3">
        <label class="form-label">Descrição</label>
        <input type="text" name="descricao" class="form-control" required>
      </div>
      <div class="mb-3">
        <label class="form-label">Valor</label>
        <input type="number" step="0.01" name="valor" class="form-control" required>
      </div>
      <div class="mb-3">
        <label class="form-label">Data desejada</label>
        <input type="date" name="dataMeta" class="form-control" required>
      </div>
      <div class="d-flex justify-content-between">
        <button type="submit" class="btn btn-success">Adicionar</button>
        <button type="button" class="btn btn-secondary" onclick="fecharPopup('popupMeta')">Cancelar</button>
      </div>
    </form>
  </div>
</div>

<div class="popup-overlay" id="popupEditarMeta">
  <div class="popup">
    <h5 class="mb-4">Editar Meta</h5>
    <form action="meta" method="post">
      <input type="hidden" name="acao" value="atualizar" />
      <input type="hidden" name="id" id="editarId" />
      <div class="mb-3">
        <label class="form-label">Descrição</label>
        <input type="text" name="descricao" id="editarDescricao" class="form-control" required>
      </div>
      <div class="mb-3">
        <label class="form-label">Valor</label>
        <input type="number" step="0.01" name="valor" id="editarValor" class="form-control" required>
      </div>
      <div class="mb-3">
        <label class="form-label">Data desejada</label>
        <input type="date" name="dataMeta" id="editarData" class="form-control" required>
      </div>
      <div class="d-flex justify-content-between">
        <button type="submit" class="btn btn-primary">Salvar</button>
        <button type="button" class="btn btn-secondary" onclick="fecharPopup('popupEditarMeta')">Cancelar</button>
      </div>
    </form>
  </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script>
  function abrirPopup(id) {
    document.getElementById(id).style.display = 'flex';
  }
  function fecharPopup(id) {
    document.getElementById(id).style.display = 'none';
  }
  function abrirMenu() {
    const menu = document.getElementById("menuOpcoes");
    menu.style.display = menu.style.display === "block" ? "none" : "block";
  }
  function abrirPopupEditar(botao) {
    document.getElementById('editarId').value = botao.getAttribute('data-id');
    document.getElementById('editarDescricao').value = botao.getAttribute('data-descricao');
    document.getElementById('editarValor').value = botao.getAttribute('data-valor');
    document.getElementById('editarData').value = botao.getAttribute('data-data');
    abrirPopup('popupEditarMeta');
  }

  // Gráfico de pizza
  const labels = [];
  const valores = [];
  <c:forEach var="meta" items="${metas}">
  labels.push("${meta.descricao}");
  valores.push(${meta.valorMeta});
  </c:forEach>

  const ctx = document.getElementById('graficoMetas').getContext('2d');
  new Chart(ctx, {
    type: 'pie',
    data: {
      labels: labels,
      datasets: [{
        label: 'Metas',
        data: valores,
        backgroundColor: ['#4e79a7', '#f28e2b', '#e15759', '#76b7b2', '#59a14f', '#edc949'],
        borderWidth: 1
      }]
    },
    options: {
      responsive: true,
      plugins: {
        legend: { position: 'bottom' },
        tooltip: {
          callbacks: {
            label: function(context) {
              return `${context.label}: R$ ${context.formattedValue}`;
            }
          }
        }
      }
    }
  });
</script>

</body>
</html>

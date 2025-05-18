package br.com.balistech.balistech.controller.saldo;

import br.com.balistech.balistech.dao.DaoFactory;
import br.com.balistech.balistech.dao.saldo.SaldoDao;
import br.com.balistech.balistech.exception.DBException;
import br.com.balistech.balistech.model.saldo.Saldo;
import br.com.balistech.balistech.model.usuario.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.time.LocalDate;

@WebServlet("/saldo")
public class SaldoServletController extends HttpServlet {
    private SaldoDao dao;

    @Override
    public void init() throws ServletException {
        super.init();
        dao = DaoFactory.getSaldoDao();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Pega o valor e o usuário da sessão
        double valor = Double.parseDouble(req.getParameter("valor"));
        HttpSession session = req.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        if (usuario == null) {
            resp.sendRedirect("login.jsp");
            return;
        }

        Long idUsuario = usuario.getId();
        Saldo saldoExistente = dao.buscarPorUsuario(idUsuario);

        try {
            if (saldoExistente != null) {
                saldoExistente.setValor(valor);
                saldoExistente.setUltimaAtualizacao(LocalDate.now());
                dao.atualizar(saldoExistente);
            } else {
                Saldo novoSaldo = new Saldo(null, idUsuario, valor, LocalDate.now());
                dao.inserir(novoSaldo);
            }
            resp.sendRedirect("main.jsp");
        } catch (DBException e) {
            req.setAttribute("erro", "Erro ao salvar saldo: " + e.getMessage());
            req.getRequestDispatcher("main.jsp").forward(req, resp);
        }
    }
}
package br.com.balistech.balistech.controller.metaFinanceira;

import br.com.balistech.balistech.dao.DaoFactory;
import br.com.balistech.balistech.dao.metaFinanceira.MetaFinanceiraDao;
import br.com.balistech.balistech.exception.DBException;
import br.com.balistech.balistech.model.metaFinanceira.MetaFinanceira;
import br.com.balistech.balistech.model.usuario.Usuario;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@WebServlet("/meta")
public class MetaFinanceiraController extends HttpServlet {
    private MetaFinanceiraDao dao;

    @Override
    public void init() throws ServletException {
        super.init();
        dao = DaoFactory.getMetaFinanceiraDao();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String acao = req.getParameter("acao");
        switch (acao) {
            case "cadastrar":
                cadastrar(req, resp);
                break;
            case "atualizar":
                atualizar(req, resp);
                break;
            case "remover":
                remover(req, resp);
                break;
        }
    }

    private void cadastrar(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario == null) {
            resp.sendRedirect("login.jsp");
            return;
        }

        try {
            String descricao = req.getParameter("descricao");
            double valorMeta = Double.parseDouble(req.getParameter("valor"));
            String dataMeta = req.getParameter("dataMeta");
            LocalDate data = LocalDate.parse(dataMeta, DateTimeFormatter.ofPattern("yyyy-MM-dd"));

            MetaFinanceira meta = new MetaFinanceira();
            meta.setIdUsuario(usuario.getId());
            meta.setDescricao(descricao);
            meta.setValorMeta(valorMeta);
            meta.setDataLimite(data);
            meta.setValorAtual(0.0);

            dao.cadastrar(meta);
            resp.sendRedirect("meta");
        } catch (DBException | NumberFormatException e) {
            req.setAttribute("erro", "Erro ao cadastrar meta: " + e.getMessage());
            req.getRequestDispatcher("main.jsp").forward(req, resp);
        }
    }

    private void atualizar(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Long id = Long.parseLong(req.getParameter("id"));
            String descricao = req.getParameter("descricao");
            double valorMeta = Double.parseDouble(req.getParameter("valor"));
            LocalDate data = LocalDate.parse(req.getParameter("dataMeta"), DateTimeFormatter.ofPattern("yyyy-MM-dd"));

            MetaFinanceira meta = dao.buscarPorId(id);
            if (meta != null) {
                meta.setDescricao(descricao);
                meta.setValorMeta(valorMeta);
                meta.setDataLimite(data);
                dao.atualizar(meta);
            }
            resp.sendRedirect("meta");
        } catch (DBException | NumberFormatException e) {
            req.setAttribute("erro", "Erro ao atualizar meta: " + e.getMessage());
            req.getRequestDispatcher("main.jsp").forward(req, resp);
        }
    }

    private void remover(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Long id = Long.parseLong(req.getParameter("id"));
            dao.remover(id);
            resp.sendRedirect("meta");
        } catch (DBException e) {
            req.setAttribute("erro", "Erro ao remover meta: " + e.getMessage());
            req.getRequestDispatcher("main.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario == null) {
            resp.sendRedirect("login.jsp");
            return;
        }

        List<MetaFinanceira> metas = dao.buscarPorUsuario(usuario.getId());
        req.setAttribute("metas", metas);
        req.getRequestDispatcher("main.jsp").forward(req, resp);
    }
}

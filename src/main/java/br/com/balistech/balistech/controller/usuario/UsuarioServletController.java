package br.com.balistech.balistech.controller.usuario;

import br.com.balistech.balistech.dao.DaoFactory;
import br.com.balistech.balistech.dao.metaFinanceira.MetaFinanceiraDao;
import br.com.balistech.balistech.dao.usuario.UsuarioDao;
import br.com.balistech.balistech.exception.DBException;
import br.com.balistech.balistech.model.metaFinanceira.MetaFinanceira;
import br.com.balistech.balistech.model.usuario.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/usuario")
public class UsuarioServletController extends HttpServlet {
    private UsuarioDao usuarioDao;
    private MetaFinanceiraDao metaDao;

    @Override
    public void init() throws ServletException {
        super.init();
        usuarioDao = DaoFactory.getUsuarioDao();
        metaDao = DaoFactory.getMetaFinanceiraDao();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String acao = req.getParameter("acao");

        switch (acao) {
            case "cadastrar":
                cadastrar(req, resp);
                break;
            case "login":
                login(req, resp);
                break;
            case "atualizar":
                atualizar(req, resp);
                break;
            case "logout":
                HttpSession sessionLogout = req.getSession(false);
                if (sessionLogout != null) {
                    sessionLogout.invalidate();
                }
                resp.sendRedirect("login.jsp");
                break;
        }
    }


    private void atualizar(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        if (usuario == null) {
            resp.sendRedirect("login.jsp");
            return;
        }

        String campo = req.getParameter("atualizarCampo");
        try {
            switch (campo) {
                case "nome":
                    usuario.setNome(req.getParameter("nome"));
                    break;
                case "email":
                    usuario.setEmail(req.getParameter("email"));
                    break;
                case "senha":
                    String senha = req.getParameter("senha");
                    String confirmar = req.getParameter("confirmarSenha");

                    if (!senha.equals(confirmar)) {
                        req.setAttribute("erro", "As senhas não coincidem.");
                        req.getRequestDispatcher("user-page.jsp").forward(req, resp);
                        return;
                    }
                    if (!senha.matches("^(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d]{8,}$")) {
                        req.setAttribute("erro", "A senha deve conter pelo menos 8 caracteres, uma letra maiúscula e um número.");
                        req.getRequestDispatcher("user-page.jsp").forward(req, resp);
                        return;
                    }

                    usuario.setSenha(senha);
                    break;
            }

            usuarioDao.atualizar(usuario);
            session.setAttribute("usuario", usuario);
            req.setAttribute("mensagem", "Alteração realizada com sucesso!");
            req.getRequestDispatcher("user-page.jsp").forward(req, resp);

        } catch (DBException e) {
            req.setAttribute("erro", "Erro ao atualizar: " + e.getMessage());
            req.getRequestDispatcher("user-page.jsp").forward(req, resp);
        }
    }

    private void cadastrar(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String nome = req.getParameter("nome");
            String email = req.getParameter("email");
            String senha = req.getParameter("senha");

            if (!senha.matches("^(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d]{8,}$")) {
                req.setAttribute("erro", "A senha deve ter pelo menos 8 caracteres, com pelo menos uma letra maiúscula e um número.");
                req.getRequestDispatcher("cadastro-usuario.jsp").forward(req, resp);
                return;
            }

            Usuario usuario = new Usuario();
            usuario.setNome(nome);
            usuario.setEmail(email);
            usuario.setSenha(senha);
            usuario.setTipoUsuario("comum");

            usuarioDao.cadastrar(usuario);
            req.setAttribute("mensagem", "Usuário cadastrado com sucesso!");
            req.getRequestDispatcher("cadastro-usuario.jsp").forward(req, resp);
        } catch (DBException e) {
            req.setAttribute("erro", "Erro ao cadastrar: " + e.getMessage());
            req.getRequestDispatcher("cadastro-usuario.jsp").forward(req, resp);
        }
    }


    private void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String senha = req.getParameter("senha");

        Usuario usuario = usuarioDao.buscarPorEmailSenha(email, senha);

        if (usuario != null) {
            HttpSession session = req.getSession();
            session.setAttribute("usuario", usuario);

            resp.sendRedirect("meta");
        } else {
            req.setAttribute("erro", "Email ou senha inválidos");
            req.getRequestDispatcher("login.jsp").forward(req, resp);
        }

        if (req.getParameter("lembrar") != null) {
            Cookie emailCookie = new Cookie("email", email);
            Cookie senhaCookie = new Cookie("senha", senha);
            emailCookie.setMaxAge(60 * 60 * 24 * 7);
            senhaCookie.setMaxAge(60 * 60 * 24 * 7);

            resp.addCookie(emailCookie);
            resp.addCookie(senhaCookie);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String acao = req.getParameter("acao");

        if ("logout".equals(acao)) {
            HttpSession sessionLogout = req.getSession(false);
            if (sessionLogout != null) {
                sessionLogout.invalidate();
            }
            resp.sendRedirect("login.jsp");
            return;
        }

        HttpSession session = req.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        if (usuario == null) {
            resp.sendRedirect("login.jsp");
            return;
        }

        List<MetaFinanceira> metas = metaDao.buscarPorUsuario(usuario.getId());
        req.setAttribute("metas", metas);

        req.getRequestDispatcher("main.jsp").forward(req, resp);
    }
}

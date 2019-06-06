/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.senac.tads.pi3.gerenprod.relatorioServlet;

import br.senac.tads.pi3.gerenprod.dao.AdministracaoDAO;
import br.senac.tads.pi3.gerenprod.dao.Auxiliar;
import br.senac.tads.pi3.gerenprod.dao.CrudInterface;
import br.senac.tads.pi3.gerenprod.dao.RelatorioDAO;
import br.senac.tads.pi3.gerenprod.model.Administracao;
import br.senac.tads.pi3.gerenprod.model.Relatorio;
import br.senac.tads.pi3.gerenprod.model.Usuario;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author AlexandreVinii
 */
@WebServlet(name = "RelatorioServlet", urlPatterns = {"/relatorio"})
public class RelatorioServlet extends HttpServlet {

    private final CrudInterface RelatorioDAO = new RelatorioDAO();
    private final CrudInterface filialDAO = new AdministracaoDAO();

    /***
     * Método usado para listar todos os registros de aluguel na tela de relatório.
     * 
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException 
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Usuario u = new Usuario(request);

        if (!u.acessaRelatorio()) {
            response.sendRedirect(request.getContextPath() + "/");
            return;
        }

        if (u.isGlobal()) {
            request.setAttribute("isGlobal", true);
        } else {
            request.setAttribute("isGlobal", false);
        }

        ArrayList<Relatorio> relatorios = RelatorioDAO.listar(u.getIdFilial());
        ArrayList<Administracao> filiais = filialDAO.listar(0);

        request.setAttribute("relatorios", relatorios);
        request.setAttribute("filiais", filiais);
        request.getRequestDispatcher("/relatorio.jsp").forward(request, response);
    }

    /***
     * Método usado para filtrar todos os registros de aluguel na tela de relatório.
     * 
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException 
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Usuario u = new Usuario(request);

        if (!u.acessaRelatorio()) {
            response.sendRedirect(request.getContextPath() + "/");
            return;
        }

        String dataInicio = request.getParameter("StartTime");
        String dataFinal = request.getParameter("EndTime");

        try {
            Date StartTime = Auxiliar.InputDateToUtilDate(dataInicio);
            Date EndTime = Auxiliar.InputDateToUtilDate(dataFinal);

            int idFilial = u.getIdFilial();

            if (u.isGlobal()) {
                request.setAttribute("isGlobal", true);
                idFilial = Integer.parseInt(request.getParameter("idFilial"));
            } else {
                request.setAttribute("isGlobal", false);
            }

            ArrayList<Relatorio> relatorios = RelatorioDAO.getAluguelByDates(StartTime, EndTime, idFilial);
            request.setAttribute("relatorios", relatorios);

            ArrayList<Administracao> filiais = filialDAO.listar(0);
            request.setAttribute("filiais", filiais);
            request.getRequestDispatcher("/relatorio.jsp").forward(request, response);

        } catch (ParseException ex) {
          
            if (u.isGlobal()) {
                request.setAttribute("isGlobal", true);
            } else {
                request.setAttribute("isGlobal", false);
            }

            ArrayList<Relatorio> relatorios = RelatorioDAO.listar(u.getIdFilial());
            ArrayList<Administracao> filiais = filialDAO.listar(0);

            request.setAttribute("relatorios", relatorios);
            request.setAttribute("filiais", filiais);
            request.getRequestDispatcher("/relatorio.jsp").forward(request, response);
        }
    }
}

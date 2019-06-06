/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.senac.tads.pi3.gerenprod.tiServlet;

import br.senac.tads.pi3.gerenprod.dao.AdministracaoDAO;
import br.senac.tads.pi3.gerenprod.dao.CrudInterface;
import br.senac.tads.pi3.gerenprod.dao.DepartamentoDAO;
import br.senac.tads.pi3.gerenprod.dao.TiDAO;
import br.senac.tads.pi3.gerenprod.model.Administracao;
import br.senac.tads.pi3.gerenprod.model.Departamento;
import br.senac.tads.pi3.gerenprod.model.Ti;
import br.senac.tads.pi3.gerenprod.model.Usuario;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 *
 * @author Bruna
 */
@WebServlet(name = "TiDesativarServlet", urlPatterns = {"/ti/desativar"})
public class TiDesativarServlet extends HttpServlet {

  private final CrudInterface tiDAO = new TiDAO();
  private final CrudInterface departamentoDAO = new DepartamentoDAO();
  private final CrudInterface filialDAO = new AdministracaoDAO();
 
  /**
   * Desativa um usuário na tela de TI. 
   * 
   * @param request
   * @param response
   * @throws ServletException
   * @throws IOException 
   */
 @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    
    Usuario u = new Usuario(request);
    
    if(!u.acessaTi()) {
      response.sendRedirect(request.getContextPath() + "/");
      return;
    }
    
    String id = request.getParameter("idUsuario");
    
    if (id != null) {
      int idUsuario = Integer.parseInt(id);
      
      boolean sucesso = tiDAO.desativar(idUsuario);
      request.setAttribute("sucesso", sucesso);

      if (sucesso) {
        request.setAttribute("mensagem", "Usuário desativado com sucesso!");
      } else {
        request.setAttribute("mensagem", "Não foi possível desativar o usuário. Por favor, tente novamente!");
      }
    } else {
      request.setAttribute("sucesso", false);
      request.setAttribute("mensagem", "Não foi possível desativar o usuário. Por favor, tente novamente!");
    }
    
    ArrayList<Departamento> departamentos = departamentoDAO.listar(u.getIdFilial());
    ArrayList<Ti> tis = tiDAO.listar(u.getIdFilial());
    ArrayList<Administracao> filiais = filialDAO.listar(0);
 
    request.setAttribute("tis", tis); 
    request.setAttribute("departamentos", departamentos);
    request.setAttribute("filiais", filiais);
    request.getRequestDispatcher("/ti.jsp").forward(request, response);
  }
  

}

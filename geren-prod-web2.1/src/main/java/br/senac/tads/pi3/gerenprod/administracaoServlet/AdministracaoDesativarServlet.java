/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.senac.tads.pi3.gerenprod.administracaoServlet;

/**
 *
 * @author caio.araujo
 */
import br.senac.tads.pi3.gerenprod.dao.CrudInterface;
import br.senac.tads.pi3.gerenprod.dao.AdministracaoDAO;
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
@WebServlet(name = "AdministracaoDesativarServlet", urlPatterns = {"/administracao/desativar"})
public class AdministracaoDesativarServlet extends HttpServlet {

  private final CrudInterface administracaoDAO = new AdministracaoDAO();
  
  /**
   * Desativa a filial. 
   * 
   * @param request
   * @param response
   * @throws ServletException
   * @throws IOException 
   */
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    
    Usuario u = new Usuario(request);
    
    if(!u.acessaAdministracao()) {
      response.sendRedirect(request.getContextPath() + "/");
      return;
    }
    
    String id = request.getParameter("idFilial");
    
    if (id != null) {
      int idFilial = Integer.parseInt(id);
      
      boolean sucesso = administracaoDAO.desativar(idFilial);
      request.setAttribute("sucesso", sucesso);

      if (sucesso) {
        request.setAttribute("mensagem", "Filial desativada com sucesso!");
      } else {
        request.setAttribute("mensagem", "Não foi possível desativar a Filial. Por favor, tente novamente!");
      }
    } else {
      request.setAttribute("sucesso", false);
      request.setAttribute("mensagem", "Não foi possível desativar a Filial. Por favor, tente novamente!");
    }
    
    ArrayList<AdministracaoServlet> administracaos = administracaoDAO.listar(1);
    
    request.setAttribute("administracaos", administracaos);
    request.getRequestDispatcher("/administracao.jsp").forward(request, response);
  }
}

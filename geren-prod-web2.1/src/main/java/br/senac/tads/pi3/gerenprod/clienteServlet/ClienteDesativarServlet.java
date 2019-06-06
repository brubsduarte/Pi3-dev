package br.senac.tads.pi3.gerenprod.clienteServlet;

import br.senac.tads.pi3.gerenprod.dao.ClienteDAO;
import br.senac.tads.pi3.gerenprod.dao.CrudInterface;
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
 * @author felip
 */
@WebServlet(name = "ClienteDesativarServlet", urlPatterns = {"/cliente/desativar"})
public class ClienteDesativarServlet extends HttpServlet {

  private final CrudInterface clienteDAO = new ClienteDAO();
  
  /**
   * Desativa um cliente. 
   * 
   * @param request
   * @param response
   * @throws ServletException
   * @throws IOException 
   */
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    
    Usuario u = new Usuario(request);
    
    if(!u.acessaProduto()) {
      response.sendRedirect(request.getContextPath() + "/");
      return;
    }
    
    String id = request.getParameter("idCliente");
    
    if (id != null) {
      int idCliente = Integer.parseInt(id);
      
      boolean sucesso = clienteDAO.desativar(idCliente);
      request.setAttribute("sucesso", sucesso);

      if (sucesso) {
        request.setAttribute("mensagem", "Cliente desativado com sucesso!");
      } else {
        request.setAttribute("mensagem", "N�o foi poss�vel desativar o Cliente. Por favor, tente novamente!");
      }
    } else {
      request.setAttribute("sucesso", false);
      request.setAttribute("mensagem", "N�o foi poss�vel desativar o Cliente. Por favor, tente novamente!");
    }
    
    ArrayList<ClienteServlet> clientes = clienteDAO.listar(1);
    
    request.setAttribute("clientes", clientes);
    request.getRequestDispatcher("/cliente.jsp").forward(request, response);
  }
}
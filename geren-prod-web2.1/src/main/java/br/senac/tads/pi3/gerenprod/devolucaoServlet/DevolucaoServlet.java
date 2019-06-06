/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.senac.tads.pi3.gerenprod.devolucaoServlet;

import br.senac.tads.pi3.gerenprod.dao.ClienteDAO;
import br.senac.tads.pi3.gerenprod.model.Cliente;
import br.senac.tads.pi3.gerenprod.model.Usuario;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Bruna
 */
@WebServlet(name = "DevolucaoServlet", urlPatterns = {"/devolucao"})
public class DevolucaoServlet extends HttpServlet {

  private final ClienteDAO clienteDAO = new ClienteDAO();
  
  /**
   * Metodo lista os clientes que estão alugando.
   * 
   * @param request
   * @param response
   * @throws ServletException
   * @throws IOException 
   */
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    Usuario u = new Usuario(request);
    
    if(!u.acessaDevolucao()) {
      response.sendRedirect(request.getContextPath() + "/");
      return;
    }
    
    ArrayList<Cliente> clientes = clienteDAO.listarAlugando(u.getIdFilial());
    
      SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
      String dateDevolucao = formato.format(new Date());
      request.setAttribute("hoje", dateDevolucao);
    
    request.setAttribute("clientes", clientes);
    request.getRequestDispatcher("/devolucao.jsp").forward(request, response);
  }
}

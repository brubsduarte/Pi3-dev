/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.senac.tads.pi3.gerenprod.loginServlet;

import br.senac.tads.pi3.gerenprod.model.Usuario;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Bruna
 */
@WebServlet(name = "BemVindoServlet", urlPatterns = {"/bemvindo"})
public class BemVindoServlet extends HttpServlet {

  /**
   * Mostra a tela de bem vindo.
   * 
   * @param request
   * @param response
   * @throws ServletException
   * @throws IOException 
   */
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    
    Usuario u = new Usuario(request);
    
    System.out.println("");
    System.out.println(u.getIdUsuario() + " " + u.getNomeDepartamento());
    System.out.println("");
    
    if(u.getIdUsuario() == 0) {
      response.sendRedirect(request.getContextPath() + "/");
      return;
    }
    
    request.getRequestDispatcher("/bemvindo.jsp").forward(request, response);
  }
}

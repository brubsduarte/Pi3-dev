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
import br.senac.tads.pi3.gerenprod.model.Administracao;
import br.senac.tads.pi3.gerenprod.model.Usuario;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet(name = "AdministracaoEditarServlet", urlPatterns = {"/administracao/editar"})
public class AdministracaoEditarServlet extends HttpServlet {

  private final CrudInterface AdministracaoDAO = new AdministracaoDAO();
  
  /**
   * Mostra a filial selecionada. 
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
      Administracao administracao = (Administracao) AdministracaoDAO.mostrar(idFilial);
      request.setAttribute("administracao", administracao);
    }
    
    ArrayList<AdministracaoServlet> administracaos = AdministracaoDAO.listar(1);
    
    request.setAttribute("administracaos", administracaos);
    request.getRequestDispatcher("/administracao.jsp").forward(request, response);
  }
  
  /**
   * Edita os dados cadastrados da filial selecionada. 
   * @param request
   * @param response
   * @throws ServletException
   * @throws IOException 
   */
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    
    Usuario u = new Usuario(request);
    
    if(!u.acessaAdministracao()) {
      response.sendRedirect(request.getContextPath() + "/");
      return;
    }
    
    Administracao p = new Administracao();
    
    p.setIdFilial(Integer.parseInt(request.getParameter("idFilial")));
    p.setNomeFilial(request.getParameter("nomeFilial"));
    p.setCnpj(request.getParameter("cnpj"));
    p.setEstado(request.getParameter("estado"));
    p.setCidade(request.getParameter("cidade"));
    p.setCep(request.getParameter("cep"));

    boolean sucesso = AdministracaoDAO.editar(p);
    request.setAttribute("sucesso", sucesso);
    
    if (sucesso) {
      request.setAttribute("mensagem", "Filial editada com sucesso!");
    } else {
      request.setAttribute("mensagem", "Não foi possivel editar a filial. Por favor, tente novamente!");
    }
    
    ArrayList<AdministracaoServlet> administracaos = AdministracaoDAO.listar(1);
    request.setAttribute("administracaos", administracaos);
    request.getRequestDispatcher("/administracao.jsp").forward(request, response);
  }
}


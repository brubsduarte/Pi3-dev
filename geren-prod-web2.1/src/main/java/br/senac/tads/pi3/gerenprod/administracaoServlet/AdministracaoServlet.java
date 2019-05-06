/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.senac.tads.pi3.gerenprod.administracaoServlet;

import br.senac.tads.pi3.gerenprod.dao.CrudInterface;
import br.senac.tads.pi3.gerenprod.dao.AdministracaoDAO;
import br.senac.tads.pi3.gerenprod.model.Administracao;
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
@WebServlet(name = "AdmnistracaoServlet", urlPatterns = {"/administracao"})
public class AdministracaoServlet extends HttpServlet {

  private final CrudInterface administracaoDAO = new AdministracaoDAO();
  
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    ArrayList<AdministracaoServlet> administracao = administracaoDAO.listar(1);
    
    request.setAttribute("administracao", administracao);
    request.getRequestDispatcher("/administracao.jsp").forward(request, response);
  }

  @Override
  protected void doPost(HttpServletRequest request,  HttpServletResponse response) throws ServletException, IOException {

    Administracao p = new Administracao();
    
    p.setNomeFilial(request.getParameter("nomeFilial"));
    p.setCNPJ(request.getParameter("cnpj"));
    p.setEstado(request.getParameter("estado"));
    p.setCidade(request.getParameter("cidade"));
    p.setCEP(request.getParameter("cep"));

    boolean sucesso = administracaoDAO.salvar(p);
    request.setAttribute("sucesso", sucesso);
    
    if (sucesso) {
      request.setAttribute("mensagem", "Filial cadastrado com sucesso!");
    } else {
      request.setAttribute("mensagem", "N�o foi poss�vel cadastrar o Filial. Por favor, tente novamente!");
    }
    
    ArrayList<AdministracaoServlet> administracao = administracaoDAO.listar(1);
    request.setAttribute("administracao", administracao);
    request.getRequestDispatcher("/administracao.jsp").forward(request, response);
  }
}




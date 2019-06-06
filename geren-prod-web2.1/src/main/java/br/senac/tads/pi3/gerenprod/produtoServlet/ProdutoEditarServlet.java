/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.senac.tads.pi3.gerenprod.produtoServlet;

import br.senac.tads.pi3.gerenprod.dao.CrudInterface;
import br.senac.tads.pi3.gerenprod.dao.ProdutoDAO;
import br.senac.tads.pi3.gerenprod.model.Produto;
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
@WebServlet(name = "ProdutoEditarServlet", urlPatterns = {"/produto/editar"})
public class ProdutoEditarServlet extends HttpServlet {

  private final CrudInterface produtoDAO = new ProdutoDAO();
  
  /**
   * Metodo que traz o produto selecionado para editar.
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
    
    String id = request.getParameter("idProduto");
    
    if (id != null) {
      int idProduto = Integer.parseInt(id);
      Produto produto = (Produto) produtoDAO.mostrar(idProduto);
      request.setAttribute("produto", produto);
    }
    
    ArrayList<ProdutoServlet> produtos = produtoDAO.listar(u.getIdFilial());
    
    request.setAttribute("produtos", produtos);
    request.getRequestDispatcher("/produto.jsp").forward(request, response);
  }
  
  /***
   * Metodo que salva as informações editadas. 
   * 
   * @param request
   * @param response
   * @throws ServletException
   * @throws IOException 
   */
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    
    Usuario u = new Usuario(request);
    
    if(!u.acessaProduto()) {
      response.sendRedirect(request.getContextPath() + "/");
      return;
    }
    
    Produto p = new Produto();
    
    p.setIdProduto(Integer.parseInt(request.getParameter("idProduto")));
    p.setNomeProduto(request.getParameter("nomeProduto"));
    p.setAno(request.getParameter("ano"));
    p.setModelo(request.getParameter("modelo"));
    p.setMarca(request.getParameter("marca"));
    p.setPlaca(request.getParameter("placa"));
    p.setPrecoDiaria(Double.parseDouble(request.getParameter("precoDiaria")));

    boolean sucesso = produtoDAO.editar(p);
    request.setAttribute("sucesso", sucesso);
    
    if (sucesso) {
      request.setAttribute("mensagem", "Produto editado com sucesso!");
    } else {
      request.setAttribute("mensagem", "Não foi possível editar o produto. Por favor, tente novamente!");
    }
    
    ArrayList<ProdutoServlet> produtos = produtoDAO.listar(u.getIdFilial());
    request.setAttribute("produtos", produtos);
    request.getRequestDispatcher("/produto.jsp").forward(request, response);
  }
}

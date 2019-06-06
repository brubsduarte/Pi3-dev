/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.senac.tads.pi3.gerenprod.clienteServlet;

import br.senac.tads.pi3.gerenprod.model.Usuario;
import br.senac.tads.pi3.gerenprod.dao.ClienteDAO;
import br.senac.tads.pi3.gerenprod.dao.CrudInterface;
import br.senac.tads.pi3.gerenprod.model.Cliente;
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
@WebServlet(name = "ClienteServlet", urlPatterns = {"/cliente"})
public class ClienteServlet extends HttpServlet {

  private final CrudInterface clienteDAO = new ClienteDAO();
  
  /**
   * Lista o cliente na tela de cliente.
   * 
   * @param request
   * @param response
   * @throws ServletException
   * @throws IOException 
   */
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    Usuario u = new Usuario(request);
    
    if(!u.acessaCliente()) {
      response.sendRedirect(request.getContextPath() + "/");
      return;
    }

    ArrayList<Cliente> clientes = clienteDAO.listar(1);
    
    request.setAttribute("clientes", clientes);
    request.getRequestDispatcher("/cliente.jsp").forward(request, response);
  }

  /**
   * Salva o cliente no banco de dados. 
   * 
   * @param request
   * @param response
   * @throws ServletException
   * @throws IOException 
   */
  @Override
  protected void doPost(HttpServletRequest request,  HttpServletResponse response) throws ServletException, IOException {

    Usuario u = new Usuario(request);
    
    if(!u.acessaCliente()) {
      response.sendRedirect(request.getContextPath() + "/");
      return;
    }
    
    Cliente c = new Cliente();
    
    c.setNomeCliente(request.getParameter("nomeCliente"));
    c.setCpf(request.getParameter("cpf"));
    c.setEmail(request.getParameter("email"));
    c.setCnh(request.getParameter("cnh"));
    c.setTelefone(request.getParameter("telefone"));
    c.setCep(request.getParameter("cep"));
    c.setRua(request.getParameter("rua"));
    c.setBairro(request.getParameter("bairro"));
    c.setCidade(request.getParameter("cidade"));
    c.setEstado(request.getParameter("estado"));

    boolean sucesso = clienteDAO.salvar(c);
    request.setAttribute("sucesso", sucesso);
    
    if (sucesso) {
      request.setAttribute("mensagem", "Cliente cadastrado com sucesso!");
    } else {
      request.setAttribute("mensagem", "Não foi possível cadastrar o Cliente. Por favor, tente novamente!");
    }
    
    ArrayList<Cliente> clientes = clienteDAO.listar(1);
    
    request.setAttribute("clientes", clientes);
    request.getRequestDispatcher("/cliente.jsp").forward(request, response);
  }
}

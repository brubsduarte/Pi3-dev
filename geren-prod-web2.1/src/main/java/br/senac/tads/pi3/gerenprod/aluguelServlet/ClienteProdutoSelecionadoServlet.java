/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.senac.tads.pi3.gerenprod.aluguelServlet;

import br.senac.tads.pi3.gerenprod.dao.AluguelDAO;
import br.senac.tads.pi3.gerenprod.dao.CrudInterface;
import br.senac.tads.pi3.gerenprod.dao.ClienteDAO;
import br.senac.tads.pi3.gerenprod.dao.ProdutoDAO;
import br.senac.tads.pi3.gerenprod.model.Aluguel;
import br.senac.tads.pi3.gerenprod.model.Cliente;
import br.senac.tads.pi3.gerenprod.model.Produto;
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
 * @author Gustavo
 */
@WebServlet(name = "ClienteProdutoSelecionadoServlet", urlPatterns = {"/aluguel/selecionar"})
public class ClienteProdutoSelecionadoServlet extends HttpServlet {

  private final ProdutoDAO produtoDAO = new ProdutoDAO();
  private final ClienteDAO clienteDAO = new ClienteDAO();
  private final CrudInterface aluguelDAO = new AluguelDAO();

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    Usuario u = new Usuario(request);

    if (!u.acessaAluguel()) {
      response.sendRedirect(request.getContextPath() + "/");
      return;
    }

    SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
    String hoje = formato.format(new Date());
    request.setAttribute("hoje", hoje);
    
    ArrayList<Produto> produtos = produtoDAO.listarNaoAlugado(u.getIdFilial());
    request.setAttribute("produtos", produtos);
    ArrayList<Cliente> clientes = clienteDAO.listarNaoAlugando(u.getIdFilial());
    request.setAttribute("clientes", clientes);

    String idClienteTela = request.getParameter("idCliente");

    if (!idClienteTela.equals("")) {
      int idCliente = Integer.parseInt(idClienteTela);
      Cliente clienteSelecionado = (Cliente) clienteDAO.mostrar(idCliente);
      request.setAttribute("clienteSelecionado", clienteSelecionado);
      
      request.setAttribute("sucesso", true);
      request.setAttribute("mensagem", "Selecionado com sucesso!");
    }

    String idProdutoTela = request.getParameter("idProduto");

    if (!idProdutoTela.equals("")) {
      int idProduto = Integer.parseInt(idProdutoTela);
      Produto produtoSelecionado = (Produto) produtoDAO.mostrar(idProduto);
      request.setAttribute("produtoSelecionado", produtoSelecionado);
      
      request.setAttribute("sucesso", true);
      request.setAttribute("mensagem", "Selecionado com sucesso!");
    }

    request.getRequestDispatcher("/aluguel.jsp").forward(request, response);
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    Usuario u = new Usuario(request);

    if (!u.acessaAluguel()) {
      response.sendRedirect(request.getContextPath() + "/");
      return;
    }

    SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
    String hoje = formato.format(new Date());
    request.setAttribute("hoje", hoje);
    
    Aluguel a = new Aluguel();

    try {
      a.setDataInicial(formato.parse(request.getParameter("dateRetirada")));
    } catch (ParseException ex) {
      Logger.getLogger(ClienteProdutoSelecionadoServlet.class.getName()).log(Level.SEVERE, null, ex);
    }

    a.setIdCliente(Integer.parseInt(request.getParameter("idClienteSelecionado")));
    a.setIdFilial(u.getIdFilial());
    a.setIdProduto(Integer.parseInt(request.getParameter("idProdutoSelecionado")));

    boolean sucesso = aluguelDAO.salvar(a);
    request.setAttribute("sucesso", sucesso);

    if (sucesso) {
      request.setAttribute("mensagem", "Aluguel feito com sucesso!");
    } else {
      request.setAttribute("mensagem", "Não foi possível fazer o aluguel. Por favor, tente novamente!");
    }

    ArrayList<Produto> produtos = produtoDAO.listarNaoAlugado(u.getIdFilial());
    request.setAttribute("produtos", produtos);
    ArrayList<Cliente> clientes = clienteDAO.listarNaoAlugando(u.getIdFilial());
    request.setAttribute("clientes", clientes);

    request.getRequestDispatcher("/aluguel.jsp").forward(request, response);
  }
}

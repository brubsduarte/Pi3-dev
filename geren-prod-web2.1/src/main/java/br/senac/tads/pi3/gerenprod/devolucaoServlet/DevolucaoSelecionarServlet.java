/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.senac.tads.pi3.gerenprod.devolucaoServlet;

import br.senac.tads.pi3.gerenprod.dao.AluguelDAO;
import br.senac.tads.pi3.gerenprod.dao.ClienteDAO;
import br.senac.tads.pi3.gerenprod.model.Aluguel;
import br.senac.tads.pi3.gerenprod.model.Cliente;
import br.senac.tads.pi3.gerenprod.model.Usuario;
import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import static java.time.temporal.ChronoUnit.DAYS;
import java.util.Date;

/**
 *
 * @author Bruna
 */
@WebServlet(name = "DevolucaoSelecionarServlet", urlPatterns = {"/devolucao/selecionar"})
public class DevolucaoSelecionarServlet extends HttpServlet {

  private final ClienteDAO clienteDAO = new ClienteDAO();
  private final AluguelDAO aluguelDAO = new AluguelDAO();
  
  /**
   * Metodo que mostra o aluguel selecionado para a devolução.
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
    
    String idClienteTela = request.getParameter("idCliente");

    if (!idClienteTela.equals("")) {
      request.setAttribute("sucesso", true);
      request.setAttribute("mensagem", "Cliente selecionado.");
      
      int idCliente = Integer.parseInt(idClienteTela);
      Aluguel aluguel = aluguelDAO.mostrar(idCliente);
      
      request.setAttribute("idCliente", idClienteTela);
      request.setAttribute("clienteSelecionado", aluguel.getCliente());
      request.setAttribute("produtoSelecionado", aluguel.getProduto());
      request.setAttribute("aluguel", aluguel);
      
      String pattern = "dd/MM/yyyy";
      DateFormat df = new SimpleDateFormat(pattern);
      
      SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
      String date = formato.format(new Date());
      request.setAttribute("hoje", date);
      
      request.setAttribute("dataRetirada", df.format(aluguel.getDataInicial()));
    }
    
    ArrayList<Cliente> clientes = clienteDAO.listarAlugando(u.getIdFilial());
    
    request.setAttribute("clientes", clientes);
    request.getRequestDispatcher("/devolucao.jsp").forward(request, response);
  }
  
  /**
   * Metodo que calcula o valor do aluguel.
   * 
   * @param request
   * @param response
   * @throws ServletException
   * @throws IOException 
   */
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    Usuario u = new Usuario(request);

    if (!u.acessaAluguel()) {
      response.sendRedirect(request.getContextPath() + "/");
      return;
    }
    
    SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
    String date = formato.format(new Date());
    request.setAttribute("hoje", date);

    String idClienteTela = request.getParameter("idClienteSelecionado");
    String dataSelecionada = request.getParameter("dateDevolucao");

    if (!idClienteTela.equals("") && !dataSelecionada.equals("")) {
      int idCliente = Integer.parseInt(idClienteTela);
      Aluguel aluguel = aluguelDAO.mostrar(idCliente);
      
      request.setAttribute("clienteSelecionado", aluguel.getCliente());
      request.setAttribute("produtoSelecionado", aluguel.getProduto());
      request.setAttribute("aluguel", aluguel);
      
      request.setAttribute("dataRetirada", request.getParameter("dataRetirada"));
      
      // Calcula a devolução
      
      double precoDiaria = Double.parseDouble(request.getParameter("precoDiaria"));
      DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
      long quantidadeDias = DAYS.between(
              LocalDate.parse(request.getParameter("dataRetirada"), formatador), 
              LocalDate.parse(request.getParameter("dateDevolucao"), formatador));

      request.setAttribute("dateDevolucao", request.getParameter("dateDevolucao"));
      
      double total = ((double)quantidadeDias + 1) * precoDiaria;
      
      DecimalFormat format = new DecimalFormat("R$ ###,###.00");
   
      request.setAttribute("valorTotal", format.format(total));
      
      request.setAttribute("sucesso", true);
      request.setAttribute("mensagem", "Valor calculado abaixo.");
    } else {
      request.setAttribute("sucesso", false);
      request.setAttribute("mensagem", "Selecione o cliente e a data de devolução.");
    }
    
    ArrayList<Cliente> clientes = clienteDAO.listarAlugando(u.getIdFilial());
    
    request.setAttribute("clientes", clientes);
    request.getRequestDispatcher("/devolucao.jsp").forward(request, response);
  }
}

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
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import static java.time.temporal.ChronoUnit.DAYS;
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
@WebServlet(name = "DevolucaoDevolverServlet", urlPatterns = {"/devolucao/devolver"})
public class DevolucaoDevolverServlet extends HttpServlet {

  private final ClienteDAO clienteDAO = new ClienteDAO();
  private final AluguelDAO aluguelDAO = new AluguelDAO();
  
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    Usuario u = new Usuario(request);

    if (!u.acessaAluguel()) {
      response.sendRedirect(request.getContextPath() + "/");
      return;
    }
    
    String idClienteTela = request.getParameter("idClienteSelecionado");

    if (!idClienteTela.equals("")) {
      int idCliente = Integer.parseInt(idClienteTela);
      Aluguel aluguel = aluguelDAO.mostrar(idCliente);
      
      // Calcula a devolução
      
      double precoDiaria = Double.parseDouble(request.getParameter("precoDiaria"));
      DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
      long quantidadeDias = DAYS.between(
              LocalDate.parse(request.getParameter("dataRetirada"), formatador), 
              LocalDate.parse(request.getParameter("dateDevolucao"), formatador));
      
      double total = ((double)quantidadeDias + 1) * precoDiaria;
      aluguel.setPrecoTotal(total);
      aluguel.setDataFinal(java.sql.Date.valueOf(LocalDate.parse(request.getParameter("dateDevolucao"), formatador)));
      
      boolean sucesso = aluguelDAO.devolver(aluguel);
      request.setAttribute("sucesso", sucesso);

      if (sucesso) {
        request.setAttribute("mensagem", "Devolução realizada com sucesso!");
      } else {
        request.setAttribute("mensagem", "Não foi possível realizar a devolução. Por favor, tente novamente!");
      }
    }
    
    ArrayList<Cliente> clientes = clienteDAO.listarAlugando(u.getIdFilial());
    
    request.setAttribute("clientes", clientes);
    request.getRequestDispatcher("/devolucao.jsp").forward(request, response);
  }
    
}

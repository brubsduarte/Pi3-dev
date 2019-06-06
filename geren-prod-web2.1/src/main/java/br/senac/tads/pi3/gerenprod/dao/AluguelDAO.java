/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.senac.tads.pi3.gerenprod.dao;

import br.senac.tads.pi3.gerenprod.db.DB;
import br.senac.tads.pi3.gerenprod.model.Aluguel;
import br.senac.tads.pi3.gerenprod.model.Cliente;
import br.senac.tads.pi3.gerenprod.model.Produto;
import br.senac.tads.pi3.gerenprod.model.Relatorio;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Gustavo
 */
public class AluguelDAO implements CrudInterface<Aluguel> {

  /**
   * Mostra os alugueis.
   * 
   * @param ID
   * @return 
   */
  @Override
  public Aluguel mostrar(int ID) {
    DB db = new DB(true);
    try {
      String sql = "select cliente.*, aluguel.*, produto.* from cliente inner join aluguel on aluguel.idCliente = cliente.idCliente inner join produto on produto.idProduto = aluguel.idProduto where cliente.idCliente = " + ID + ";";
      ResultSet rs = db.executarConsulta(sql);
      Produto p = new Produto();
      Aluguel a = new Aluguel();
      Cliente c = new Cliente();
      while (rs.next()) {
        p.setIdProduto(rs.getInt("idProduto"));
        p.setNomeProduto(rs.getString("NomeProduto"));
        p.setAno(rs.getString("Ano"));
        p.setModelo(rs.getString("Modelo"));
        p.setMarca(rs.getString("Marca"));
        p.setPlaca(rs.getString("Placa"));
        p.setPrecoDiaria(rs.getDouble("PrecoDiaria"));
        p.setAlugado(rs.getBoolean("Alugado"));
        p.setAtivo(rs.getBoolean("Ativo"));
        
        c.setIdCliente(rs.getInt("idCliente"));
        c.setNomeCliente(rs.getString("Nome"));
        c.setCpf(rs.getString("CPF"));
        c.setEmail(rs.getString("Email"));
        c.setCnh(rs.getString("CNH"));
        c.setTelefone(rs.getString("Telefone"));
        c.setCep(rs.getString("CEP"));
        c.setRua(rs.getString("Rua"));
        c.setBairro(rs.getString("Bairro"));
        c.setCidade(rs.getString("Cidade"));
        c.setEstado(rs.getString("Estado"));
        c.setAtivo(rs.getBoolean("Ativo"));
        
        a.setIdAluguel(rs.getInt("idAluguel"));
        a.setDataInicial(rs.getDate("Datainicial"));
        a.setIdCliente(rs.getInt("idCliente"));
        a.setIdProduto(rs.getInt("idProduto"));
        a.setIdFilial(rs.getInt("idFilial"));
        a.setCliente(c);
        a.setProduto(p);
      }
      db.close();
      return a;
    } catch (SQLException ex) {
      System.out.println(ex.getMessage());
      db.close();
      return null;
    }
  }

  @Override
  public boolean editar(Aluguel objeto) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  /**
   * Salva um aluguel.
   * 
   * @param a
   * @return 
   */
  @Override
  public boolean salvar(Aluguel a) {
    DB db = new DB(false);

    try {

      String pattern = "yyyy-MM-dd";
      DateFormat df = new SimpleDateFormat(pattern);

      String sql
        = "INSERT INTO aluguel "
        + "(DataInicial, idCliente, idFilial, idProduto)"
        + "VALUES ("
        + "'" + df.format(a.getDataInicial()) + "', "
        + a.getIdCliente() + ", "
        + a.getIdFilial() + ", "
        + a.getIdProduto() + ");";

      if (!db.executarAlteracao(sql)) {
        throw new Exception("Não foi possivel cadastrar o aluguel.");
      }

      sql
        = "UPDATE produto SET "
        + "Alugado = 1 "
        + "Where idProduto = " + a.getIdProduto() + ";";

      if (!db.executarAlteracao(sql)) {
        throw new Exception("Não foi possivel cadastrar o aluguel.");
      }
      
      sql
        = "UPDATE cliente SET "
        + "Alugando = 1 "
        + "Where idCliente = " + a.getIdCliente()+ ";";

      if (!db.executarAlteracao(sql)) {
        throw new Exception("Não foi possivel cadastrar o aluguel.");
      }

      db.commit();
      db.close();
      return true;

    } catch (Exception e) {
      System.out.println(e.getMessage());
      db.rollback();
      db.close();
      return false;
    }
  }

  /**
   * Devolução do aluguel.
   * @param a
   * @return 
   */
  public boolean devolver(Aluguel a) {
    DB db = new DB(false);

    try {

      String pattern = "yyyy-MM-dd";
      DateFormat df = new SimpleDateFormat(pattern);

      String sql
        = "UPDATE aluguel SET "
        + "DataFinal = '" + df.format(a.getDataFinal()) + "', "
        + "ValorTotal = " + a.getPrecoTotal() + " "
        + "Where idAluguel = " + a.getIdAluguel()+ ";";
      
      System.out.println(sql);
      
      if (!db.executarAlteracao(sql)) {
        throw new Exception("Não foi possivel devolver o produto.");
      }

      sql
        = "UPDATE produto SET "
        + "Alugado = 0 "
        + "Where idProduto = " + a.getIdProduto() + ";";

      System.out.println(sql);
      
      if (!db.executarAlteracao(sql)) {
        throw new Exception("Não foi possivel devolver o produto.");
      }
      
      sql
        = "UPDATE cliente SET "
        + "Alugando = 0 "
        + "Where idCliente = " + a.getIdCliente()+ ";";

      System.out.println(sql);
      
      if (!db.executarAlteracao(sql)) {
        throw new Exception("Não foi possivel devolver o produto.");
      }

      db.commit();
      db.close();
      return true;

    } catch (Exception e) {
      System.out.println(e.getMessage());
      db.rollback();
      db.close();
      return false;
    }
  }
  
  @Override
  public boolean desativar(int ID) {
    return false;
  }

  @Override
  public ArrayList<Aluguel> listar(int idFilial) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public ArrayList<Relatorio> getAluguelByDates(Date dataInicial, Date dataFinal, int idFilial) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

}

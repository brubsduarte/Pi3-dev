/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.senac.tads.pi3.gerenprod.dao;

import br.senac.tads.pi3.gerenprod.db.DB;
import br.senac.tads.pi3.gerenprod.model.Produto;
import br.senac.tads.pi3.gerenprod.model.Relatorio;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Bruna
 */
public class ProdutoDAO implements CrudInterface<Produto> {

  /**
   * Lista os produtos.
   * 
   * @param idFilial
   * @return 
   */
  @Override
  public ArrayList<Produto> listar(int idFilial) {
    DB db = new DB(true);
    try {
      String sql = "SELECT * FROM produto WHERE idFilial = " + idFilial + " AND Ativo = true;";
      ResultSet rs = db.executarConsulta(sql);
      ArrayList<Produto> produtos = new ArrayList<>();
      while (rs.next()) {
        Produto p = new Produto();
        p.setIdProduto(rs.getInt("idProduto"));
        p.setNomeProduto(rs.getString("NomeProduto"));
        p.setAno(rs.getString("Ano"));
        p.setModelo(rs.getString("Modelo"));
        p.setMarca(rs.getString("Marca"));
        p.setPlaca(rs.getString("Placa"));
        p.setPrecoDiaria(rs.getDouble("PrecoDiaria"));
        p.setAlugado(rs.getBoolean("Alugado"));
        p.setAtivo(rs.getBoolean("Ativo"));
        produtos.add(p);
      }
      db.close();
      return produtos;
    } catch (SQLException ex) {
      System.out.println(ex.getMessage());
      db.close();
      return null;
    }
  }
  
  /**
   * Lista os produtos alugados.
   * @param idFilial
   * @return 
   */
  public ArrayList<Produto> listarAlugando(int idFilial) {
    DB db = new DB(true);
    try {
      String sql = "SELECT * FROM produto WHERE idFilial = " + idFilial + " AND Alugado = true AND Ativo = true;";
      ResultSet rs = db.executarConsulta(sql);
      ArrayList<Produto> produtos = new ArrayList<>();
      while (rs.next()) {
        Produto p = new Produto();
        p.setIdProduto(rs.getInt("idProduto"));
        p.setNomeProduto(rs.getString("NomeProduto"));
        p.setAno(rs.getString("Ano"));
        p.setModelo(rs.getString("Modelo"));
        p.setMarca(rs.getString("Marca"));
        p.setPlaca(rs.getString("Placa"));
        p.setPrecoDiaria(rs.getDouble("PrecoDiaria"));
        p.setAlugado(rs.getBoolean("Alugado"));
        p.setAtivo(rs.getBoolean("Ativo"));
        produtos.add(p);
      }
      db.close();
      return produtos;
    } catch (SQLException ex) {
      System.out.println(ex.getMessage());
      db.close();
      return null;
    }
  }
  
  /**
   * Lista os produtos não alugados.
   * @param idFilial
   * @return 
   */
  public ArrayList<Produto> listarNaoAlugado(int idFilial) {
    DB db = new DB(true);
    try {
      String sql = "SELECT * FROM produto WHERE idFilial = " + idFilial + " AND Alugado = false AND Ativo = true;";
      ResultSet rs = db.executarConsulta(sql);
      ArrayList<Produto> produtos = new ArrayList<>();
      while (rs.next()) {
        Produto p = new Produto();
        p.setIdProduto(rs.getInt("idProduto"));
        p.setNomeProduto(rs.getString("NomeProduto"));
        p.setAno(rs.getString("Ano"));
        p.setModelo(rs.getString("Modelo"));
        p.setMarca(rs.getString("Marca"));
        p.setPlaca(rs.getString("Placa"));
        p.setPrecoDiaria(rs.getDouble("PrecoDiaria"));
        p.setAlugado(rs.getBoolean("Alugado"));
        p.setAtivo(rs.getBoolean("Ativo"));
        produtos.add(p);
      }
      db.close();
      return produtos;
    } catch (SQLException ex) {
      System.out.println(ex.getMessage());
      db.close();
      return null;
    }
  }
  
  /**
   * Mostra os produtos.
   * 
   * @param idProduto
   * @return 
   */
  @Override
  public Produto mostrar(int idProduto) {
    DB db = new DB(true);
    try {
      String sql = "SELECT * FROM produto WHERE idProduto = " + idProduto + ";";
      ResultSet rs = db.executarConsulta(sql);
      Produto p = new Produto();
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
      }
      db.close();
      return p;
    } catch (SQLException ex) {
      System.out.println(ex.getMessage());
      db.close();
      return null;
    }
  }

  /**
   * Edita o produto. 
   * @param p
   * @return 
   */
  @Override
  public boolean editar(Produto p) {
    DB db = new DB(false);

    try {

      String sql
              = "UPDATE produto SET "
              + "NomeProduto = '" + p.getNomeProduto() + "', "
              + "Ano = '" + p.getAno() + "', "
              + "Modelo = '" + p.getModelo() + "', "
              + "Marca = '" + p.getMarca()+ "', "
              + "Placa = '" + p.getPlaca() + "', "
              + "PrecoDiaria = " + p.getPrecoDiaria() + " "
              + "Where idProduto = " + p.getIdProduto() + "; ";
      
      if (!db.executarAlteracao(sql)) {
        throw new Exception("Não foi possivel atualizar o produto.");
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
   * Salva os produtos.
   * @param p
   * @return 
   */
  @Override
  public boolean salvar(Produto p) {
    DB db = new DB(false);

    try {

      String sql
              = "INSERT INTO produto "
              + "(NomeProduto, Ano, Modelo, Marca, Placa, PrecoDiaria, Alugado, idFilial, Ativo)"
              + "VALUES ("
              + "'" + p.getNomeProduto() + "', "
              + "'" + p.getAno() + "', "
              + "'" + p.getModelo() + "', "
              + "'" + p.getMarca() + "', "
              + "'" + p.getPlaca() + "', "
              + p.getPrecoDiaria() + ", "
              + "false, "
              + p.getIdFilial() + ", "
              + "true );";

      if (!db.executarAlteracao(sql)) {
        throw new Exception("Não foi possível cadastrar o produto.");
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
   * Desativa o produto.
   * 
   * @param produtoID
   * @return 
   */
  @Override
  public boolean desativar(int produtoID) {
    DB db = new DB(false);

    try {

      String sql
              = "UPDATE produto SET "
              + "Ativo = false "
              + "Where idProduto = " + produtoID + "; ";

      if (!db.executarAlteracao(sql)) {
        throw new Exception("Não foi possível desativar o produto.");
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
  public ArrayList<Relatorio> getAluguelByDates(Date dataInicial, Date dataFinal, int idFilial) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }
}

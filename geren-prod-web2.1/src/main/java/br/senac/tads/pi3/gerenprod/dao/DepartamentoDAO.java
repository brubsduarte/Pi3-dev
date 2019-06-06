/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.senac.tads.pi3.gerenprod.dao;

import br.senac.tads.pi3.gerenprod.db.DB;
import br.senac.tads.pi3.gerenprod.model.Departamento;
import br.senac.tads.pi3.gerenprod.model.Relatorio;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Bruna
 */
public class DepartamentoDAO implements CrudInterface<Departamento> {

  /**
   * Lista o departamento. 
   * 
   * @param idFilial
   * @return 
   */
  @Override
  public ArrayList<Departamento> listar(int idFilial) {
    DB db = new DB(true);
    try {
      String sql = "SELECT * FROM departamento;";
      ResultSet rs = db.executarConsulta(sql);
      ArrayList<Departamento> departamentos = new ArrayList<>();
      while (rs.next()) {
        Departamento d = new Departamento();
        d.setIdDepartamento(rs.getInt("idDepartamento"));
        d.setNomeDepartamento(rs.getString("NomeDepartamento"));
        departamentos.add(d);
      }
      db.close();
      return departamentos;
    } catch (SQLException ex) {
      System.out.println(ex.getMessage());
      db.close();
      return null;
    }
  }

  @Override
  public Departamento mostrar(int ID) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public boolean editar(Departamento objeto) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public boolean salvar(Departamento objeto) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public boolean desativar(int ID) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public ArrayList<Relatorio> getAluguelByDates(Date dataInicial, Date dataFinal, int idFilial) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

}


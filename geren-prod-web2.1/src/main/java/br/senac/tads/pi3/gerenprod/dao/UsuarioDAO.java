/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.senac.tads.pi3.gerenprod.dao;

import br.senac.tads.pi3.gerenprod.db.DB;
import br.senac.tads.pi3.gerenprod.model.Usuario;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Bruna
 */
public class UsuarioDAO {

  /**
   * Faz o login do usuário. 
   * 
   * @param usuario
   * @return 
   */
  public Usuario login (Usuario usuario) {
    DB db = new DB(true);
    try {
      
      String sql = "select usuario.idUsuario, usuario.NomeUsuario, usuario.Email, usuario.Senha, usuario.Ativo, usuario.idDepartamento, departamento.NomeDepartamento, usuario.idFilial, filial.NomeFilial from usuario inner join departamento on departamento.idDepartamento = usuario.idDepartamento inner join filial on filial.idFilial = usuario.idFilial "
                 + "where usuario.Email = '" + usuario.getEmail() + "' and usuario.Ativo = true;";
      
      System.out.println(sql);
      
      ResultSet rs = db.executarConsulta(sql) != null ? db.executarConsulta(sql) : null;
      
      if(rs == null) {
        return usuario;
      }
      
      while (rs.next()) {
        usuario = new Usuario();
        usuario.setIdUsuario(rs.getInt("idUsuario"));
        usuario.setNome(rs.getString("NomeUsuario"));
        usuario.setEmail(rs.getString("Email"));
        usuario.setSenha(rs.getString("Senha"));
        usuario.setAtivo(rs.getBoolean("Ativo"));
        usuario.setIdDepartamento(rs.getInt("idDepartamento"));
        usuario.setIdFilial(rs.getInt("idFilial"));
        usuario.setNomeDepartamento(rs.getString("NomeDepartamento"));
        usuario.setNomeFilial(rs.getString("NomeFilial"));
      }
      db.close();
      
      return usuario;
    } catch (SQLException ex) {
      System.out.println(ex.getMessage());
      db.close();
      return null;
    }
  }
}
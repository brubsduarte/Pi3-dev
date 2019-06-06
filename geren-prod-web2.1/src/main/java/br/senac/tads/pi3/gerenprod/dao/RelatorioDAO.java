/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.senac.tads.pi3.gerenprod.dao;

import br.senac.tads.pi3.gerenprod.db.DB;
import br.senac.tads.pi3.gerenprod.model.Relatorio;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author mac-ale
 */
public class RelatorioDAO implements CrudInterface<Relatorio> {

  /**
   * Lista o aluguel.
   * 
   * @param idFilial
   * @return 
   */
    @Override
    public ArrayList<Relatorio> listar(int idFilial) {
        DB db = new DB(true);
        try {
            String sql = "SELECT\n"
                    + "idAluguel,\n"
                    + "DataFinal,\n"
                    + "Nome,\n"
                    + "nomeProduto,\n"
                    + "precoDiaria,\n"
                    + "valorTotal\n"
                    + "FROM Aluguel\n"
                    + "INNER JOIN Cliente ON Cliente.idCliente = Aluguel.idCliente\n"
                    + "INNER JOIN Produto ON Produto.idProduto = Aluguel.idProduto ";
            
            if(idFilial != 0) {
              sql += "WHERE Aluguel.idFilial = " + idFilial;
            }

            ResultSet rs = db.executarConsulta(sql);

            ArrayList<Relatorio> relatorios = new ArrayList<>();
            while (rs.next()) {
                Relatorio relat = new Relatorio();
                relat.setIdAluguel(rs.getInt("idAluguel"));
                relat.setDataFinal(rs.getDate("DataFinal"));
                relat.setNomeCliente(rs.getString("Nome"));
                relat.setNomeProduto(rs.getString("nomeProduto"));
                relat.setPrecoDiaria(rs.getDouble("precoDiaria"));
                relat.setValorTotal(rs.getDouble("valorTotal"));
                relatorios.add(relat);
            }

            db.close();
            return relatorios;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            db.close();
            return null;
        }
    }
    
    /**
     * Busca informação de aluguel de acordo com a data selecionada. 
     * 
     * @param de
     * @param para
     * @param idFilial
     * @return 
     */
    @Override
    public ArrayList<Relatorio> getAluguelByDates(Date de, Date para, int idFilial) {
        DB db = new DB(true);
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        
        String dataInicio = sdf.format(de);
        
        String dataFim = sdf.format(para);

        try {
            String sql = "SELECT\n"
                    + "idAluguel,\n"
                    + "DataFinal,\n"
                    + "Nome,\n"
                    + "NomeProduto,\n"
                    + "PrecoDiaria,\n"
                    + "ValorTotal\n"
                    + "FROM Aluguel\n"
                    + "INNER JOIN Cliente ON Cliente.idCliente = Aluguel.idCliente\n"
                    + "INNER JOIN Produto ON Produto.idProduto = Aluguel.idProduto\n"
                    + "WHERE DataFinal BETWEEN '"+dataInicio+"' AND '"+dataFim+"' ";

            if(idFilial != 0) {
              sql += "AND Aluguel.idFilial = " + idFilial;
            }

            ResultSet rs = db.executarConsulta(sql);
            ArrayList<Relatorio> resultado = new ArrayList<>();
            
            while (rs.next()) {
                Relatorio relat = new Relatorio();
                relat.setIdAluguel(rs.getInt("idAluguel"));
                relat.setDataFinal(rs.getDate("DataFinal"));
                relat.setNomeCliente(rs.getString("Nome"));
                relat.setNomeProduto(rs.getString("nomeProduto"));
                relat.setPrecoDiaria(rs.getDouble("precoDiaria"));
                relat.setValorTotal(rs.getDouble("valorTotal"));
                resultado.add(relat);
            }

            db.close();
            return resultado;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            db.close();
            return null;
        }
    }


    @Override
    public Relatorio mostrar(int ID) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean editar(Relatorio objeto) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean salvar(Relatorio objeto) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean desativar(int ID) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}

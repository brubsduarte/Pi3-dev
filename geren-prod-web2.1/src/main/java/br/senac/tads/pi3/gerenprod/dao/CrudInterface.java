/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.senac.tads.pi3.gerenprod.dao;

import br.senac.tads.pi3.gerenprod.model.Relatorio;
import java.util.ArrayList;
import java.util.Date;

/**
 * Interface com os métodos de listar, mostrar, editar, salvar e desativar. 
 * @author Bruna
 * @param <O>
 */
public interface CrudInterface<O> {
  
  public ArrayList<O> listar(int idFilial);
  public O mostrar(int ID);
  public boolean editar(O objeto);
  public boolean salvar(O objeto);
  public boolean desativar(int ID);

  public ArrayList<Relatorio> getAluguelByDates(Date dataInicial, Date dataFinal, int idFilial);
}

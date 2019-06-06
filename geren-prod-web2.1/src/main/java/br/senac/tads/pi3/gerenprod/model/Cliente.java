/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.senac.tads.pi3.gerenprod.model;

/**
 *
 * @author Felippe
 */
public class Cliente extends Object {

  private int idCliente;
  private String nomeCliente, cpf, email, cnh, telefone, cep, rua, bairro, cidade, estado;
  private boolean ativo;

  public Cliente(int idCliente, String nomeCliente, String cpf, String email, String cnh, String telefone, String cep, String rua, String bairro, String cidade, String estado, boolean ativo) {
    this.idCliente = idCliente;
    this.nomeCliente = nomeCliente;
    this.cpf = cpf;
    this.email = email;
    this.cnh = cnh;
    this.telefone = telefone;
    this.cep = cep;
    this.rua = rua;
    this.bairro = bairro;
    this.cidade = cidade;
    this.estado = estado;
    this.ativo = ativo;
  }

  public Cliente() {
    this.idCliente = 0;
    this.nomeCliente = "";
    this.cpf = "";
    this.email = "";
    this.cnh = "";
    this.telefone = "";
    this.cep = "";
    this.rua = "";
    this.bairro = "";
    this.cidade = "";
    this.estado = "";
    this.ativo = true;
  }

  public int getIdCliente() {
    return idCliente;
  }

  public void setIdCliente(int idCliente) {
    this.idCliente = idCliente;
  }

  public String getNomeCliente() {
    return nomeCliente;
  }

  public void setNomeCliente(String nomeCliente) {
    this.nomeCliente = nomeCliente;
  }

  public String getCpf() {
    return cpf;
  }

  public void setCpf(String cpf) {
    this.cpf = cpf;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getCnh() {
    return cnh;
  }

  public void setCnh(String cnh) {
    this.cnh = cnh;
  }

  public String getTelefone() {
    return telefone;
  }

  public void setTelefone(String telefone) {
    this.telefone = telefone;
  }

  public String getCep() {
    return cep;
  }

  public void setCep(String cep) {
    this.cep = cep;
  }

  public String getRua() {
    return rua;
  }

  public void setRua(String rua) {
    this.rua = rua;
  }

  public String getBairro() {
    return bairro;
  }

  public void setBairro(String bairro) {
    this.bairro = bairro;
  }

  public String getCidade() {
    return cidade;
  }

  public void setCidade(String cidade) {
    this.cidade = cidade;
  }

  public String getEstado() {
    return estado;
  }

  public void setEstado(String estado) {
    this.estado = estado;
  }

  public boolean isAtivo() {
    return ativo;
  }

  public void setAtivo(boolean ativo) {
    this.ativo = ativo;
  }
}

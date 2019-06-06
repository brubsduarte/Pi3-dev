package br.senac.tads.pi3.gerenprod.model;

/**
 * 
 * @author Bruna
 */
public class Produto extends Object {
    
    private int idProduto, idFilial;
    private String nomeProduto, ano, modelo, marca, placa;
    private double precoDiaria;
    private boolean alugado, ativo;

    public Produto() {
        this.idProduto = 0;
        this.idFilial = 0;
        this.nomeProduto = "";
        this.ano = "";
        this.modelo = "";
        this.marca = "";
        this.placa = "";
        this.precoDiaria = 0;
        this.alugado = false;
        this.ativo = true;
    }
    
    public Produto(int idProduto, int idFilial, String nomeProduto, String ano, String modelo, String marca, String placa, double precoDiaria, boolean alugado, boolean ativo) {
        this.idProduto = idProduto;
        this.idFilial = idFilial;
        this.nomeProduto = nomeProduto;
        this.ano = ano;
        this.modelo = modelo;
        this.marca = marca;
        this.placa = placa;
        this.precoDiaria = precoDiaria;
        this.alugado = alugado;
        this.ativo = ativo;
    }

    public int getIdProduto() {
        return idProduto;
    }
    
     public int getIdFilial() {
        return idFilial;
    }

    public void setIdProduto(int idProduto) {
        this.idProduto = idProduto;
    }
    
     public void setIdFilial(int idFilial) {
        this.idFilial = idFilial;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    public String getAno() {
        return ano;
    }

    public void setAno(String ano) {
        this.ano = ano;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public double getPrecoDiaria() {
        return precoDiaria;
    }

    public void setPrecoDiaria(double precoDiaria) {
        this.precoDiaria = precoDiaria;
    }

    public boolean isAlugado() {
        return alugado;
    }

    public void setAlugado(boolean alugado) {
        this.alugado = alugado;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }  
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author aaaaaaaaaaaaaaaaaaaa
 */
public class Turma {
    private int codigo;
    private String descricao;
    
    public Turma(){
        
    }
    
    
    public Turma(int c, String d){
        codigo = c;
        descricao = d;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    
    public static ArrayList <Turma> carregarLista(){
        
        if(Banco.conexao == null){
            System.out.println("Banco de dados não conectado");
            return null;
        }
        
        ArrayList <Turma> listaTurmas = new ArrayList <Turma>();
        
        try{
            String sql = "SELECT * FROM turma";
            PreparedStatement pst = Banco.conexao.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            pst.clearParameters();
            
            while(rs.next()){
                Turma turma = new Turma();
                turma.setCodigo(rs.getInt("Codigo"));
                turma.setDescricao(rs.getString("descricao"));
                
                listaTurmas.add(turma);
                
            }
            return listaTurmas;
            
        }
        catch(SQLException e){
            System.out.println(e);
            return null;
        }
        
        
        
    }
    
    public boolean excluir(){
        
        if(Banco.conexao == null){
            return false;
        }
        
        try{
            String sql = "DELETE FROM turma WHERE codigo = ?";
            PreparedStatement pst = Banco.conexao.prepareStatement(sql);
            pst.setInt(1,codigo);
            pst.executeUpdate();
            pst.clearParameters();
            return true;
            
            
        }
        catch(SQLException e){
            System.out.println(e);
            return false;
        }
    }
    
    public boolean alterar(){
        if(Banco.conexao == null){
            return false;
        }
        
        try{
            String sql = "UPDATE turma SET descricao = ? WHERE codigo = ?";
            PreparedStatement pst = Banco.conexao.prepareStatement(sql);
            pst.setString(1,descricao);
            pst.setInt(2,codigo);
            pst.executeUpdate();
            pst.clearParameters();
            return true;
            
        }
        catch(SQLException e){
            System.out.println(e);
            return false; 
        }
        
        
    }
    
    public boolean salvar(){
        if(Banco.conexao == null){
            return false;
        }
        
        try{
            String sql = "INSERT INTO turma (descricao) VALUES (?)";
            PreparedStatement pst = Banco.conexao.prepareStatement(sql);
            pst.setString(1,descricao);
            pst.executeUpdate();
            pst.clearParameters();
            return true;
        }
        catch(SQLException e){
            System.out.println(e);
            return false;
        }
    }  
      
}

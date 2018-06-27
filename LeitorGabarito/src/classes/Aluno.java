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
public class Aluno {
    private int codigo;
    private Turma turma;
    private String nome;
    private String sobrenome;
    
    
    public Aluno(){
        
    }
    
    public Aluno (int c, Turma t, String n, String sn ){
        codigo = c;
        turma = t;
        nome = n;
        sobrenome = sn;
  
    }
    
    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public Turma getTurma() {
        return turma;
    }

    public void setTurma(Turma turma) {
        this.turma = turma;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }
        
    public boolean salvar(){
        if(Banco.conexao == null){
            return false;
        }
        
        String sql = "INSERT INTO aluno Values (?,?,?,?)";
        
        try{
            PreparedStatement pst = Banco.conexao.prepareStatement(sql);
            pst.setInt(1, codigo);
            pst.setInt(2, turma.getCodigo() );
            pst.setString(3, nome);
            pst.setString(4, sobrenome);

            
            pst.executeUpdate();
            pst.clearParameters();
            
            return true;
        }
        catch(SQLException e){
            System.out.println(e);
            return false;
        }       
    } // salvar
    
    public boolean alterar(){
        if(Banco.conexao == null){
            return false;
        }
        
        try{
            String sql = "Update aluno set codigo = ?, codigo_turma = ?, nome = ?, sobrenome = ? "
                + " WHERE codigo = ? ";
            PreparedStatement pst = Banco.conexao.prepareStatement(sql);
            
            pst.setInt(1, codigo);
            pst.setInt(2, turma.getCodigo() );
            pst.setString(3, nome);
            pst.setString(4, sobrenome);
            pst.setInt(5, codigo);
            
            pst.executeUpdate();
            pst.clearParameters();
            
            return true;
            
        }
        catch(SQLException e){
            System.out.println(e);
            return false;
        }
              
    } // alterar
    
    public boolean excluir(){
        if(Banco.conexao == null){
            return false;
        }
        
        try{
            String sql = "CALL  excluir_aluno(?)";
            PreparedStatement pst = Banco.conexao.prepareStatement(sql);
            pst.setInt(1, codigo);
            pst.executeUpdate();
            pst.clearParameters();
            return true;
        }
        catch(SQLException e){
            System.out.println(e);
            return false;
        }
        
    }
    
    public boolean ler(int c){
        if(Banco.conexao ==  null){
            return false;
        }
        
        try{
            String sql = "Select a.codigo, a.nome, a.sobrenome, a.codigo_turma, t.descricao as descricao_turma "
                    + "FROM aluno a join turma t on a.codigo_turma = t.codigo AND a.codigo = ?";
            PreparedStatement pst = Banco.conexao.prepareStatement(sql);
            pst.setInt(1, c);
            ResultSet rs = pst.executeQuery();
            
            if( !rs.next() ){
                return false;
            }
            
            codigo = c;
            nome = rs.getString("nome");
            sobrenome = rs.getString("sobrenome");
            turma.setCodigo( rs.getInt("codigo_turma") );
            turma.setDescricao( rs.getString("descricao_turma") );
                      
            return true;
        }
        catch(SQLException e){
            System.out.println(e);
            return false;
        }
        
    }
    
    public static ArrayList <Aluno> carregarLista(){
        
        if(Banco.conexao == null){
            System.out.println("Banco não conectado");
            return null;
        }
        
        ArrayList <Aluno> listaAluno = new ArrayList <Aluno> () ;
        
        try{
            String sql = "Select a.codigo, a.nome, a.sobrenome, a.codigo_turma,"
                    + " t.descricao as descricao_turma "
                    + "FROM aluno a join turma t on a.codigo_turma = t.codigo";
            
            PreparedStatement pst = Banco.conexao.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            pst.clearParameters();
            
            while(rs.next()){
                
                Aluno aluno = new Aluno();
                aluno.setCodigo(rs.getInt("codigo"));
                aluno.setNome(rs.getString("nome"));
                aluno.setSobrenome(rs.getString("sobrenome"));
                
                Turma turma = new Turma();
                turma.setCodigo( rs.getInt("codigo_turma") );
                turma.setDescricao( rs.getString("descricao_turma") );
                aluno.setTurma(turma);
                
                listaAluno.add(aluno);
            }
            
            
        }
        catch(SQLException e){
            System.out.println(e);
            return null;
        }
        
        return listaAluno;
        
    }
    

    

    
}

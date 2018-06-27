/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author aaaaaaaaaaaaaaaaaaaa
 */
public class Correcao {
    
    private int codigo;
    private int matriculaAluno;
    private String codigoProva;
    private String diaCorrecao;
    private String mesCorrecao;
    private String anoCorrecao;
    private ArrayList <Character> listaQuestoes;
    
    
     public Correcao(){
        listaQuestoes = new ArrayList<>();
    }
     
    public Correcao(int c, int mA, String cP){
        this();
        codigo = c;
        matriculaAluno = mA;
        codigoProva = cP;  
    }
    
    public int getQuantidadeQuestoes(){
        return listaQuestoes.size();
    }
    
    public String getNomeAluno(){
        try{
            String sql = "SELECT nome, sobrenome FROM aluno WHERE codigo = ?";
            PreparedStatement pst = Banco.conexao.prepareStatement(sql);
            pst.setInt(1, matriculaAluno);
            ResultSet rs = pst.executeQuery();
            pst.clearParameters();
            rs.next();
            return rs.getString("nome") + " " + rs.getString("sobrenome");
        }
        catch(SQLException e){
            e.printStackTrace();
            return null;
        }
    }

   
    
    public void setQuestao(char resposta){
        listaQuestoes.add(resposta);
    }
    
    public void setQuestao(String resposta){
        
        if(resposta.isEmpty()){
            listaQuestoes.add(' ');
            return;
        }
        if(resposta != null){
            listaQuestoes.add(resposta.charAt(0));
        }
    }
    
    public char getQuestao(int index){
        if(index >= listaQuestoes.size()){
            return ' ';
        }
        
        return listaQuestoes.get(index);
        

    }
    
    public void limparQuestoes(){
        listaQuestoes.clear();
    }
    
    
    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public int getMatriculaAluno() {
        return matriculaAluno;
    }

    public void setMatriculaAluno(int matriculaAluno) {
        this.matriculaAluno = matriculaAluno;
    }

    public String getCodigoProva() {
        return codigoProva;
    }

    public void setCodigoProva(String codigoProva) {
        this.codigoProva = codigoProva;
    }
    
    public String getDiaCorrecao() {
        return diaCorrecao;
    }

    public String getMesCorrecao() {
        return mesCorrecao;
    }

    public String getAnoCorrecao() {
        return anoCorrecao;
    }
    
    public void setData(String data){
        diaCorrecao = data.substring(0,2);
        mesCorrecao = data.substring(2,4);
        anoCorrecao = data.substring(4);
    }
    
    public void setDataAgora(){
       SimpleDateFormat df = new SimpleDateFormat("ddMMyyyy");

       Date agora = new Date();
       String data = df.format(agora); 
       diaCorrecao = data.substring(0,2);
       mesCorrecao = data.substring(2,4);
       anoCorrecao = data.substring(4); 

    }
    

    public static ArrayList <Correcao> carregarLista(int codigo_prova) {
        if (Banco.conexao == null) {
            System.out.println("Banco de dados não conectado");
            return null;
        }
        
        SimpleDateFormat df = new SimpleDateFormat("ddMMyyyy");

        ArrayList<Correcao> listaCorrecoes = new ArrayList<>();

        try {
            String sql = "SELECT * FROM correcao Where codigo_prova = ?";
            PreparedStatement pst = Banco.conexao.prepareStatement(sql);
            pst.setInt(1,codigo_prova);          
            ResultSet rs = pst.executeQuery();
            pst.clearParameters();

            while (rs.next()) {
                Correcao correcao = new Correcao();
                correcao.setCodigo(rs.getInt("Codigo"));
                correcao.setCodigoProva(rs.getString("Codigo_prova"));
                correcao.setMatriculaAluno(rs.getInt("Codigo_aluno"));
                correcao.setData(df.format(rs.getDate("data_correcao")));

                
                sql = "SELECT * FROM questao_correcao WHERE codigo_correcao = ?";
                pst = Banco.conexao.prepareStatement(sql);
                pst.setInt(1,correcao.getCodigo());
                ResultSet rs2 = pst.executeQuery();
                pst.clearParameters();
                
                while(rs2.next()){
                    correcao.setQuestao(rs2.getString("resposta"));
                }
                
                listaCorrecoes.add(correcao);

            }
            return listaCorrecoes;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }

    public boolean excluir() {

        if (Banco.conexao == null) {
            return false;
        }

        try {
            String sql = "DELETE FROM questao_correcao WHERE codigo_correcao = ?";
            PreparedStatement pst = Banco.conexao.prepareStatement(sql);
            pst.setInt(1, codigo);
            pst.executeUpdate();
            pst.clearParameters();
            
            sql = "DELETE FROM correcao WHERE codigo = ?";
            pst = Banco.conexao.prepareStatement(sql);
            pst.setInt(1, codigo);
            pst.executeUpdate();
            pst.clearParameters();

            return true;

        } catch (SQLException e) {
            System.out.println(e);
            return false;
        }
    }

    public boolean salvar() {
        if (Banco.conexao == null) {
            return false;
        }

        try{
            String sql = "DELETE FROM questao_correcao WHERE codigo_correcao = (SELECT codigo FROM correcao "
                    + "WHERE codigo_aluno = ? " 
                    +"AND codigo_prova = ?)";
            PreparedStatement pst = Banco.conexao.prepareStatement(sql);
            pst.setInt(1, matriculaAluno);
            pst.setString(2, codigoProva);
            pst.executeUpdate();
            pst.clearParameters();
            


            sql = "DELETE FROM correcao WHERE codigo_aluno = ? AND codigo_prova = ?";
            pst = Banco.conexao.prepareStatement(sql);
            pst.setInt(1, matriculaAluno);
            pst.setString(2, codigoProva);
            pst.executeUpdate();
            pst.clearParameters();
        }
        catch(SQLException e){
            System.out.println("Não foi possivel setar o codigo correcao em salvar\n"+e);
            
        }
        
        
        try {
            
            String sql = "INSERT INTO correcao (codigo_aluno, codigo_prova, data_correcao ) VALUES (?,?,?)";
            PreparedStatement pst = Banco.conexao.prepareStatement(sql);
            pst.setInt(1, matriculaAluno);
            pst.setString(2, codigoProva);
            pst.setString(3, anoCorrecao + "-" + mesCorrecao + "-" + diaCorrecao);
            pst.executeUpdate();
            pst.clearParameters();
            
            
            sql = "SELECT codigo FROM correcao WHERE codigo_aluno = ? AND codigo_prova = ?";
            pst = Banco.conexao.prepareStatement(sql);
            pst.setInt(1, matriculaAluno);
            pst.setString(2, codigoProva);
            ResultSet rs = pst.executeQuery();
            pst.clearParameters();
            rs.next();
            codigo = rs.getInt("codigo");
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        
        for(int i = 0; i < listaQuestoes.size(); i++){
            try {
                String sql = "INSERT INTO questao_correcao (codigo_correcao, resposta,numero_questao ) VALUES (?,?,?)";
                PreparedStatement pst = Banco.conexao.prepareStatement(sql);

                pst.setInt(1, codigo);
                pst.setString( 2, String.valueOf( listaQuestoes.get(i) ) );
                pst.setInt(3,(i+1));
                pst.executeUpdate();
                pst.clearParameters();            
            } 
            catch (SQLException e) {
                e.printStackTrace();
                return false;
            } 
        }
        
        return true;

    }
  
  
    
}

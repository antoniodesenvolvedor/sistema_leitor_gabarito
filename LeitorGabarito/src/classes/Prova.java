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
public class Prova {

    private int codigo;
    private String disciplina;
    private String diaCriacao;
    private String mesCriacao;
    private String anoCriacao;
    private ArrayList <Character> listaQuestoes;
    
     public char getQuestao(int index){
         if(index >= listaQuestoes.size()){
             return ' ';
         }
         
         
        return listaQuestoes.get(index);
           
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
            listaQuestoes.add( resposta.charAt(0) );
        }
    }
    
    public void limparQuestoes(){
        listaQuestoes.clear();
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int Codigo) {
        this.codigo = Codigo;
    }

    public String getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(String disciplina) {
        this.disciplina = disciplina;
    }

    public String getDiaCriacao() {
        return diaCriacao;
    }

    public String getMesCriacao() {
        return mesCriacao;
    }

    public String getAnoCriacao() {
        return anoCriacao;
    }
    
    public void setData(String data){
        diaCriacao = data.substring(0,2);
        mesCriacao = data.substring(2,4);
        anoCriacao = data.substring(4);
    }
    
    public void setDataAgora(){
       SimpleDateFormat df = new SimpleDateFormat("ddMMyyyy");

       Date agora = new Date();
       String data = df.format(agora); 
       diaCriacao = data.substring(0,2);
       mesCriacao = data.substring(2,4);
       anoCriacao = data.substring(4); 
       
    }


    public Prova(String d) {
        this();
        disciplina = d;
    }

    public Prova() {    
        listaQuestoes = new ArrayList<>();
    }
    

    public static ArrayList <Prova> carregarLista() {
        if (Banco.conexao == null) {
            System.out.println("Banco de dados não conectado");
            return null;
        }
        SimpleDateFormat df = new SimpleDateFormat("ddMMyyyy");

        ArrayList<Prova> listaProvas = new ArrayList<>();

        try {
            String sql = "SELECT * FROM prova";
            PreparedStatement pst = Banco.conexao.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            pst.clearParameters();

            while (rs.next()) {
                Prova prova = new Prova();
                prova.setCodigo(rs.getInt("Codigo"));
                prova.setData( df.format( rs.getDate("data_criacao") ) );
                prova.setDisciplina( rs.getString("disciplina"));
                
                sql = "SELECT * FROM questao_prova WHERE codigo_prova = ?";
                pst = Banco.conexao.prepareStatement(sql);
                pst.setInt(1,prova.getCodigo());
                ResultSet rs2 = pst.executeQuery();
                pst.clearParameters();
                
                while(rs2.next()){
                    prova.setQuestao(rs2.getString("resposta"));
                }
                listaProvas.add(prova);

            }
            return listaProvas;

        } catch (SQLException e) {
            System.out.println(e);
            return null;
        }

    }

    public boolean excluir() {

        if (Banco.conexao == null) {
            return false;
        }

        try {
            String sql = "CALL excluir_prova(?)";
            PreparedStatement pst = Banco.conexao.prepareStatement(sql);
            pst.setInt(1, codigo);
            pst.executeUpdate();
            pst.clearParameters();          

            return true;

        } catch (SQLException e) {
            System.out.println(e);
            return false;
        }
    }

    public boolean alterar() {
        if (Banco.conexao == null) {
            return false;
        }

        try {
            String sql = "UPDATE prova SET disciplina = ? WHERE codigo = ?";
            PreparedStatement pst = Banco.conexao.prepareStatement(sql);
            pst.setString(1, disciplina);
            pst.setInt(2, codigo);
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

        try {
            String sql = "INSERT INTO prova (disciplina, data_criacao ) VALUES (?,?)";
            PreparedStatement pst = Banco.conexao.prepareStatement(sql);
            pst.setString(1, disciplina);
            pst.setString(2, anoCriacao + "-" + mesCriacao + "-" + diaCriacao);
            pst.executeUpdate();
            pst.clearParameters();
            return true;
        } catch (SQLException e) {
            System.out.println(e);
            return false;
        }
    }
    
    
    public void salvarGabarito(){
        if (Banco.conexao == null) {
            return;
        }
        
        // limpando as questões anterior para não precisar verificar se deve-se alterar ou inserir
        try{
            String sql = "DELETE FROM questao_prova WHERE codigo_prova = ?";
            PreparedStatement pst = Banco.conexao.prepareStatement(sql);
            
            pst.setInt(1, codigo );
            pst.executeUpdate();
            pst.clearParameters();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        
        
        for(int i = 0; i < listaQuestoes.size(); i++){
            try {
                String sql = "INSERT INTO questao_prova (codigo_prova, resposta,numero_questao ) VALUES (?,?,?)";
                PreparedStatement pst = Banco.conexao.prepareStatement(sql);

                pst.setInt(1, codigo);
                pst.setString( 2, String.valueOf(listaQuestoes.get(i)) );
                pst.setInt(3,(i+1));
                pst.executeUpdate();
                pst.clearParameters();
            
            } 
            catch (SQLException e) {
                e.printStackTrace();
                return;
            } 
        }

    }
    
    
    public int getQuantidadeQuestoes(){
        if(Banco.conexao == null){
            return 0;
        }
        
        try{
            String sql = "SELECT COUNT(codigo) FROM questao_prova WHERE codigo_prova = ?";
            PreparedStatement pst = Banco.conexao.prepareStatement(sql);
            pst.setInt(1,codigo);
            ResultSet rs = pst.executeQuery();
            pst.clearParameters();
            
            rs.next();
            return rs.getInt("count(codigo)");
            
            
        }
        catch(SQLException e){
            System.out.println(e);
            return 0;
        }
        
        
    }

}

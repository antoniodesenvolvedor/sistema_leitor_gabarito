/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import classes.Banco;
import classes.Correcao;
import classes.Prova;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author aaaaaaaaaaaaaaaaaaaa
 */
public class TelaCorrecoes extends javax.swing.JFrame {

    private ArrayList <Correcao> listaCorrecoes;
    private Prova prova;
    
    public TelaCorrecoes() {
        initComponents();
    }
    
    public TelaCorrecoes(Prova p){
        this();
        prova = p;
        listaCorrecoes = Correcao.carregarLista(prova.getCodigo());
        carregarTable(jTableCorrecoes);
        
        
        jLabelCodigoProva.setText(""+prova.getCodigo());
        jLabelDataCriacao.setText(prova.getDiaCriacao() + "/" + prova.getMesCriacao() + "/" + prova.getAnoCriacao());
        jLabelDisciplina.setText(prova.getDisciplina());
        
        int quantidadeQuestoes = prova.getQuantidadeQuestoes();
        jLabelQuantidadeQuestoes.setText(""+quantidadeQuestoes);
        try{
            String sql = "SELECT COUNT(*) FROM correcao WHERE codigo_prova = ?";
            PreparedStatement pst = Banco.conexao.prepareStatement(sql);
            pst.setInt(1,prova.getCodigo());
            ResultSet rs = pst.executeQuery();
            pst.clearParameters();
            rs.next();
            jLabelQuantidadeCorrecoes.setText(rs.getString("COUNT(*)"));                        
        }
        catch(SQLException e){
            System.out.println(e);                      
        }
        
        double mediaProva = 0;
        double notaMaxima = 0;
        double notaMinima = 100;
        DefaultTableModel model = (DefaultTableModel) jTableCorrecoes.getModel();
        
        for(int i = 1; i < jTableCorrecoes.getRowCount(); i++){
            double temp =  Double.parseDouble( model.getValueAt(i, 2).toString() );
            mediaProva += temp;
            if(notaMaxima < temp){
                notaMaxima = temp;
            }
            if(notaMinima > temp){
                notaMinima = temp;
            }
        }
        

        try{
           // calculo média
           mediaProva = mediaProva / listaCorrecoes.size() / quantidadeQuestoes * 10 ; 
        }
        catch(ArithmeticException e){
            mediaProva = 0;
        }
        
        notaMaxima = notaMaxima / quantidadeQuestoes * 10;
        notaMinima = notaMinima / quantidadeQuestoes * 10;
        
        DecimalFormat df = new DecimalFormat("0.#");
        jLabelMediaProva.setText("" + df.format(mediaProva));
        jLabelNotaMaxima.setText("" + df.format(notaMaxima));
        jLabelNotaMinima.setText("" + df.format(notaMinima));
        
        
    }
    

    private void carregarTable(JTable jTable){
        if(listaCorrecoes == null){
            return; 
        }
        
        int linha = 0;    
        int quantidadeAcertos = 0;

        // quantidade de correções
        int quantidadeCorrecoes = listaCorrecoes.size();
        
        // quantidade de questões por correção
        int quantidadeQuestoes = listaCorrecoes.get(0).getQuantidadeQuestoes();
        
        String dadosCorrecao[] = new String[quantidadeQuestoes + 4];
        
        DefaultTableModel model;         
        model = (DefaultTableModel) jTable.getModel();

        
        // adicionando dados da prova
        dadosCorrecao[0] = "Gabarito Modelo";
        for(int i = 4; i < dadosCorrecao.length; i++){
            dadosCorrecao[i] = "" + prova.getQuestao( i-4 );
        }
        model.addRow(dadosCorrecao);
        // posição da linha a ser adiconado à table
        linha++;
        
        // adicionando dados das correções
        for(int i = 0; i < quantidadeCorrecoes; i++){
           quantidadeAcertos = 0;
           dadosCorrecao[0] = listaCorrecoes.get(i).getNomeAluno();
           dadosCorrecao[1] = "" + listaCorrecoes.get(i).getMatriculaAluno();

           for(int j = 4; j < dadosCorrecao.length; j++){
               dadosCorrecao[j] = "" + listaCorrecoes.get(i).getQuestao(j - 4);
               
               
               if(dadosCorrecao[j].equals(""+ prova.getQuestao(j-4))){                   
                   quantidadeAcertos++;
               }  
           }

           dadosCorrecao[2] = "" + quantidadeAcertos;
           if(quantidadeQuestoes == 0){
              dadosCorrecao[3] = "0"; 
           } 
           else{
              DecimalFormat df = new DecimalFormat("0.#"); 
              dadosCorrecao[3] =  df.format( (double)quantidadeAcertos / quantidadeQuestoes * 10);  
           }
           model.addRow(dadosCorrecao);
           linha++;
        }

        jTable.setModel( model );                       
              
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        jTableCorrecoes = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabelCodigoProva = new javax.swing.JLabel();
        jLabelDataCriacao = new javax.swing.JLabel();
        jLabelDisciplina = new javax.swing.JLabel();
        jLabelQuantidadeQuestoes = new javax.swing.JLabel();
        jLabelQuantidadeCorrecoes = new javax.swing.JLabel();
        jLabelMediaProva = new javax.swing.JLabel();
        jLabelNotaMaxima = new javax.swing.JLabel();
        jLabelNotaMinima = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Correções");

        jTableCorrecoes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nome", "Matrícula", "Acertos", "Nota", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59", "60", "61", "62", "63", "64", "65", "66", "67", "68", "69", "70", "71", "72", "73", "74", "75", "76", "77", "78", "79", "80", "81", "82", "83", "84", "85", "86", "87", "88", "89", "90"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableCorrecoes.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jTableCorrecoes.setCellSelectionEnabled(true);
        jScrollPane2.setViewportView(jTableCorrecoes);
        jTableCorrecoes.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        if (jTableCorrecoes.getColumnModel().getColumnCount() > 0) {
            jTableCorrecoes.getColumnModel().getColumn(0).setPreferredWidth(200);
        }
        for(int i = 4; i < jTableCorrecoes.getColumnModel().getColumnCount(); i++){
            jTableCorrecoes.getColumnModel().getColumn(i).setPreferredWidth(30);
        }

        jLabel1.setText("Código  Prova:");

        jLabel2.setText("Data de Criação:");

        jLabel3.setText("Disciplina:");

        jLabel4.setText("Quantidade Questoes");

        jLabel5.setText("Média Prova:");

        jLabel6.setText("Quantidade Correções");

        jLabel7.setText("Nota Máxima");

        jLabel8.setText("Nota Mínima");

        jLabelCodigoProva.setText("0");

        jLabelDataCriacao.setText("__/__/____");

        jLabelDisciplina.setText("__________________");

        jLabelQuantidadeQuestoes.setText("0");

        jLabelQuantidadeCorrecoes.setText("0");

        jLabelMediaProva.setText("0");

        jLabelNotaMaxima.setText("0");

        jLabelNotaMinima.setText("0");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 89, Short.MAX_VALUE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelDisciplina)
                    .addComponent(jLabelCodigoProva)
                    .addComponent(jLabelDataCriacao))
                .addGap(34, 34, 34)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabelQuantidadeQuestoes))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(18, 18, 18)
                        .addComponent(jLabelQuantidadeCorrecoes))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabelMediaProva)))
                .addGap(51, 51, 51)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelNotaMinima)
                    .addComponent(jLabelNotaMaxima))
                .addContainerGap(77, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel4)
                    .addComponent(jLabel7)
                    .addComponent(jLabelCodigoProva)
                    .addComponent(jLabelQuantidadeQuestoes)
                    .addComponent(jLabelNotaMaxima))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel8)
                    .addComponent(jLabel6)
                    .addComponent(jLabelDataCriacao)
                    .addComponent(jLabelQuantidadeCorrecoes)
                    .addComponent(jLabelNotaMinima))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel5)
                    .addComponent(jLabelDisciplina)
                    .addComponent(jLabelMediaProva))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 980, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 485, Short.MAX_VALUE)
                .addGap(39, 39, 39))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TelaCorrecoes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaCorrecoes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaCorrecoes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaCorrecoes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaCorrecoes().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabelCodigoProva;
    private javax.swing.JLabel jLabelDataCriacao;
    private javax.swing.JLabel jLabelDisciplina;
    private javax.swing.JLabel jLabelMediaProva;
    private javax.swing.JLabel jLabelNotaMaxima;
    private javax.swing.JLabel jLabelNotaMinima;
    private javax.swing.JLabel jLabelQuantidadeCorrecoes;
    private javax.swing.JLabel jLabelQuantidadeQuestoes;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTableCorrecoes;

    // End of variables declaration//GEN-END:variables
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import classes.Banco;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author ant
 */
public class TelaUsuarios extends javax.swing.JFrame {

    /**
     * Creates new form TelaUsuarios2
     */
    // lista de usuarios e objeto usuario ao qual a operação está sendo feita
    private ArrayList <Usuario> listaUsuarios;
    private Usuario usuario;
    
    private TableRowSorter <TableModel> tableSorter ;
    
    public TelaUsuarios() {
        initComponents();
        
        // carregando ArrayList
        usuario = new Usuario();
        listaUsuarios = new ArrayList<>();
        usuario.carregarLista(listaUsuarios);
        
        // ajustando jTablePesquisa
        carregarTable(jTablePesquisa);
        addValueChanged(jTablePesquisa);
        tableSorter = new TableRowSorter<>(jTablePesquisa.getModel());
	jTablePesquisa.setRowSorter(tableSorter);

    }
    
    
    private void carregarTable(JTable jTable){

        if(listaUsuarios != null){
            int tamanho = listaUsuarios.size();
            String dados[] = new String[2];
            DefaultTableModel model;         
            model = (DefaultTableModel) jTable.getModel();
            
            // limpando para evitar lixo ao atualizar a table
            int quantidadeLinhas = model.getRowCount();
            for(int i = 0; i < quantidadeLinhas; i++){
                model.removeRow(0);
            }
                                  
            for(int i = 0; i < tamanho; i++){
                dados[0] = listaUsuarios.get(i).nome;
                dados[1] = listaUsuarios.get(i).login;

                model.addRow(dados);
            }
            
            jTable.setModel( model );                       
        }      
    }
              
    
    private void addValueChanged(JTable jTable){
       
       // classe interna anônima
        jTable.getSelectionModel().addListSelectionListener(
            new javax.swing.event.ListSelectionListener() {               
                @Override
                public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                    
                    // preventing from double events
                    if(!evt.getValueIsAdjusting() ){
                        int index = jTablePesquisa.getSelectedRow();
                        if(index == -1){
                            return;
                        }    
                        index = tableSorter.convertRowIndexToModel(index);
                        
                        usuario = listaUsuarios.get(index);
                        jTextFieldNome.setText(usuario.nome);
                        jTextFieldLogin.setText(usuario.login);
                        jPasswordFieldSenha.setText(usuario.senha);
                                               
                        setComponents(false,true);

                    }
                    
                }
            }
        );     
    }
    
    private void setComponents(boolean alterando, boolean alterar){
        
        jButtonNovo.setEnabled(!alterando);
        jButtonLimpar.setEnabled(alterando);
        jButtonSalvar.setEnabled(alterando);
        jButtonCancelar.setEnabled(alterando);
        jButtonExcluir.setEnabled(alterar);
        jButtonAlterar.setEnabled(alterar);

 
        jTextFieldNome.setEnabled(alterando);
        jTextFieldLogin.setEnabled(alterando);
        jPasswordFieldSenha.setEnabled(alterando);
        jPasswordFieldConfirmarSenha.setEnabled(alterando);
    } 
    
    private void limparComponents(){
        jTextFieldNome.setText("");
        jTextFieldLogin.setText("");
        jPasswordFieldSenha.setText("");
        jPasswordFieldConfirmarSenha.setText("");

    }

    
    
    
    
    // classe interna 
    private class Usuario{
        int codigo;
        String login;
        String senha;
        String nome; 
        
        
        void carregarLista(ArrayList <Usuario> lista){
           
            // limpando lista para evitar lixo
           lista.clear();
           
            try{
                String sql = "SELECT * FROM tb_usuario";
                PreparedStatement pst = Banco.conexao.prepareStatement(sql);
                ResultSet rs = pst.executeQuery();
                
                while(rs.next()){
                    Usuario usuarioLocal = new Usuario();
                    usuarioLocal.codigo = rs.getInt("codigo");
                    usuarioLocal.login = rs.getString("login");
                    usuarioLocal.senha = rs.getString("senha");
                    usuarioLocal.nome = rs.getString("nome");
                    
                    lista.add(usuarioLocal);
                }
            }
            catch(SQLException e){
                e.printStackTrace();
            }
            
        }
        
        void limparCampos(){
            codigo = 0;
            login = null;
            senha = null;
            nome = null;
        }
        
        void salvar(){
            // verifica se o usuario carregado na memoria é novo ou é uma alteração
            if(usuario.codigo == 0){
                
                try{
                String sql = "INSERT INTO tb_usuario (login, senha, nome)"
                        + "VALUES (?,?,?)";
                PreparedStatement pst = Banco.conexao.prepareStatement(sql);
                pst.setString(1,login);
                pst.setString(2,senha);
                pst.setString(3,nome);
                pst.executeUpdate();
                }
                catch(SQLException e){
                    e.printStackTrace();
                }
            }
            else{
               try{
                    String sql = "UPDATE tb_usuario SET login = ?, senha = ?, nome = ? WHERE  = ?";
                    PreparedStatement pst = Banco.conexao.prepareStatement(sql);
                    pst.setString(1,login);
                    pst.setString(2,senha);
                    pst.setString(3,nome);
                    pst.setInt(4,codigo);
                    pst.executeUpdate();
                }
                catch(SQLException e){
                    e.printStackTrace();
                } 
                
            }
   
        }
        
        boolean excluir(){
            if(codigo == 0){
                JOptionPane.showMessageDialog(null, "Não foi possível excluir o aluno");
                return false;
            }
            try {
                String sql = "DELETE FROM tb_usuario WHERE codigo = ?";
                PreparedStatement pst = Banco.conexao.prepareStatement(sql);
                pst.setInt(1, codigo);
                pst.executeUpdate(); 
                return true;

            } 
            catch (SQLException e) {
                e.printStackTrace();
                return false;
                

            }
        }
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTextFieldNome = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jTextFieldLogin = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jPasswordFieldConfirmarSenha = new javax.swing.JPasswordField();
        jPasswordFieldSenha = new javax.swing.JPasswordField();
        jButtonSalvar = new javax.swing.JButton();
        jButtonNovo = new javax.swing.JButton();
        jButtonCancelar = new javax.swing.JButton();
        jButtonAlterar = new javax.swing.JButton();
        jButtonExcluir = new javax.swing.JButton();
        jButtonLimpar = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jTextFieldPesquisar = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTablePesquisa = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Cadastro de Usuários");
        setLocation(new java.awt.Point(0, 0));

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Dados"));

        jLabel1.setText("Nome");

        jTextFieldNome.setEnabled(false);

        jLabel2.setText("Login");

        jTextFieldLogin.setEnabled(false);

        jLabel3.setText("Senha");

        jLabel4.setText("Confirmar Senha");

        jPasswordFieldConfirmarSenha.setEnabled(false);

        jPasswordFieldSenha.setEnabled(false);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addGap(25, 25, 25)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextFieldNome, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 5, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPasswordFieldSenha, javax.swing.GroupLayout.DEFAULT_SIZE, 134, Short.MAX_VALUE)
                    .addComponent(jPasswordFieldConfirmarSenha))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextFieldNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(jPasswordFieldSenha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextFieldLogin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(jPasswordFieldConfirmarSenha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jButtonSalvar.setText("Salvar");
        jButtonSalvar.setEnabled(false);
        jButtonSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSalvarActionPerformed(evt);
            }
        });

        jButtonNovo.setText("Novo");
        jButtonNovo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonNovoActionPerformed(evt);
            }
        });

        jButtonCancelar.setText("Cancelar");
        jButtonCancelar.setEnabled(false);
        jButtonCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCancelarActionPerformed(evt);
            }
        });

        jButtonAlterar.setText("Alterar");
        jButtonAlterar.setEnabled(false);
        jButtonAlterar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAlterarActionPerformed(evt);
            }
        });

        jButtonExcluir.setText("Excluir");
        jButtonExcluir.setEnabled(false);
        jButtonExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonExcluirActionPerformed(evt);
            }
        });

        jButtonLimpar.setText("Limpar");
        jButtonLimpar.setEnabled(false);
        jButtonLimpar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonLimparActionPerformed(evt);
            }
        });

        jLabel5.setText("Pesquisar");

        jTextFieldPesquisar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextFieldPesquisarKeyReleased(evt);
            }
        });

        jTablePesquisa.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nome", "Login"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTablePesquisa);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButtonSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonAlterar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                    .addComponent(jLabel5)
                                    .addGap(18, 18, 18)
                                    .addComponent(jTextFieldPesquisar))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                    .addComponent(jButtonNovo, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jButtonCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jButtonLimpar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonSalvar)
                    .addComponent(jButtonAlterar)
                    .addComponent(jButtonExcluir))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonNovo)
                    .addComponent(jButtonCancelar)
                    .addComponent(jButtonLimpar))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jTextFieldPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(13, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSalvarActionPerformed
        //validando senha
        if(new String(jPasswordFieldSenha.getPassword() ).length() < 5){
            JOptionPane.showMessageDialog(null, "Senha precisa ser maior que 5 caracteres");
            jPasswordFieldSenha.setText("");
            jPasswordFieldConfirmarSenha.setText("");
            jPasswordFieldSenha.requestFocus();
            return;
        }

        if (! new String( jPasswordFieldSenha.getPassword() ).equals
            ( new String( jPasswordFieldConfirmarSenha.getPassword() ) ) ){
            JOptionPane.showMessageDialog(null, "Senhas não conferem");
            jPasswordFieldSenha.setText("");
            jPasswordFieldConfirmarSenha.setText("");
            jPasswordFieldSenha.requestFocus();
            return;
        }
        
        
        
        usuario.login = jTextFieldLogin.getText();
        usuario.senha = new String(jPasswordFieldSenha.getPassword());
        usuario.nome = jTextFieldNome.getText();
        
        usuario.salvar();  
        
        usuario.carregarLista(listaUsuarios);
        carregarTable(jTablePesquisa);
        setComponents(false,false);

    }//GEN-LAST:event_jButtonSalvarActionPerformed

    private void jButtonNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonNovoActionPerformed
        setComponents(true,false);
        limparComponents();
        jTablePesquisa.clearSelection();
        usuario.limparCampos();
    }//GEN-LAST:event_jButtonNovoActionPerformed

    private void jButtonCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCancelarActionPerformed
        setComponents(false,false);
        limparComponents();
        jTablePesquisa.clearSelection();
    }//GEN-LAST:event_jButtonCancelarActionPerformed

    private void jButtonAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAlterarActionPerformed
        setComponents(true,false);
        
        int indexTable = jTablePesquisa.getSelectedRow();
        if(indexTable == -1){
               return;
        }
        indexTable = tableSorter.convertRowIndexToModel(indexTable);       
        usuario = listaUsuarios.get(indexTable);

    }//GEN-LAST:event_jButtonAlterarActionPerformed

    private void jButtonExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonExcluirActionPerformed
       int index = jTablePesquisa.getSelectedRow();    
       if(index == -1){
           return;
       }
       index = tableSorter.convertRowIndexToModel(index);
       
       int opcao;       
        Object[]botoes = {"Sim", "Não"};
        opcao = JOptionPane.showOptionDialog(null,
                "Deseja mesmo excluir o cadastro " + listaUsuarios.get(index).nome, "Fechar",
                JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,
                null,botoes,botoes[0]);
        if(opcao == JOptionPane.NO_OPTION){
            return;
        }

       boolean exito = listaUsuarios.get(index).excluir();
       if(exito){
           usuario.carregarLista(listaUsuarios);
           jTablePesquisa.clearSelection();
           limparComponents();
           carregarTable(jTablePesquisa);
           setComponents(false,false);
           JOptionPane.showMessageDialog(null,"Exclusão realizada com sucesso");
       }
       else{
           JOptionPane.showMessageDialog(null,"Não foi possível realizar a exclusão");
       }
                  
    }//GEN-LAST:event_jButtonExcluirActionPerformed

    private void jButtonLimparActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLimparActionPerformed
        limparComponents();
    }//GEN-LAST:event_jButtonLimparActionPerformed

    private void jTextFieldPesquisarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldPesquisarKeyReleased
        
        String text = jTextFieldPesquisar.getText();	
        if(text.trim().length() != 0)
        {
            tableSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));    
        }
        else{
            tableSorter.setRowFilter(null);
        }
    }//GEN-LAST:event_jTextFieldPesquisarKeyReleased

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
            java.util.logging.Logger.getLogger(TelaUsuarios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaUsuarios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaUsuarios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaUsuarios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaUsuarios().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonAlterar;
    private javax.swing.JButton jButtonCancelar;
    private javax.swing.JButton jButtonExcluir;
    private javax.swing.JButton jButtonLimpar;
    private javax.swing.JButton jButtonNovo;
    private javax.swing.JButton jButtonSalvar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPasswordField jPasswordFieldConfirmarSenha;
    private javax.swing.JPasswordField jPasswordFieldSenha;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTablePesquisa;
    private javax.swing.JTextField jTextFieldLogin;
    private javax.swing.JTextField jTextFieldNome;
    private javax.swing.JTextField jTextFieldPesquisar;
    // End of variables declaration//GEN-END:variables
}

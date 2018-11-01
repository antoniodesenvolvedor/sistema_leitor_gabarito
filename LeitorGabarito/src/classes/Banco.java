/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author aaaaaaaaaaaaaaaaaaaa
 */
public class Banco {
    
    
    public static Connection conexao;
    
    public static void conectarBanco(String banco, String usuario, String senha)
    {
        
        try
        {
            Class.forName( "com.mysql.cj.jdbc.Driver");
            conexao = DriverManager.getConnection( "jdbc:mysql://localhost:3306/leitor_gabarito?useTimezone=true&serverTimezone=UTC" ,"root","1234" );
            System.out.println("Conexão bem sucedida" );
        }
        catch( SQLException erro )
        {
            erro.printStackTrace();           
        }
        catch( ClassNotFoundException erro )
        {
          erro.printStackTrace();
        }
    } // fim do método conectarBanco
    
}

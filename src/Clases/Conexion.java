/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author richard
 */
public class Conexion {
    private String db = "licoreria";
    private String user= "root";
    private String password= "0000";
    private String url= "jdbc:mysql://localhost/"+db;
    private Connection con= null;

    public Conexion() {
        try{
            //obtenemos el driver de para mysql
            Class.forName("com.mysql.jdbc.Driver");
            //obtenemos la conexión
            con = DriverManager.getConnection( this.url, this.user , this.password );         
         }catch(SQLException e){
            System.err.println( e.getMessage() );
         }catch(ClassNotFoundException e){
            System.err.println( e.getMessage() );
         }
    }
    
    public Connection getConexion()
   {
        return this.con;
   }
    
}

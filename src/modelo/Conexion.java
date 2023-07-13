/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;
import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author smitt
 */
public class Conexion {
    String url= "jdbc:mysql://localhost:3306/pelicula_db";
    String user = "root" , pass= "admin";
    Connection con;
    
    public Connection getConnection (){
        try{
          //Class.forName("com".mysql.jdbc.Driver);
          con = ( Connection) DriverManager.getConnection(url,user,pass);
        }
        catch (Exception e){
            System.out.println(""+ e);
        }
        return con;
    }
}

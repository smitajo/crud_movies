/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;
import java.awt.Image;
import java.awt.Toolkit;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author smitt
 */
public class PeliculaDAO {
     PreparedStatement ps;
    ResultSet rs;
    Connection con;
    Conexion conectar = new Conexion();
    Pelicula p = new Pelicula();
    
        public ArrayList<Pelicula> listar() {
    ArrayList<Pelicula> datos = new ArrayList<>();
    
    String sql = "SELECT * FROM pelicula";
 
    try{
        ps = conectar.getConnection().prepareStatement(sql);
        rs = ps.executeQuery();
        while(rs.next()){
            Pelicula p = new Pelicula();
            p.setId(rs.getInt(1));
            p.setNom(rs.getString(2));
            p.setDirector(rs.getString(3));
            p.setAño(rs.getString(4));
            p.setClasi(rs.getString(5));
             p.setImagen(rs.getBytes(6));
            datos.add(p);
        }
    }catch(SQLException ex){
        System.out.println(ex.getMessage());
    }catch(Exception ex){
        System.out.println(ex.getMessage());
    
    }
    return datos;
    }
    public int agregar (Pelicula peli, byte [] imagenData){
        int r=0;
        String sql="insert into pelicula(nombre,director,año,clasificacion, imagen) values(?,?,?,?,?)";
        try {
            con = conectar.getConnection();
            ps= con.prepareStatement(sql);
            ps.setString(1, peli.getNom());
            ps.setString(2, peli.getDirector());
            ps.setString(3, peli.getAño());
            ps.setString(4, peli.getClasi());
           ps.setBytes(5, imagenData);
            r =ps.executeUpdate();
            if(r==1){
                return 1;
            }
            else{
                return 0;
            }
            
            
        } catch (Exception e) {
            System.out.println(e);
        }
        return r;
        
        
    }
     public int actualizar(Pelicula peli) {
    int r = 0;
    String sql = "UPDATE pelicula SET Nombre=?, director=?, año=? , clasificacion=?,imagen=?  WHERE id=?";
        
    try {
        con = conectar.getConnection();
        ps = con.prepareStatement(sql);
        ps.setString(1, peli.getNom());
        ps.setString(2, peli.getDirector());
        ps.setString(3, peli.getAño());
        ps.setString(4, peli.getClasi());
        ps.setBytes(5, peli.getImagen());
        ps.setInt(6, peli.getId());
        r = ps.executeUpdate();
        
        if (r == 1) {
            return 1;
        } else {
            return 0;
        }
    } catch (Exception e) {
        System.out.println(e);
    }
    
    return 0;
}
     public int Delete (int id){
        int r=0;
        String sql = "delete from pelicula where id =" + id;
        try {
            con = conectar.getConnection();
            ps= con.prepareStatement(sql);
            r= ps.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
        return r;
    }
}

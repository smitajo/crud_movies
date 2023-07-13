/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author smitt
 */

public class Pelicula {
    int id;
    String nom;
    String director;
    String año;
    String clasi;
    private byte[] imagen;
    
    public Pelicula(){
        
    }
    

    public Pelicula(int id, String nom, String director, String año, String clasi, byte[] imagen) {
        this.id = id;
        this.nom = nom;
        this.director = director;
        this.año = año;
        this.clasi = clasi;
        this.imagen = imagen;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getAño() {
        return año;
    }

    public void setAño(String año) {
        this.año = año;
    }

    public String getClasi() {
        return clasi;
    }

    public void setClasi(String clasi) {
        this.clasi = clasi;
    }

    public byte[] getImagen() {
        return imagen;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }

    

    public Pelicula(byte[] imagen) {
        this.imagen = imagen;
    }
    

    

   
    
    
    
}

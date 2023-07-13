/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;
import modelo.Pelicula;
import modelo.PeliculaDAO;
import vista.vista;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel; 
import java.util.List;
import java.util.Set;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import vista.vista;

/**
 *
 * @author smitt
 */
public class Controlador   implements ActionListener{
    PeliculaDAO dao = new PeliculaDAO();
    Pelicula Pelicula = new Pelicula();
    vista vista = new vista();
    DefaultTableModel modelo = new DefaultTableModel();
    
    public Controlador (vista v) {
        this.vista=v;
        this.vista.btnListar.addActionListener(this);
        this.vista.btnAgregar.addActionListener(this);
        this.vista.btnEditar.addActionListener(this);
        this.vista.btnDelete.addActionListener(this);
        this.vista.btnActualizar.addActionListener(this);
        this.vista.btnImagen.addActionListener(this);
    }
    
    void nuevo(){
        vista.txtId.setText("");
        vista.txtNom.setText("");
        vista.txtDirector.setText("");
        vista.txtAño.setText("");
        vista.txtClasi.setText("");
        vista.txtruta.setText("");
        vista.txtNom.requestFocus();
    }
    void limpiarTabla(){
       int rowCount = modelo.getRowCount();
for (int i = rowCount - 1; i >= 0; i--) {
    modelo.removeRow(i);
}
    }
public void delete() {
    int fila = vista.table.getSelectedRow();
    if (fila == -1) {
        JOptionPane.showMessageDialog(vista, "Seleccione una fila");
    } else {
        int id = Integer.parseInt(vista.table.getValueAt(fila, 0).toString());
        int respuesta = JOptionPane.showConfirmDialog(vista, " eliminar la pelicula seleccionado?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (respuesta == JOptionPane.YES_OPTION) {
            int resultado = dao.Delete(id);
            if (resultado == 1) {
                JOptionPane.showMessageDialog(vista, "pelicula eliminada ");
                limpiarTabla();
            } else {
                JOptionPane.showMessageDialog(vista, "No se pudo eliminar la pelicula");
            }
        }
    }
}
public void add() {
   String nom = vista.txtNom.getText();
String director = vista.txtDirector.getText();
String año = vista.txtAño.getText();
String clasi = vista.txtClasi.getText();

File file = new File(vista.txtruta.getText());
byte[] imagenData = new byte[(int) file.length()];
try (FileInputStream fis = new FileInputStream(file);
     ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
    int readBytes;
    while ((readBytes = fis.read(imagenData, 0, imagenData.length)) != -1) {
        bos.write(imagenData, 0, readBytes);
    }
    imagenData = bos.toByteArray();
} catch (IOException e) {
    e.printStackTrace();
}

Pelicula.setNom(nom);
Pelicula.setDirector(director);
Pelicula.setAño(año);
Pelicula.setClasi(clasi);
Pelicula.setImagen(imagenData);

int r = dao.agregar(Pelicula, imagenData);
         
    if(r ==1){
        JOptionPane.showMessageDialog(vista, "pelicula agregada");
    } else{
        JOptionPane.showMessageDialog(vista, "Error");
    }
    limpiarTabla();
}
public void actualizar(){
       
       if (vista.txtId.getText().equals("")){
           JOptionPane.showMessageDialog(vista, "Se sugiere que el usuario seleccione " + "La opcion \"Editar\" para solucionar el problema.");
       }else{
           int id = Integer.parseInt(vista.txtId.getText().trim());
           String nom = vista.txtNom.getText();
           String director = vista.txtDirector.getText();
           String año = vista.txtAño.getText();
           String clasi = vista.txtClasi.getText();
           File ruta = new File(vista.txtruta.getText());
           Pelicula.setId(id);
           Pelicula.setNom(nom);
           Pelicula.setDirector(director);
           Pelicula.setAño(año);
           Pelicula.setClasi(clasi);
           
           int r = dao.actualizar(Pelicula);
           if (r==1){
               JOptionPane.showMessageDialog(vista, " pelicula actualizado con éxito");
           }else{
               JOptionPane.showMessageDialog(vista, "Error");
           }
           
       }
       limpiarTabla();
   }
    void centrarCeldas(JTable table){
    DefaultTableCellRenderer tcr= new DefaultTableCellRenderer();
    tcr.setHorizontalAlignment(SwingConstants.CENTER);
    for ( int i = 0; i< vista.table.getColumnCount();i++){
        table.getColumnModel().getColumn(i).setCellRenderer(tcr);
    }
}
  public void listar(JTable table){
        
        table.setDefaultRenderer(Object.class, new Render());
        DefaultTableModel dt = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        dt.addColumn("id");
        dt.addColumn("nombre");
        dt.addColumn("director");
        dt.addColumn("año");
        dt.addColumn("clasificacion");
        dt.addColumn("imagen");

        dao = new PeliculaDAO();
        Pelicula p = new Pelicula();
        ArrayList<Pelicula> list = dao.listar();

        if(list.size() > 0){
            for(int i=0; i<list.size(); i++){
                Object fila[] = new Object[6];
                p = list.get(i);
                fila[0] = p.getId();
                fila[1] = p.getNom();
                fila[2] = p.getDirector();
                fila[3] = p.getAño();
                fila[4] = p.getClasi();
                try{
                    byte[] bi = p.getImagen();
                    BufferedImage image = null;
                    InputStream in = new ByteArrayInputStream(bi);
                    image = ImageIO.read(in);
                    ImageIcon imgi = new ImageIcon(image.getScaledInstance(60, 60, 0));
                    fila[5] = new JLabel(imgi);

                }catch(Exception ex){
                    fila[5] = new JLabel("No imagen");
                }
                dt.addRow(fila);
            }
            table.setModel(dt);
            table.setRowHeight(60);
        }
    }

    @Override
    public void actionPerformed (ActionEvent e) {
    if (e.getSource() == vista.btnListar){
        limpiarTabla();
        listar(vista.table);
        nuevo();
}
    if(e.getSource()== vista.btnImagen){
        
    }
    if ( e.getSource()== vista.btnAgregar){
    add();
    listar(vista.table);
    nuevo();
    }
    if(e.getSource()== vista.btnEditar){
        int fila = vista.table.getSelectedRow();
        if(fila == -1){
            JOptionPane.showMessageDialog(vista, "seleccione fila");
        } else{
            int id = Integer.parseInt((String) vista.table.getValueAt(fila, 0).toString());
            String nom = (String) vista.table.getValueAt(fila, 1);
            String director = (String) vista.table.getValueAt(fila, 2);
            String año = (String) vista.table.getValueAt(fila,3);
            String clasi = (String) vista.table.getValueAt(fila,4);
            
            
            vista.txtId.setText(" " + id);
            vista.txtNom.setText(nom);
            vista.txtDirector.setText(director);
            vista.txtAño.setText(año);
            vista.txtClasi.setText(clasi);
           
            
            
        }
    }
    if (e.getSource() == vista.btnActualizar){
        actualizar();
        listar(vista.table);
        nuevo();
    }
    if (e.getSource()== vista.table){
        delete();
        listar(vista.table);
        nuevo();
    
    }
    if (e.getSource() == vista.btnDelete){
      delete();
    listar(vista.table);
    nuevo();
    }
}
}

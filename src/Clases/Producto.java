/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JButton;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author richard
 */
public class Producto extends Conexion{

    public Producto() {
    }
    
    public boolean nuevoProducto(String nombreProducto, String descripcion, float gradoAlcohol, float contenido, String fabricacion){
        
        String q= "INSERT INTO producto(nombre_prod, descripcion, grados_alco, contenido, fabricacion) VALUES('"+nombreProducto+"', '"+
                  descripcion+"', "+gradoAlcohol+", "+contenido+", '"+fabricacion+"')";
        
        //se ejecuta la consulta
        try {
            PreparedStatement pstm = this.getConexion().prepareStatement(q);
            pstm.execute();
            pstm.close();
            return true;
        }catch(SQLException e){
            System.err.println( e.getMessage() );
            return false;
        }            
    }
    
    public boolean updateProducto(String id_prod, String nombreProducto, String descripcion, float gradoAlcohol, float contenido, String fabricacion){
        
        String q= "UPDATE producto SET nombre_prod='"+nombreProducto+"', descripcion='"+descripcion+"', grados_alco="+
                   gradoAlcohol+", contenido="+contenido+", fabricacion='"+fabricacion+"' WHERE id_prod="+id_prod;
        
        try{
            PreparedStatement pstm= this.getConexion().prepareStatement(q);
            pstm.execute();
            pstm.close();
            return true;
        }catch(SQLException e){
            System.err.println(e.getMessage());
            return false;
        }
    }    
    
    public static AbstractTableModel gestionProducto(){
        
        AbstractTableModel model= new AbstractTableModel() {
            private Conexion con= new Conexion();
            private ArrayList<HashMap> productos= new ArrayList<>();
            private Object [][] datos= this.contenido();
            private String [] nameColumn={"id", "Nombre", "Existencia", "status", ""};
            
            // <editor-fold defaultstate="collapsed" desc="Metodos privados">
            private Object [][] contenido(){
                boolean activo= true;
                boolean inactivo= false;
                try{
                    String sql="SELECT * FROM producto ORDER BY id_prod";
                    PreparedStatement pstm= con.getConexion().prepareStatement(sql);
                    ResultSet res= pstm.executeQuery();
                    while(res.next()){
                        HashMap map= new HashMap();
                        ResultSetMetaData meta= res.getMetaData();
                        for (int i = 1, j = meta.getColumnCount(); i <=j; i++) {
                            map.put(meta.getColumnLabel(i).toLowerCase(), res.getString(i));
                        }
                        productos.add(map);
                    }
                    res.close();
                }catch(SQLException ex){
                    System.err.println(ex.getMessage());
                }
                Object [][] data= new Object[productos.size()][5];
                int i= 0;
                for(HashMap pro : productos){
                    String [] col={pro.get("id_prod").toString(), pro.get("nombre_prod").toString(), pro.get("existencia").toString(), pro.get("status").toString(), ""};
                    for (int j = 0; j < data[i].length; j++) {
                        if(j==3){
                            if(Integer.valueOf(col[j])==1){
                                data[i][j]= activo;
                            }
                            else{
                                data[i][j]= inactivo;
                            }
                        }
                        else{
                            if(j==4){
                                data[i][j]= new JButton();
                            }
                            else{
                                data[i][j]= col[j];
                            }
                        }
                    }
                    i++;
                }
                return data;
            }
            
            private void setEstado(String id_prod, String status){
                try{
                    String sql= "UPDATE producto SET status= '"+status+"' WHERE id_prod='"+id_prod+"'";
                    PreparedStatement pstm= this.con.getConexion().prepareStatement(sql);
                    pstm.execute();
                    pstm.close();
                }catch(SQLException e){
                    System.err.println(e.getMessage());
                }
            }
            
            //</editor-fold>
            
            @Override
            public int getRowCount() {
                return this.datos.length;
            }

            @Override
            public int getColumnCount() {
                return this.nameColumn.length;
            }

            @Override
            public Object getValueAt(int rowIndex, int columnIndex) {
                return this.datos[rowIndex][columnIndex];
            }
            
            @Override
            public String getColumnName(int columnIndex){
                return this.nameColumn[columnIndex];
            }
            
            @Override
            public Class<?> getColumnClass(int c){
                return this.datos[0][c].getClass();
            }
            
            @Override
            public void setValueAt(Object aValue, int rowIndex, int columnIndex){
                if(columnIndex == 3){
                    boolean value= Boolean.parseBoolean(aValue.toString());
                    String estado= "0";
                    if(value == true){
                        estado= "1";
                    }
                    this.setEstado(this.datos[rowIndex][0].toString(), estado);
                    this.datos[rowIndex][columnIndex]= value;
                    // Disparamos el Evento TableDataChanged (La tabla ha cambiado)
                    //fireTableCellUpdated(rowIndex, columnIndex);
                }
            }
            
            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                if(columnIndex>=3){
                    return true;
                }
                else{
                    return false;
                }
            }
        };
       return model;
    }
    
    public static String[] searchProducto(String dato){
        Conexion con= new Conexion();
        String [] producto= null;
        String q="SELECT * FROM producto WHERE nombre_prod='"+dato+"'";
        
        try{
            PreparedStatement pstm;
            
            try{
                int id= Integer.parseInt(dato);
                pstm= con.getConexion().prepareStatement("SELECT * FROM producto WHERE id_prod="+id);
            }catch(NumberFormatException ex){
                pstm=con.getConexion().prepareStatement(q);
            }
            
            ResultSet res= pstm.executeQuery();
            if(res.next()){
                producto= new String[8];
                producto[0]= res.getString("id_prod");
                producto[1]= res.getString("nombre_prod");
                producto[2]= res.getString("descripcion");
                producto[3]= res.getString("grados_alco");
                producto[4]= res.getString("contenido");
                producto[5]= res.getString("existencia");
                producto[6]= res.getString("fabricacion");
                producto[7]= res.getString("status");
            }
            res.close();
            
        }catch(SQLException e){
            System.err.println(e.getMessage());
        }
        return producto;
    }
    
    public static String[] nameProductos(){
        Conexion con= new Conexion();
        int registros= 0;
        String q= "SELECT count(*) as total FROM producto WHERE status='1'";
        try{
            PreparedStatement pstm= con.getConexion().prepareStatement(q);
            ResultSet res= pstm.executeQuery();
            res.next();
            registros= res.getInt("total");
            res.close();
        }
        catch(SQLException e){
            System.err.println(e.getMessage());
        }
        
        String [] productos= new String[registros];
        
        try{
            PreparedStatement pstm= con.getConexion().prepareStatement("SELECT nombre_prod FROM producto WHERE status='1'");
            ResultSet res= pstm.executeQuery();
            int i=0;
            while(res.next()){
                productos[i]= res.getString("nombre_prod");
                i++;
            }
            res.close();
        }catch(SQLException ex){
            System.err.println(ex.getMessage());
        }
        return productos;
    }
}

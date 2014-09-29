package Clases;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;

public class Proveedor extends Conexion{
    private String rif;
    private String razonSocial;
    
    public Proveedor(String rif, String razonSocial){
        this.rif= rif;
        this.razonSocial= razonSocial;
    }
    
    public boolean insertProveedor(){
        boolean ok= false;
        try{
            String sql="INSERT INTO proveedor VALUES('"+this.getRif()+"', '"+this.getRazonSocial()+"')";
            try (PreparedStatement pstm = this.getConexion().prepareStatement(sql)) {
                pstm.execute();
                pstm.close();
                ok= true;
            }
        }catch(SQLException e){
            System.err.println(e.getMessage());
        }
        return ok;
    }

    public void setRif(String rif) {
        try{
            String sql="UPDATE proveedor SET rif_prov='"+rif+"' WHERE  rif_prov= '"+this.rif+"'";
            PreparedStatement pstm= this.getConexion().prepareStatement(sql);
            pstm.execute();
            pstm.close();
            this.rif = rif;
        }catch(SQLException e){
            System.err.println(e.getMessage());
        }
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getRif() {
        return rif;
    }

    public String getRazonSocial() {
        return razonSocial;
    }
    
    public static Proveedor searchProveedor(String rif){
        Proveedor prov= null;
        Conexion con= new Conexion();
        try{
            String sql= "SELECT * FROM proveedor WHERE rif_prov='"+rif+"'";
            PreparedStatement pstm= con.getConexion().prepareStatement(sql);
            try (ResultSet res = pstm.executeQuery()) {
                if(res.next()){
                    prov= new Proveedor(res.getString("rif_prov"), res.getString("rs_prov"));
                }
            }
        }catch(SQLException e){
            System.err.println(e.getMessage());
        }
        return prov;
    }
    
    public static DefaultTableModel gestionProveedores(){
        Conexion con= new Conexion();
        String [] columnNames= {"RIF", "Razón social"};
        int registros= 0;
               try{
                   String sql= "SELECT COUNT(*) AS total FROM proveedor";
                   PreparedStatement pstm= con.getConexion().prepareStatement(sql);
                   try (ResultSet res = pstm.executeQuery()) {
                        registros= res.getInt("total");
                    }
               }catch(SQLException e){
                   System.err.println(e.getMessage());
               }
               String[][] datos= new String[registros][columnNames.length];
               try{
                   String sql= "SELECT * FROM proveedor";
                   PreparedStatement pstm= con.getConexion().prepareStatement(sql);
                   try (ResultSet res = pstm.executeQuery()) {
                        int i=0;
                        while(res.next()){
                            datos[i][0]=res.getString("rif_prov");
                            datos[i][1]=res.getString("rs_prov");
                            i++;
                        }
                    }
               }catch(SQLException ex){
                   System.err.println(ex.getMessage());
               }
        return new DefaultTableModel(datos, columnNames);
    }
    
}

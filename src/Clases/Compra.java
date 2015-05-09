/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author richard
 */
public class Compra extends Conexion{
    private String numFac;
    private Date fechaFac;
    private String rifProv;
    private String [][] items;
    
    public Compra(){}

    private Compra(String numFac, Date fechaFac, String rifProv, String[][] items) {
        this.numFac = numFac;
        this.fechaFac = fechaFac;
        this.rifProv= rifProv;
        this.items = items;
    }
    
    public static Compra existeFact(String numFact){
        Conexion con= new Conexion();
        Compra compra=null;
        Date fecha_compra= null;
        String rif_prov=null;
        int resultado= 0;
        String sql="SELECT c.fecha_compra, c.rif_prov, COUNT(*) AS registros FROM entrada e,"
                  +" fact_compra c WHERE e.fact_ent='"+numFact+"' AND e.fact_ent=c.num_fact";
        
        try{
            PreparedStatement pstm= con.getConexion().prepareStatement(sql);
            ResultSet res= pstm.executeQuery();
            if(res.next()){
                resultado= res.getInt("registros");
                fecha_compra= res.getDate("fecha_compra");
                rif_prov= res.getString("rif_prov");
            }
            res.close();
        }catch(SQLException e){
            System.err.println(e.getMessage());
        }
            if(resultado > 0){
                String [][] datos= new String[resultado][4];
                try{
                    sql=   "SELECT e.id_prod, p.nombre_prod, e.cant_compra, e.ttl_cont "
                          +"FROM entrada e, producto p, fact_compra c "
                          +"WHERE e.fact_ent= '"+numFact+"' "
                          +"AND e.fact_ent=c.num_fact AND e.id_prod=p.id_prod";
                    
                    PreparedStatement pstm= con.getConexion().prepareStatement(sql);
                    ResultSet res= pstm.executeQuery();
                    int i= 0;
                    while(res.next()){
                        datos[i][0]= res.getString("id_prod");
                        datos[i][1]= res.getString("nombre_prod");
                        datos[i][2]= res.getString("cant_compra");
                        datos[i][3]= res.getString("ttl_cont");
                        i++;
                    }
                    res.close();
                    compra= new Compra(numFact, fecha_compra, rif_prov, datos);
                }catch(SQLException e){
                    System.err.println(e.getMessage());
                }
            }
        
        return compra;
    }
    
    public boolean insertCompra(String numFact, Date fecha, String rifProv, String [][] items){
        boolean ok= false;
        String sql= "INSERT INTO fact_compra VALUES('"+numFact+"', '"+new java.sql.Date(fecha.getTime())+"', '"+rifProv+"')";
        try{
            PreparedStatement pstm= this.getConexion().prepareStatement(sql);
            pstm.execute();            
            pstm.close();
            ok= true;
        }catch(SQLException e){
            System.err.println(e.getMessage());            
        }
        if(ok){
            sql= "INSERT INTO entrada VALUES(?,?,?,?)";
            String sql1= "UPDATE producto SET existencia=existencia+? WHERE id_prod=?";
            try{
                PreparedStatement pstm= this.getConexion().prepareStatement(sql);
                PreparedStatement pstm1= this.getConexion().prepareStatement(sql1);
                for (int i = 0; i < items.length; i++) {
                    for (int j=0; j< items[i].length; j++) {
                        pstm.setString(j+1, items[i][j]);
                        pstm1.setString(1, items[i][2]);
                        pstm1.setString(2, items[i][1]);
                    }
                    pstm.execute();
                    pstm1.execute();
                }
                pstm.close();
                pstm1.close();
            }catch(SQLException e){
                System.err.println(e.getMessage());
            }
        }
        return ok;
    }
    
    public boolean setItemsCompra(String [][] data){
        boolean ok= false;
        String [][] nuevosItems= data;
        try{
            PreparedStatement pstm = this.getConexion().prepareStatement("");
            for (int i=0; i<this.getItems().length; i++) {
                String [] itemDB=this.getItems()[i];
                String [] itemNuevo= nuevosItems[i];
                if(!itemDB[0].equals(itemNuevo[1])){
                    String sql  = "UPDATE producto SET existencia=existencia-"+itemDB[2]+" WHERE id_prod='"+itemDB[0]+"'";
                    pstm.execute(sql);
                    String sql1 = "DELETE FROM entrada WHERE fact_ent='"+this.getNumFac()+"' AND id_prod='"+itemDB[0]+"'";
                    pstm.execute(sql1);
                    String sql2 = "INSERT INTO entrada VALUES(?, ?, ?, ?)";
                    pstm= this.getConexion().prepareStatement(sql2);                    
                    for(int j=0; j<itemNuevo.length; j++){
                        pstm.setString(j+1, itemNuevo[j]);
                    }
                    pstm.execute();
                    String sql3 = "UPDATE producto SET existencia=existencia+"+itemNuevo[2]+" WHERE id_prod='"+itemNuevo[1]+"'";
                    pstm.execute(sql3);
                    
                    ok=true;
                }
                else{
                    String update= "UPDATE entrada SET ";
                    if(!itemDB[2].equals(itemNuevo[2])){
                        update+="cant_compra="+itemNuevo[2]+", ttl_cont="+itemNuevo[3];
                    }
                    if(!update.equals("UPDATE entrada SET ")){
                        
                        int cantidad= Integer.valueOf(itemNuevo[2]) - Integer.valueOf(itemDB[2]);
                        String sql3 = "UPDATE producto SET existencia=existencia+"+cantidad+" WHERE id_prod='"+itemNuevo[1]+"'";
                        
                        pstm=this.getConexion().prepareStatement(update+" WHERE fact_ent='"+this.numFac+"' AND id_prod='"+itemDB[0]+"'");
                        pstm.execute();
                        pstm.execute(sql3);
                        
                        ok= true;
                    }
                }
            }
            
            if(this.getItems().length < nuevosItems.length){
                String insert= "INSERT INTO entrada VALUES(?, ?, ?, ?)";
                String sql3 = "UPDATE producto SET existencia=existencia+? WHERE id_prod=?";
                pstm= this.getConexion().prepareStatement(insert);
                PreparedStatement pstm1=this.getConexion().prepareStatement(sql3);
                for (int i = this.getItems().length; i < nuevosItems.length; i++) {
                    String [] item= nuevosItems[i];
                    for (int j = 0; j < 4; j++) {
                        pstm.setString(j+1, item[j]);
                        pstm1.setString(1, item[2]);
                        pstm1.setString(2, item[1]);
                    }
                    pstm.execute();
                    pstm1.execute();
                }
                ok= true;
                pstm1.close();
            }
            pstm.close();
        }catch(SQLException e){
            System.err.println(e.getMessage());
        }
        return ok;
    }

    public String getNumFac() {
        return numFac;
    }

    public Date getFechaFac() {
        return fechaFac;
    }

    public String[][] getItems() {
        return items;
    }

    public String getRifProv() {
        return rifProv;
    }

    public void setRifProv(String rifProv) {
        this.rifProv = rifProv;
        try{
            String sql= "UPDATE fact_compra SET rif_prov='"+rifProv+"' WHERE num_fact='"+this.numFac+"'";
            PreparedStatement pstm= this.getConexion().prepareStatement(sql);
            pstm.execute();
            pstm.close();
        }catch(SQLException e){
            System.err.println(e.getMessage());
        }
    }

    public void setFechaFac(Date fechaFac) {
        this.fechaFac = fechaFac;
        try{
            String sql="UPDATE fact_compra SET fecha_compra='"+new java.sql.Date(fechaFac.getTime())+"' WHERE num_fact='"+this.numFac+"'";
            PreparedStatement pstm= this.getConexion().prepareStatement(sql);
            pstm.execute();
            pstm.close();
        }catch(SQLException e){
            System.err.println(e.getMessage());
        }
    }
    
    public static DefaultTableModel getCompras(){
        Conexion con= new Conexion();
        String [] columnName= {"Num. Fact.", "Fecha Compra", "Proveedor", ""};
        int registros=0;
        try{
            String sql="SELECT COUNT(*) AS registros FROM fact_compra";
            PreparedStatement pstm= con.getConexion().prepareStatement(sql);
            ResultSet res= pstm.executeQuery();
            if(res.next()){
                registros= res.getInt("registros");
            }
            res.close();
        }catch(SQLException e){
            System.err.println(e.getMessage());
        }
        Object [][] datos= new Object[registros][columnName.length];
        
        try{
            String sql="SELECT fc.num_fact, fc.fecha_compra, p.rs_prov FROM fact_compra fc, proveedor p WHERE fc.rif_prov = p.rif_prov ORDER BY fc.fecha_compra";
            PreparedStatement pstm= con.getConexion().prepareStatement(sql);
            ResultSet res= pstm.executeQuery();
            int i=0;
            while(res.next()){
                datos[i][0]=res.getString("num_fact");
                datos[i][1]=res.getString("fecha_Compra");
                datos[i][2]=res.getString("rs_prov");
                
                i++;
            }
            res.close();
        }catch(SQLException ex){
            System.err.println(ex.getMessage());
        }
        
        return new DefaultTableModel(datos, columnName);
    }
}

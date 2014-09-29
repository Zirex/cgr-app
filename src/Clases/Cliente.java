package Clases;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Cliente extends Conexion{
    private String rifCliente;
    private String rsCliente;
    private String dirCliente;
    
    public Cliente(String rifCliente, String rsCliente, String dirCliente){
        this.rifCliente= rifCliente;
        this.rsCliente= rsCliente;
        this.dirCliente= dirCliente;
    }
    
    public boolean insertCliente(){
        boolean ok= false;
        try{
            String sql= "INSERT INTO cliente VALUES('"+this.rifCliente+"', '"+this.rsCliente+"', '"+this.dirCliente+"')";
            PreparedStatement pstm= this.getConexion().prepareStatement(sql);
            pstm.execute();
            pstm.close();
            ok= true;
        }catch(SQLException e){
            System.err.println(e.getMessage());
        }
        return ok;
    }
    
    public static Cliente searchCliente(String rifCliente){
        Conexion con= new Conexion();
        Cliente cl= null;
        try{
            String sql="SELECT * FROM cliente WHERE rif_clie='"+rifCliente+"'";
            PreparedStatement pstm= con.getConexion().prepareStatement(sql);
            ResultSet res= pstm.executeQuery();
            if(res.next()){
                cl= new Cliente(res.getString("rif_clie"),
                                res.getString("rs_clie"),
                                res.getString("dir_clie"));
            }
            res.close();
        }catch(SQLException e){
            System.err.println(e.getMessage());
        }
        return cl;
    }

    public String getRifCliente() {
        return rifCliente;
    }

    public String getRsCliente() {
        return rsCliente;
    }

    public String getDirCliente() {
        return dirCliente;
    }

    public void setRifCliente(String rifCliente) {
        try{
            String sql="UPDATE cliente SET rif_clie='"+rifCliente+"' WHERE rif_clie='"+this.rifCliente+"'";
            PreparedStatement pstm= this.getConexion().prepareStatement(sql);
            pstm.execute();
            pstm.close();
            this.rifCliente = rifCliente;
        }catch(SQLException e){
            System.err.println(e.getMessage());
        }
    }

    public void setRsCliente(String rsCliente) {
        try{
            String sql="UPDATE cliente SET rs_clie='"+rsCliente+"' WHERE rif_clie='"+this.rifCliente+"'";
            PreparedStatement pstm= this.getConexion().prepareStatement(sql);
            pstm.execute();
            pstm.close();
            this.rsCliente = rsCliente;
        }catch(SQLException e){
            System.err.println(e.getMessage());
        }
    }

    public void setDirCliente(String dirCliente) {
        try{
            String sql="UPDATE cliente SET dir_clie='"+dirCliente+"' WHERE rif_clie='"+this.rifCliente+"'";
            PreparedStatement pstm= this.getConexion().prepareStatement(sql);
            pstm.execute();
            pstm.close();
            this.dirCliente = dirCliente;
        }catch(SQLException e){
            System.err.println(e.getMessage());
        }
    }
    
}

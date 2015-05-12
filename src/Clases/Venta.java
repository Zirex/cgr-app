/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import Vista.GuiPrincipal;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author richard
 */
public class Venta extends Conexion{
    private String nunFact;
    private Date fecha;
    private String rifCliente;
    private String[][] itemsVenta;
    
    private HashSet<itemVenta> listaItems= new HashSet<>();
    private ArrayList<String> productosNegativos= new ArrayList<>();
    
    // <editor-fold defaultstate="collapsed" desc="Clase itemVenta">
    private class itemVenta{
        private String factSal;
        private String facEnt;
        private String idProd;
        private int cantVenta;
        private float ttlCont;

        public itemVenta(String factSal, String facEnt, String idProd, int cantVenta, float ttlCont) {
            this.factSal = factSal;
            this.facEnt = facEnt;
            this.idProd = idProd;
            this.cantVenta = cantVenta;
            this.ttlCont = ttlCont;
        }

        public String getFactSal() {
            return factSal;
        }

        public String getFacEnt() {
            return facEnt;
        }

        public String getIdProd() {
            return idProd;
        }

        public int getCantVenta() {
            return cantVenta;
        }

        public float getTtlCont() {
            return ttlCont;
        }

        public void setCantVenta(int cantVenta) {
            this.cantVenta = cantVenta;
        }

        public void setTtlCont(float ttlCont) {
            this.ttlCont = ttlCont;
        }
        
    }
    // </editor-fold>

    public Venta(String numFact){
        this.nunFact= numFact;
    }
    
    private Venta(String nunFact, Date fehca, String rifCliente, String[][] itemsVenta) {
        this.nunFact = nunFact;
        this.fecha = fehca;
        this.rifCliente= rifCliente;
        this.itemsVenta = itemsVenta;
    }

    public String getNunFact() {
        return nunFact;
    }

    public Date getFecha() {
        return fecha;
    }

    public String getRifCliente() {
        return rifCliente;
    }

    public String[][] getItemsVenta() {
        return itemsVenta;
    }
    
    private void adicionarItemVenta(String idProd, int cantidad){
        this.productosNegativos.clear();
        if(!GuiPrincipal.listaFactCompProdExistentes.isEmpty()){
            // booleano para comprobar si existe el producto en la lista.
            boolean existeProducto= false;
            for (HashMap compra : GuiPrincipal.listaFactCompProdExistentes) {
                if(compra.containsValue(idProd)){
                    existeProducto = true;
                    int existencia= Integer.valueOf(compra.get("diferencia")+""); 
                    int diferencia= existencia - cantidad;
                    int ventaAnterior= Integer.parseInt(compra.get("cant_venta")+"");
                    float ttlLitros= Float.valueOf(compra.get("ttl_litros")+"");
                    float contenido= ttlLitros/ventaAnterior; 
                    itemVenta venta= new itemVenta(String.valueOf(this.nunFact),
                                                   compra.get("num_fact")+"",
                                                   idProd,
                                                   existencia,
                                                   contenido);
                    if(diferencia < 0){
                        this.buscarBDFC(idProd, cantidad);
                        if(this.productosNegativos.isEmpty()){
                            this.listaItems.add(venta);
                            GuiPrincipal.listaFactCompProdExistentes.remove(compra);
                        }
                        break;
                    }
                    else if(diferencia == 0){
                        venta.setTtlCont(existencia*contenido);
                        this.listaItems.add(venta);
                        GuiPrincipal.listaFactCompProdExistentes.remove(compra);
                        break;
                    }
                    else{
                        venta.setCantVenta(cantidad);
                        venta.setTtlCont(cantidad*contenido);
                        this.listaItems.add(venta);
                        break;
                    }
                }
            }
            if(!existeProducto)
                this.buscarBDFC(idProd, cantidad);
            
        }
        else{
            this.buscarBDFC(idProd, cantidad);
        }
    }
    
    private void buscarBDFC(String idProd, int cantidad){
        ArrayList<HashMap> lct= new ArrayList<>();
        try{
            CallableStatement proc= this.getConexion().prepareCall("CALL ultimasFacturas(?,?)");
            proc.setString("idProd", idProd);
            proc.setInt("cantidadVenta", cantidad);
            ResultSet res= proc.executeQuery();
            while(res.next()){
                HashMap map= new HashMap();
                ResultSetMetaData rsmd= res.getMetaData();
                for (int i = 1, l=rsmd.getColumnCount(); i <= l; i++) {
                    map.put(rsmd.getColumnLabel(i).toLowerCase(), res.getString(i));
                }
                lct.add(map);
            }
            res.close();
        }catch(SQLException e){
            System.err.println(e.getMessage());
        }
        
        if(!lct.isEmpty()){
            int size= lct.size()-1;
            int diferencia= Integer.valueOf(lct.get(0).get("diferencia")+"");
            if(diferencia < 0){
                this.productosNegativos.add(lct.get(0).get("nombre_prod")+"");
            }
            else{
                for (HashMap compra : lct) {
                    itemVenta venta= new itemVenta(this.nunFact, 
                                                   compra.get("num_fact")+"", 
                                                   compra.get("id_prod")+"", 
                                                   Integer.valueOf(compra.get("cant_venta")+""),
                                                   Float.valueOf(compra.get("ttl_litros")+""));
                    this.listaItems.add(venta);
                }
                GuiPrincipal.listaFactCompProdExistentes.add(lct.get(size));
            }
        }
    }

    public ArrayList<String> getProductosNegativos(String[][] itemsVenta) {
        for (String[] item : itemsVenta) {
            this.adicionarItemVenta(item[0], Integer.valueOf(item[2]));
        }
        this.productosNegativos.toString();
        return this.productosNegativos;
    }
    
    public boolean insertarVenta(String numFact, Date fecha, String rifCliente, String[][] itemsVenta){
        boolean ok= false;
        
        try{
            String sql= "INSERT INTO fact_venta VALUES('"+numFact+"', '"+new java.sql.Date(fecha.getTime())+"', '"+rifCliente+"')";
            PreparedStatement pstm= this.getConexion().prepareStatement(sql);
            pstm.execute(sql);
            pstm.close();
            this.fecha= fecha;
            this.rifCliente=rifCliente;
            ok= true;
        }catch(SQLException e){
            System.err.println(e.getMessage());
        }
        if(ok){
            try{
                String sql="INSERT INTO salida VALUES(?,?,?,?,?)";
                Statement s= this.getConexion().createStatement();
                try (PreparedStatement pstm = this.getConexion().prepareStatement(sql)) {
                    for (itemVenta item: this.listaItems) {
                        pstm.setString(1, item.getFactSal());
                        pstm.setString(2, item.getFacEnt());
                        pstm.setString(3, item.getIdProd());
                        pstm.setInt(4, item.getCantVenta());
                        pstm.setFloat(5, item.getTtlCont());
                        
                        pstm.execute();
                        String sql1="UPDATE producto SET existencia=existencia -"+item.getCantVenta()+" WHERE id_prod="+item.getIdProd();
                        s.execute(sql1);
                    }
                    pstm.close();
                    s.close();
                    this.itemsVenta= itemsVenta;
                }
            }catch(SQLException ex){
                System.err.println(ex.getMessage());
                ok=false;
            }
        }
        
        return ok;
    }
    
    public static Venta searchVenta(String numFact){
        Venta venta= null;
        Conexion con= new Conexion();
        Date fechaVenta= null;
        String rifCliente= null;
        int resultado= 0;
        try{
            String sql="SELECT fecha_venta, rif_clie "
                    + "FROM fact_venta WHERE id_fact='"+numFact+"'";
            Statement st= con.getConexion().createStatement();
            ResultSet res= st.executeQuery(sql);
            if(res.next()){
                fechaVenta= res.getDate("fecha_venta");
                rifCliente= res.getString("rif_clie");
                CallableStatement proc= con.getConexion().prepareCall("CALL getItems(?)");
                proc.setString(1, numFact);
                ResultSet res2= proc.executeQuery();
                sql="SELECT COUNT(*) AS registros FROM tabla_getItems";
                ResultSet res1= st.executeQuery(sql);
                if(res1.next()){
                    resultado= res1.getInt("registros");
                }
                String [][] data= new String[resultado][4];
                int i= 0;
                while(res2.next()){
                    for (int j = 0; j < data[i].length; j++) {
                        data[i][j]= res2.getString(j+1);
                    }
                    i++;
                }
                res1.close();
                res2.close();
                res.close();
                venta= new Venta(numFact, fechaVenta, rifCliente, data);
            }
        }catch(SQLException e){
            System.err.println(e.getMessage());
        }
        return venta;
    }
    
    public static DefaultTableModel getVentas(){
        Conexion con= new Conexion();
        String [] ColumnName= {"N° fact.", "Fecha venta", "Cliente", ""};
        int registros=0;
        try{
            String sql="SELECT COUNT(*) AS total FROM fact_venta";
            PreparedStatement pstm= con.getConexion().prepareStatement(sql);
            ResultSet res= pstm.executeQuery();
            if(res.next()){
                registros= res.getInt("total");
            }
            res.close();
        }catch(SQLException e){
            System.err.println(e.getMessage());
        }
        Object[][] datos= new Object[registros][ColumnName.length];
        try{
            String sql= "SELECT fv.id_fact, fv.fecha_venta, c.rs_clie FROM fact_venta fv, cliente c WHERE c.rif_clie=fv.rif_clie ORDER BY fv.fecha_venta, id_fact";
            PreparedStatement pstm= con.getConexion().prepareStatement(sql);
            ResultSet res= pstm.executeQuery();
            int i=0;
            while(res.next()){
                for (int j = 0; j < ColumnName.length-1; j++) {
                    datos[i][j]= res.getString(j+1);
                }
                i++;
            }
            res.close();
        }catch(SQLException ex){
            System.err.println(ex.getMessage());
        }
        return new DefaultTableModel(datos, ColumnName);
    }
    
    private void devolverProducto(HashMap devolucion){
        if(!GuiPrincipal.listaFactCompProdExistentes.isEmpty()){
            for (HashMap compra : GuiPrincipal.listaFactCompProdExistentes) {
                if(compra.containsValue(devolucion.get("id_prod"))){
                    int cantExistente= Integer.valueOf(compra.get("diferencia").toString());
                    int cantDev= Integer.valueOf(devolucion.get("cant_venta").toString());
                    int cantNueva= cantExistente+cantDev;
                    compra.put("diferencia", cantNueva);
                    break;
                }
            }
        }
        else{
            GuiPrincipal.listaFactCompProdExistentes.add(devolucion);
        }
    }
    
    private void cambiarProductoBD(String [] cambio){
        String [] itemBd= cambio;
        try{
            Statement st= this.getConexion().createStatement();
            String sql;
            sql="SELECT s.fact_ent AS num_fact, s.id_prod, p.nombre_prod, s.can_venta AS cant_venta, s.ttl_cont, s.can_venta AS diferencia"
                + " FROM salida s, producto p"
                + " WHERE s.fact_sal='"+this.nunFact+"' AND s.id_prod='"+itemBd[0]+"' AND s.id_prod=p.id_prod LIMIT 1";
            ResultSet res= st.executeQuery(sql);
            if(res.next()){
                HashMap devolucion= new HashMap();
                ResultSetMetaData resmd= res.getMetaData();
                for (int j = 1; j <= resmd.getColumnCount(); j++) {
                    devolucion.put(resmd.getColumnLabel(j).toLowerCase(), res.getString(j));
                }
                this.devolverProducto(devolucion);
            }
            sql="DELETE FROM salida WHERE fact_sal='"+this.nunFact+"' AND id_prod='"+itemBd[0]+"'";
            st.execute(sql);
            sql="UPDATE producto SET existencia=existencia+"+itemBd[2]+" WHERE id_prod='"+itemBd[0]+"'";
            st.execute(sql);
            sql="INSERT INTO salida VALUES(?,?,?,?,?)";
            PreparedStatement pstm= this.getConexion().prepareStatement(sql);
            for (itemVenta item : this.listaItems) {
                pstm.setString(1, item.getFactSal());
                pstm.setString(2, item.getFacEnt());
                pstm.setString(3, item.getIdProd());
                pstm.setInt(4, item.getCantVenta());
                pstm.setFloat(5, item.getTtlCont());
            }
            pstm.close();
        }catch(SQLException ex){
            System.err.println(ex.getMessage());
        }
        
    }
    
    private void aumentarVentaBD(int cantAct, int cantNue, String[] itemNuevo, String[] itemBd){
        try{
            Statement st= this.getConexion().createStatement();
            String sql;
            HashMap ultimoItemFact= new HashMap();
            sql="UPDATE producto SET existencia=existencia-"+(cantNue-cantAct)+" WHERE id_prod='"+itemNuevo[0]+"'";
            st.execute(sql);
            sql="SELECT s.fact_ent AS num_fact, s.id_prod, p.nombre_prod, s.can_venta AS cant_venta, s.ttl_cont, s.can_venta AS diferencia"
                + " FROM salida s, producto p"
                + " WHERE s.fact_sal='"+this.nunFact+"' AND s.id_prod='"+itemBd[0]+"' AND s.id_prod=p.id_prod ORDER BY s.fact_ent DESC LIMIT 1";
            ResultSet res= st.executeQuery(sql);
            if(res.next()){
                ResultSetMetaData resmd= res.getMetaData();
                for (int j = 1; j <= resmd.getColumnCount(); j++) {
                    ultimoItemFact.put(resmd.getColumnLabel(j).toLowerCase(), res.getString(j));
                }
            }
            PreparedStatement pstm= this.getConexion().prepareStatement("INSERT INTO salida VALUES(?,?,?,?,?)");
            if(!ultimoItemFact.isEmpty()){
                for (itemVenta it : this.listaItems) {
                    System.out.println(it.getFacEnt()+" la cantidad de la venta es= "+it.getCantVenta());
                    if(it.getFacEnt().equals(ultimoItemFact.get("num_fact"))){
                        sql="UPDATE salida SET can_venta=can_venta+'"+(cantNue-cantAct)+"' "
                            + "WHERE fact_sal='"+this.nunFact+"' AND fact_ent='"+it.getFacEnt()+"' "
                            + "AND id_prod='"+it.getIdProd()+"'";
                        st.execute(sql);
                        System.out.println("la factura entrada es= "+it.getFacEnt()+"\nel valor de la cantAct es= "+cantAct+"\nel valor de la cantNuev es= "+cantNue);
                    }
                    else{
                        pstm.setString(1, it.getFactSal());
                        pstm.setString(2, it.getFacEnt());
                        pstm.setString(3, it.getIdProd());
                        pstm.setInt(4, it.getCantVenta());
                        pstm.setFloat(5, it.getTtlCont());
                    }
                }
            }
            pstm.close();
        }catch(SQLException ex){
            System.err.println(ex.getMessage());
        }
    }
    
    private void disminuirVentaBD(itemVenta venta){
        itemVenta it= venta;
        int diferencia= it.getCantVenta();
        ArrayList<HashMap> lista= new ArrayList<>();
        try{
            String sql="SELECT s.fact_ent AS num_fact, s.id_prod, p.nombre_prod, s.can_venta AS cant_venta, s.ttl_cont, s.can_venta AS diferencia "
                        + "FROM salida s, producto p "
                        + "WHERE s.fact_sal='"+it.getFactSal()+"' AND s.id_prod='"+it.getIdProd()+"' AND s.id_prod=p.id_prod";
            Statement st= this.getConexion().createStatement();
            ResultSet res=st.executeQuery(sql);
            ResultSetMetaData rsmt= res.getMetaData();
            while(res.next()){
                HashMap map= new HashMap();
                for (int i = 1; i < rsmt.getColumnCount(); i++) {
                    map.put(rsmt.getColumnLabel(i), res.getString(i));
                }
                lista.add(map);
            }
            for(int i=0; i<lista.size(); i++) {
                HashMap map1= lista.get(i);
                int cantMap1= Integer.valueOf(map1.get("cant_venta")+"");
                diferencia-=cantMap1;
                if(diferencia<=0){
                    int aux= cantMap1+diferencia;
                    sql="UPDATE salida SET can_venta="+aux+", ttl_cont=+"+(it.getTtlCont()*aux)
                        + " WHERE fact_sal='"+it.getFactSal()+"' AND fact_ent='"+map1.get("num_fact")+"' "
                        + "AND id_prod='"+it.getIdProd()+"'";
                    st.execute(sql);
                    for (int j = i+1; j < lista.size(); j++) {
                        sql="DELETE FROM salida WHERE fact_sal='"+it.getFactSal()+
                            "' AND fact_ent='"+lista.get(j).get("num_fact")+"' AND id_prod='"+it.getIdProd()+"'";
                        st.execute(sql);
                    }
                    break;
                }
                
            }
        }catch(SQLException e){
            System.err.println(e.getMessage());
        }
    }
    
    public boolean setItemsVenta(String[][] data){
        boolean ok= false;
        String[][] nuevosItemsVenta= data;
        try{
            Statement st= this.getConexion().createStatement();
            String sql;
            int i=0;
            for (String[] itemBd : this.getItemsVenta()) {
                this.productosNegativos=new ArrayList<>();
                this.listaItems= new HashSet<>();
                String [] itemNuevo= nuevosItemsVenta[i];
                if(!itemBd[0].equals(itemNuevo[0])){
                    this.adicionarItemVenta(itemNuevo[0], Integer.valueOf(itemNuevo[2]));
                    if(this.productosNegativos.isEmpty()){
                        this.cambiarProductoBD(itemBd);
                    }
                }
                else{
                    int cantAct=Integer.valueOf(itemBd[2]);
                    int cantNue=Integer.valueOf(itemNuevo[2]);
                    if(cantAct < cantNue){
                        this.adicionarItemVenta(itemNuevo[0], (cantNue-cantAct));
                        if(this.productosNegativos.isEmpty()){
                            this.aumentarVentaBD(cantAct, cantNue, itemNuevo, itemBd);
                        }
                    }
                    else if(cantAct > cantNue){
                        int aux= Integer.valueOf(itemNuevo[2]);
                        float aux1= Float.valueOf(itemNuevo[3]) /aux;
                        itemVenta venta= new itemVenta(this.nunFact, "0", itemNuevo[0], aux, aux1);
                        this.disminuirVentaBD(venta);
                    }
                }
                i++;
            }
            for (int j = i; j < nuevosItemsVenta.length; j++) {
                this.productosNegativos=new ArrayList<>();
                this.listaItems= new HashSet<>();
                String [] item= nuevosItemsVenta[j];
                this.adicionarItemVenta(item[0], Integer.valueOf(item[2]));
                if(this.productosNegativos.isEmpty()){
                    sql="UPDATE producto SET existencia=existencia-"+item[2]+" WHERE id_prod='"+item[0]+"'";
                    st.execute(sql);
                    sql="INSER INTO salida VALUES(?,?,?,?,?)";
                    PreparedStatement pstm= this.getConexion().prepareStatement(sql);
                    for (itemVenta it : this.listaItems) {
                        pstm.setString(1, it.getFactSal());
                            pstm.setString(2, it.getFacEnt());
                            pstm.setString(3, it.getIdProd());
                            pstm.setInt(4, it.getCantVenta());
                            pstm.setFloat(5, it.getTtlCont());
                    }
                    pstm.close();
                }
            }
            st.close();
            this.itemsVenta= nuevosItemsVenta;
            ok=true;
        }catch(SQLException e){
            System.err.println(e.getMessage());
        }
        
        return ok;
    }
    
}
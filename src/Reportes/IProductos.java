package Reportes;

import Clases.Conexion;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

public class IProductos extends Conexion{
    private final String logo= "/Imagenes/Logo.png";
    
    public void verProductos(String status, String status1){
        JasperReport repor;
        JasperPrint re;
        
        try{
            URL in= this.getClass().getResource("reporteProductos.jasper");
            repor= (JasperReport) JRLoader.loadObject(in);
            Map<String, Object> parametros= new HashMap<>();
            parametros.clear();
            parametros.put("logo", getClass().getResourceAsStream(logo));
            parametros.put("status", status);
            parametros.put("status1", status1);
            re= JasperFillManager.fillReport(repor, parametros, this.getConexion());
            JasperViewer.viewReport(re, false);
        }catch(JRException e){
            System.err.println(e.getMessage());
        }
    }
}

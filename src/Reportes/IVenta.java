/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Reportes;

import Clases.Conexion;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author richard
 */
public class IVenta extends Conexion{
    private final String logo= "/Imagenes/Logo.png";
    
    public IVenta(String numFact){
        JasperReport report;
        JasperPrint print;
        try{
            URL in= this.getClass().getResource("reporteFactVenta.jasper");
            InputStream subReporte= this.getClass().getResourceAsStream("subReportVenta.jasper");
            report = (JasperReport) JRLoader.loadObject(in);
            Map<String, Object> parametros= new HashMap<>();
            parametros.clear();
            parametros.put("logo", this.getClass().getResourceAsStream(logo));
            parametros.put("numFact", numFact);
            parametros.put("subReporteVenta", subReporte);
            print= JasperFillManager.fillReport(report, parametros, this.getConexion());
            JasperViewer.viewReport(print, false);
        }catch(JRException e){
            System.err.println(e.getMessage());
        }
    }
}

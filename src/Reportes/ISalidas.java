package Reportes;

import Clases.Conexion;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

public class ISalidas extends Conexion{
    private final String logo= "/Imagenes/Logo.png";
    
    public void ISalidas(Date fechaInicio, Date fechaFin){
        JasperReport report;
        JasperPrint print;
        try{
            URL in= this.getClass().getResource("reporteSalidas.jasper");
            report= (JasperReport) JRLoader.loadObject(in);
            Map<String, Object> parametros= new HashMap<>();
            parametros.clear();
            parametros.put("logo", getClass().getResourceAsStream(logo));
            parametros.put("fechaInicio", fechaInicio);
            parametros.put("fechaFin", fechaFin);
            print= JasperFillManager.fillReport(report, parametros, this.getConexion());
            JasperViewer.viewReport(print, false);
        }catch(JRException e){
            System.err.println(e.getMessage());
        }
    }
}

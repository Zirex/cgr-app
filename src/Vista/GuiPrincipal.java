/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import java.awt.Dimension;
import java.beans.PropertyVetoException;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JInternalFrame;

/**
 *
 * @author richard
 */
public class GuiPrincipal extends javax.swing.JFrame {
    private InternalCompra icom;
    private InternalVenta iven;
    private InternalBuscador ibus;
    private InternalReporte irep;
    public static ArrayList<HashMap> listaFactCompProdExistentes;

    /**
     * Creates new form GuiPrincipal
     */
    public GuiPrincipal() {
        initComponents();
        this.setLocationRelativeTo(null);
        listaFactCompProdExistentes= new ArrayList<>();
    }
    
    private boolean estacerrado(Object obj){
    JInternalFrame[] activos=panel.getAllFrames();
    boolean cerrado=true;
    int i=0;
    while (i<activos.length && cerrado){
	if(activos[i]==obj){
		cerrado=false;
	}
	i++;
    }
    return cerrado;
}
    
    private void posicion(JInternalFrame frame){
       Dimension d= frame.getDesktopPane().getSize();
               frame.setLocation((d.width - frame.getSize().width)/2,
                                (d.height - frame.getSize().height)/2);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jToolBar1 = new javax.swing.JToolBar();
        btnCompra = new javax.swing.JButton();
        btnVenta = new javax.swing.JButton();
        btnConsulta = new javax.swing.JButton();
        btnReporte = new javax.swing.JButton();
        panel = new javax.swing.JDesktopPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Sistema de control de reportes de licores");
        setIconImage(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/beer.png")).getImage()
        );
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);

        btnCompra.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Recepcion.png"))); // NOI18N
        btnCompra.setText("Recepción");
        btnCompra.setFocusable(false);
        btnCompra.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnCompra.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnCompra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCompraActionPerformed(evt);
            }
        });
        jToolBar1.add(btnCompra);

        btnVenta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Factura.png"))); // NOI18N
        btnVenta.setText("Ventas");
        btnVenta.setFocusable(false);
        btnVenta.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnVenta.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVentaActionPerformed(evt);
            }
        });
        jToolBar1.add(btnVenta);

        btnConsulta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Buscar.png"))); // NOI18N
        btnConsulta.setText("Consultas");
        btnConsulta.setFocusable(false);
        btnConsulta.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnConsulta.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnConsulta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConsultaActionPerformed(evt);
            }
        });
        jToolBar1.add(btnConsulta);

        btnReporte.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/report.png"))); // NOI18N
        btnReporte.setText("Reportes");
        btnReporte.setFocusable(false);
        btnReporte.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnReporte.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnReporte.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReporteActionPerformed(evt);
            }
        });
        jToolBar1.add(btnReporte);

        getContentPane().add(jToolBar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 670, 90));
        getContentPane().add(panel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 92, 670, 460));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCompraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCompraActionPerformed
        // TODO add your handling code here:
        if(this.estacerrado(icom)){
            icom= new InternalCompra();
            panel.add(icom);
            this.posicion(icom);
            icom.show();
        }
        else{
            icom.moveToFront();
            try{
                icom.setIcon(false);
            }catch(PropertyVetoException ex){
                System.err.println(ex.getMessage());
            }
        }
    }//GEN-LAST:event_btnCompraActionPerformed

    private void btnVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVentaActionPerformed
        // TODO add your handling code here:
        if(this.estacerrado(iven)){
            iven= new InternalVenta();
            panel.add(iven);
            this.posicion(iven);
            iven.show();
        }
        else{
            iven.moveToFront();
            try{
                iven.setIcon(false);
            }catch(PropertyVetoException ex){
                System.err.println(ex.getMessage());
            }
        }
    }//GEN-LAST:event_btnVentaActionPerformed

    private void btnReporteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReporteActionPerformed
        // TODO add your handling code here:
        //new IProductos().verProductos();
        if(this.estacerrado(irep)){
            irep= new InternalReporte();
            panel.add(irep);
            this.posicion(irep);
            irep.show();
        }
        else{
            irep.moveToFront();
            try{
                irep.setIcon(false);
            }catch(PropertyVetoException ex){
                System.err.println(ex.getMessage());
            }
        }
    }//GEN-LAST:event_btnReporteActionPerformed

    private void btnConsultaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConsultaActionPerformed
        // TODO add your handling code here:
        if(this.estacerrado(ibus)){
            ibus= new InternalBuscador();
            panel.add(ibus);
            this.posicion(ibus);
            ibus.show();
        }
        else{
            ibus.moveToFront();
            try{
                ibus.setIcon(false);
            }catch(PropertyVetoException ex){
                System.err.println(ex.getMessage());
            }
        }
    }//GEN-LAST:event_btnConsultaActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GuiPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GuiPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GuiPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GuiPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GuiPrincipal().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCompra;
    private javax.swing.JButton btnConsulta;
    private javax.swing.JButton btnReporte;
    private javax.swing.JButton btnVenta;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JDesktopPane panel;
    // End of variables declaration//GEN-END:variables
}

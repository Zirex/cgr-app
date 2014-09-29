/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import Reportes.ICompra;
import Reportes.IVenta;
import java.awt.Component;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author zirex
 */
public class MyTableRenderer {
    
     public static class ImageRenderer extends DefaultTableCellRenderer {
         @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            boolean bool = Boolean.parseBoolean(value.toString());
            Image img = null;
            if(bool) {
                img = getToolkit().getImage(getClass().getResource("/Imagenes/green.png"));
            } else {
                img = getToolkit().getImage(getClass().getResource("/Imagenes/red.png"));
            }
            setSize(16, 16);
            setHorizontalAlignment(SwingConstants.CENTER);
            setIcon(new ImageIcon(img));
            super.getTableCellRendererComponent(table, "", isSelected,
                    hasFocus, row, column);
            return this;
            }        
     }
     
     /*------------------------BOTON TABLA PRODUCTO ADMINISTRATIVO---------------------------------*/
     
    public static class BotonProductoRenderer implements TableCellRenderer{

        public final Icon PREVIEW_ICON = new ImageIcon(getClass().getResource("/Imagenes/view_after.png"));
        private JButton customPreviewButton= new JButton(PREVIEW_ICON);    

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            customPreviewButton.setFocusable(false); 
            customPreviewButton.setFocusPainted(false);
            customPreviewButton.setMargin(new Insets(0, 0, 0, 0));
            customPreviewButton.setToolTipText("Clic para mirar con detalle el producto");
            return customPreviewButton;
        }
    }    
    
    public static class BotonProductoEditor extends AbstractCellEditor implements TableCellEditor{
        public final Icon PREVIEW_ICON = new ImageIcon(getClass().getResource("/Imagenes/view_after.png"));
        private JButton btn= new JButton(PREVIEW_ICON);  
        
        public BotonProductoEditor(final JTable table){
            btn.setFocusable(false); 
            btn.setFocusPainted(false); 
            btn.setMargin(new Insets(0, 0, 0, 0));
            btn.setToolTipText("Clic para mirar con detalle el producto");
            btn.addActionListener(new ActionListener(){

                @Override
                public void actionPerformed(ActionEvent e) {
                    int row= table.convertRowIndexToModel(table.getEditingRow());
                    String nombreProducto= table.getValueAt(row, 1).toString();
                    new ModalProducto(nombreProducto).setVisible(true);
                }
                
            });
        }

        @Override
        public Object getCellEditorValue() {
            return null;
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            return btn;
        }
        
    }
    
    /*--------------------------BOTON TABLA COMPRA ADMINISTRATIVO--------------------------*/
    
    public static class botonCompraVentaRenderer implements TableCellRenderer{
        public final Icon PREVIEW_ICON = new ImageIcon(getClass().getResource("/Imagenes/view_after.png"));
        private JButton customPreviewButton= new JButton(PREVIEW_ICON);    

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            customPreviewButton.setFocusable(false); 
            customPreviewButton.setFocusPainted(false);
            customPreviewButton.setMargin(new Insets(0, 0, 0, 0));
            customPreviewButton.setToolTipText("Clic para mirar con más detalle");
            return customPreviewButton;
        }
    }
    
    public static class BotonCompraVentaEditor extends AbstractCellEditor implements TableCellEditor{
        public final Icon PREVIEW_ICON = new ImageIcon(getClass().getResource("/Imagenes/view_after.png"));
        private JButton btn= new JButton(PREVIEW_ICON);  
        
        public BotonCompraVentaEditor(final JTable table, final String msj){
            btn.setFocusable(false); 
            btn.setFocusPainted(false); 
            btn.setMargin(new Insets(0, 0, 0, 0));
            btn.setToolTipText("Clic para mirar con más detalle");
            btn.addActionListener(new ActionListener(){

                @Override
                public void actionPerformed(ActionEvent e) {
                    int row= table.convertRowIndexToModel(table.getEditingRow());
                    String numFact= table.getValueAt(row, 0).toString();
                    if(msj.equals("Compra")){
                        new ICompra(numFact);
                    }
                    else{
                        new IVenta(numFact);
                    }
                }
                
            });
        }

        @Override
        public Object getCellEditorValue() {
            return null;
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            return btn;
        }
        
    }
    
}
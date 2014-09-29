/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import Clases.Producto;
import java.awt.Color;
import javax.swing.JOptionPane;

/**
 *
 * @author richard
 */
public class ModalProducto extends javax.swing.JDialog {
    private Producto producto;
    private String [] getproducto;
    /**
     * Creates new form ModalProducto
     */
    public ModalProducto(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocationRelativeTo(null);
        
        this.producto= new Producto();
    }
    
    public ModalProducto(String pro){
        initComponents();
        this.setLocationRelativeTo(null);
        this.producto= new Producto();
        this.buscarProducto(pro);
    }
    
    private boolean validarDatos(){
        boolean error= false;
        if(txtNombre.getText().isEmpty()){
            txtNombre.setBorder(javax.swing.BorderFactory.createEtchedBorder(Color.lightGray, Color.red));
            txtNombre.setToolTipText("Por favor digite el nombre del producto.");
            error= true; 
        }
        
        if(txtDescripcion.getText().isEmpty()){
            txtDescripcion.setBorder(javax.swing.BorderFactory.createEtchedBorder(Color.lightGray, Color.red));
            txtDescripcion.setToolTipText("Por favor digite el nombre del producto.");
            error= true; 
        }
        if(txtGrado.getText().isEmpty()){
            txtGrado.setBorder(javax.swing.BorderFactory.createEtchedBorder(Color.lightGray, Color.red));
            txtGrado.setToolTipText("Por favor digite los grados de alcohol del producto");
            error= true;
        }
        else{
            try{
                float gradoAlcohol = Float.parseFloat(this.txtGrado.getText().trim());

            }catch(NumberFormatException ex){
                txtGrado.setText("");
                txtGrado.setBorder(javax.swing.BorderFactory.createEtchedBorder(Color.lightGray, Color.red));
                txtGrado.setToolTipText("Por favor digite cantidades numericas unicamente.");
                error= true;
            }
        }
        
        if(txtContenido.getText().isEmpty()){
            txtContenido.setBorder(javax.swing.BorderFactory.createEtchedBorder(Color.lightGray, Color.red));
            txtContenido.setToolTipText("Por favor digite el contenido o capacidad del producto");
            error= true;
        }
        else{
            try{
                float contenido = Float.parseFloat(this.txtGrado.getText().trim());

            }catch(NumberFormatException ex){
                txtGrado.setText("");
                txtGrado.setBorder(javax.swing.BorderFactory.createEtchedBorder(Color.lightGray, Color.red));
                txtGrado.setToolTipText("Por favor digite cantidades numericas unicamente.");
                error= true;
            }
        }
        
        if(cmbFabricacion.getSelectedItem().toString().isEmpty()){
            cmbFabricacion.setBackground(Color.red);
            cmbFabricacion.setToolTipText("Por favor seleccione el tipo de fabricación");
            error=true;
        }
        return error; 
    }
    
    private void limpiarTexto(){
        txtNombre.setText("");
        txtDescripcion.setText("");
        txtGrado.setText("");
        txtContenido.setText("");
        this.cmbFabricacion.setSelectedIndex(0);
        btnGuardar.setEnabled(true);
    }
    
    private void buscarProducto(String nombreProd){
        getproducto= Producto.searchProducto(nombreProd);
            if(getproducto != null){
                if(getproducto[7].equals("1")){
                    txtNombre.setText(nombreProd);
                    txtDescripcion.setBorder(javax.swing.UIManager.getBorder("textField.Border"));
                    txtDescripcion.setToolTipText(null);
                    txtGrado.setBorder(new javax.swing.JTextField().getBorder());
                    txtGrado.setToolTipText(null);
                    txtContenido.setBorder(new javax.swing.JTextField().getBorder());
                    txtContenido.setToolTipText(null);
                    cmbFabricacion.setBackground(javax.swing.UIManager.getColor("comboBox.Background"));
                    cmbFabricacion.setToolTipText(null);
                    btnGuardar.setEnabled(false);
                    
                    txtDescripcion.setText(getproducto[2]);
                    txtGrado.setText(getproducto[3]);
                    txtContenido.setText(getproducto[4]);
                    this.cmbFabricacion.setSelectedItem(getproducto[6]);
                }
                else{
                    JOptionPane.showMessageDialog(this, "El producto esta dado de baja.\n para activar el producto debe dirigirse a la ventana de administrador", "Informacion", JOptionPane.INFORMATION_MESSAGE);
                }
            }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtDescripcion = new javax.swing.JTextArea();
        txtGrado = new javax.swing.JTextField();
        txtContenido = new javax.swing.JTextField();
        cmbFabricacion = new javax.swing.JComboBox();
        btnGuardar = new javax.swing.JButton();
        btnActualizar = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Gestión productos");
        setResizable(false);

        jLabel1.setText("Nombre producto:");

        jLabel2.setText("Descripción:");

        jLabel3.setText("Grados de alcohol:");

        jLabel4.setText("Contenido:");

        jLabel5.setText("Fabricación:");

        txtNombre.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNombreFocusLost(evt);
            }
        });

        txtDescripcion.setColumns(20);
        txtDescripcion.setLineWrap(true);
        txtDescripcion.setRows(5);
        txtDescripcion.setAutoscrolls(false);
        txtDescripcion.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDescripcionFocusLost(evt);
            }
        });
        jScrollPane1.setViewportView(txtDescripcion);

        txtGrado.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtGradoFocusLost(evt);
            }
        });

        txtContenido.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtContenidoFocusLost(evt);
            }
        });

        cmbFabricacion.setModel(new javax.swing.DefaultComboBoxModel(new String[] { " ", "Nacional", "Exportado" }));
        cmbFabricacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbFabricacionActionPerformed(evt);
            }
        });

        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        btnActualizar.setText("Actualizar");
        btnActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarActionPerformed(evt);
            }
        });

        btnLimpiar.setText("Limpiar");
        btnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1))
                        .addGap(22, 22, 22)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5))
                        .addGap(21, 21, 21)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cmbFabricacion, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtContenido, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtGrado, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(54, 54, 54)
                .addComponent(btnGuardar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnActualizar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnLimpiar)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(48, 48, 48)
                        .addComponent(jLabel2))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtGrado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtContenido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbFabricacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnGuardar)
                    .addComponent(btnActualizar)
                    .addComponent(btnLimpiar))
                .addGap(20, 20, 20))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtNombreFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNombreFocusLost
        // TODO add your handling code here:
        if(!txtNombre.getText().isEmpty()){
            txtNombre.setBorder(new javax.swing.JTextField().getBorder());
            txtNombre.setToolTipText(null);
            this.buscarProducto(txtNombre.getText().trim());
        }
    }//GEN-LAST:event_txtNombreFocusLost

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        // TODO add your handling code here:
        String nombreProducto= this.txtNombre.getText().trim();
        String descripcion= this.txtDescripcion.getText().trim();
        float gradoAlcohol = 0;
        float contenido = 0;
        String fabricacion= this.cmbFabricacion.getSelectedItem().toString();
        
        if(!this.validarDatos()){
            
            gradoAlcohol = Float.parseFloat(txtGrado.getText().trim());
            contenido = Float.valueOf(txtContenido.getText().trim());
            
            if(this.producto.nuevoProducto(nombreProducto, descripcion, gradoAlcohol, contenido, fabricacion)){
                JOptionPane.showMessageDialog(this, "Producto guardado exitosamente", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
                InternalCompra.cmbProductos.addItem(txtNombre.getText().trim());
                this.limpiarTexto();
            }
            else{
                JOptionPane.showMessageDialog(this, "Fallo al guardar el producto", "Mensaje", JOptionPane.ERROR_MESSAGE);
            }
            
        }
        
        else{
            JOptionPane.showMessageDialog(this, "Revisa los campos incorrectos", "Error Tipeado", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
        // TODO add your handling code here:
        String nombreProducto= this.txtNombre.getText().trim();
        String descripcion= this.txtDescripcion.getText().trim();
        float gradoAlcohol = 0;
        float contenido = 0;
        String fabricacion= this.cmbFabricacion.getSelectedItem().toString();
        
        if(!this.validarDatos()){
            
            gradoAlcohol = Float.parseFloat(txtGrado.getText().trim());
            contenido = Float.valueOf(txtContenido.getText().trim());
            
            if(this.producto.updateProducto(getproducto[0], nombreProducto, descripcion, gradoAlcohol, contenido, fabricacion)){
                JOptionPane.showMessageDialog(this, "Producto se modifico exitosamente", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
                this.limpiarTexto();
            }
            else{
                JOptionPane.showMessageDialog(this, "Fallo al modificar el producto", "Mensaje", JOptionPane.ERROR_MESSAGE);
            }
            
        }
        
        else{
            JOptionPane.showMessageDialog(this, "Revisa los campos incorrectos", "Error Tipeado", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_btnActualizarActionPerformed

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        // TODO add your handling code here:
        this.limpiarTexto();
    }//GEN-LAST:event_btnLimpiarActionPerformed

    private void txtDescripcionFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDescripcionFocusLost
        // TODO add your handling code here:
        if(!txtDescripcion.getText().isEmpty()){
            txtDescripcion.setBorder(javax.swing.UIManager.getBorder("textField.Border"));
            txtDescripcion.setToolTipText(null);
        }
    }//GEN-LAST:event_txtDescripcionFocusLost

    private void txtGradoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtGradoFocusLost
        // TODO add your handling code here:
        if(!txtGrado.getText().isEmpty()){
            txtGrado.setBorder(new javax.swing.JTextField().getBorder());
            txtGrado.setToolTipText(null);
        }
    }//GEN-LAST:event_txtGradoFocusLost

    private void txtContenidoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtContenidoFocusLost
        // TODO add your handling code here:
        if(!txtContenido.getText().isEmpty()){
            txtContenido.setBorder(new javax.swing.JTextField().getBorder());
            txtContenido.setToolTipText(null);
        }
    }//GEN-LAST:event_txtContenidoFocusLost

    private void cmbFabricacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbFabricacionActionPerformed
        // TODO add your handling code here:
        if(!cmbFabricacion.getSelectedItem().toString().isEmpty()){
            cmbFabricacion.setBackground(javax.swing.UIManager.getColor("comboBox.Background"));
            cmbFabricacion.setToolTipText(null);
        }
    }//GEN-LAST:event_cmbFabricacionActionPerformed

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
            java.util.logging.Logger.getLogger(ModalProducto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ModalProducto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ModalProducto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ModalProducto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ModalProducto dialog = new ModalProducto(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JComboBox cmbFabricacion;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField txtContenido;
    private javax.swing.JTextArea txtDescripcion;
    private javax.swing.JTextField txtGrado;
    private javax.swing.JTextField txtNombre;
    // End of variables declaration//GEN-END:variables
}

// Creamos el formato de nuestra contraseña
   // # -> un número   U -> letra mayúscula  L -> letra minúscula
   // * -> cualquier caracter  ' -> caracteres de escape
   // A -> cualquier letra o número   ? -> cualquier caracter
   // H -> cualquier caracter hexagonal (0-9, a-f or A-F).
package Vista;

import Clases.Compra;
import Clases.Producto;
import Clases.Proveedor;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.util.EventObject;
import java.util.HashSet;
import javax.swing.DefaultCellEditor;
import javax.swing.InputMap;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.text.JTextComponent;

/**
 *
 * @author richard
 */
public class InternalCompra extends javax.swing.JInternalFrame {
    private ModeloTabla model;
    private Compra cp;
    private Proveedor pro;
    private HashSet<Proveedor> provs;
    /**
     * Creates new form InternalCompra
     */
    public InternalCompra() {
        initComponents();
        this.provs= new HashSet();
        this.cargarTabla();
        this.cargarComboBoxProducto();
    }
    
    private void cargarComboBoxProducto(){
        cmbProductos.removeAllItems();
        String [] productos= Producto.nameProductos();
        for (String pro : productos) {
            cmbProductos.addItem(pro);
        }
    }
    
    private void inicializarTabla(){
        this.model= new ModeloTabla();
        this.tablaCompra.setModel(model);
        this.tablaCompra.getTableHeader().setReorderingAllowed(false);
        this.tablaCompra.getColumnModel().getColumn(1).setCellEditor(new DefaultCellEditor(cmbProductos));
        KeyStroke tab = KeyStroke.getKeyStroke(KeyEvent.VK_TAB, 0);
        KeyStroke enter = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0);
        InputMap im = tablaCompra.getInputMap(JTable.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        im.put(enter, im.get(tab));
        
        tablaCompra.addKeyListener(new KeyListener(){
            @Override
            public void keyPressed(KeyEvent ke) {
            }

            @Override
            public void keyTyped(KeyEvent ke) {
            }

            @Override
            public void keyReleased(KeyEvent ke) {
                if (model.rowAdd) {
                    tablaCompra.changeSelection ( tablaCompra.getRowCount () - 1, 0, false, false );
                    model.rowAdd = false;
                }
            }
        });
    }
    
    private void cargarTabla(){
        this.inicializarTabla();
        this.model.insertarFilaNueva(-1);
    }
    
    private boolean validarCampos(){
        boolean error= false;
        if(txtNum.getText().isEmpty()){
            txtNum.setBorder(javax.swing.BorderFactory.createEtchedBorder(Color.lightGray, Color.red));
            txtNum.setToolTipText("Por favor digite el numero de factura.");
            error= true; 
        }
        if(dcFecha.getDate()== null){
            error= true; 
        }
        if(txtRif.getText().isEmpty()){
            txtRif.setBorder(javax.swing.BorderFactory.createEtchedBorder(Color.lightGray, Color.red));
            txtRif.setToolTipText("Por favor digite el RIF del proveedor");
            error= true;
        }
        if(txtRS.getText().isEmpty()){
            txtRS.setBorder(javax.swing.BorderFactory.createEtchedBorder(Color.lightGray, Color.red));
            txtRS.setToolTipText("Por favor digite la razón social del proveedor");
            error= true;
        }
        if(error){
            JOptionPane.showMessageDialog(this, "Por favor verifique que todos los campos esten completos");
        }
        return error;
    }
    
    private void limpiarCompra(){
        txtNum.setText("");
        dcFecha.setDate(null);
        txtRif.setText("");
        txtRS.setText("");
        this.cargarTabla();
    }
    
    private void actualizarProvs(String rifProv, String rsProv){
        if(!this.pro.getRif().equals(rifProv) || !this.pro.getRazonSocial().equals(rsProv)){
            this.provs.remove(this.pro);
            if(!this.pro.getRif().equals(rifProv)){
                this.pro.setRif(rifProv);
            }
            if(!this.pro.getRazonSocial().equals(rsProv)){
                this.pro.setRazonSocial(rsProv);
            }
            this.provs.add(this.pro);
        }
    }
    
    private void llenarCamposProv(String rifProv){
        for (Proveedor proveedor : provs) {
                if(proveedor.getRif().equals(rifProv)){
                    txtRif.setBorder(new javax.swing.JTextField().getBorder());
                    txtRif.setToolTipText(null);
                    txtRS.setBorder(new javax.swing.JTextField().getBorder());
                    txtRS.setToolTipText(null);
                    
                    txtRS.setText(proveedor.getRazonSocial());
                    this.pro= proveedor;
                    return;
                }
            }
            this.pro= Proveedor.searchProveedor(rifProv);
            if(this.pro != null){
                txtRif.setBorder(new javax.swing.JTextField().getBorder());
                txtRif.setToolTipText(null);
                txtRS.setBorder(new javax.swing.JTextField().getBorder());
                txtRS.setToolTipText(null);
                
                txtRif.setText(this.pro.getRif());
                txtRS.setText(this.pro.getRazonSocial());
                this.provs.add(pro);
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

        cmbProductos = new com.jidesoft.swing.AutoCompletionComboBox();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtNum = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaCompra = new javax.swing.JTable(){
            @Override
            public boolean editCellAt(int row, int column, EventObject e)
            {
                boolean result = super.editCellAt(row, column, e);

                selectAll(e);

                return result;
            }

            /*
            * Select the text when editing on a text related cell is started
            */
            private void selectAll(EventObject e)
            {
                final Component editor = getEditorComponent();

                if (editor == null
                    || ! (editor instanceof JTextComponent))
                return;

                if (e == null)
                {
                    ((JTextComponent)editor).selectAll();
                    return;
                }

                //  Typing in the cell was used to activate the editor

                if (e instanceof KeyEvent)
                {
                    ((JTextComponent)editor).selectAll();
                    return;
                }

                //  F2 was used to activate the editor

                if (e instanceof ActionEvent)
                {
                    ((JTextComponent)editor).selectAll();
                    return;
                }

                //  A mouse click was used to activate the editor.
                //  Generally this is a double click and the second mouse click is
                //  passed to the editor which would remove the text selection unless
                //  we use the invokeLater()

                if (e instanceof MouseEvent)
                {
                    SwingUtilities.invokeLater(new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                ((JTextComponent)editor).selectAll();
                            }
                        });
                    }
                }
            };
            dcFecha = new com.toedter.calendar.JDateChooser();
            btnProducto = new javax.swing.JButton();
            btnGuardar = new javax.swing.JButton();
            btnSalir = new javax.swing.JButton();
            jLabel3 = new javax.swing.JLabel();
            txtRif = new javax.swing.JTextField();
            jLabel4 = new javax.swing.JLabel();
            txtRS = new javax.swing.JTextField();

            setClosable(true);
            setIconifiable(true);
            setTitle("Recepción de productos");

            jLabel1.setText("Numero fact:");

            jLabel2.setText("Fecha recepción:");

            txtNum.addFocusListener(new java.awt.event.FocusAdapter() {
                public void focusLost(java.awt.event.FocusEvent evt) {
                    txtNumFocusLost(evt);
                }
            });

            tablaCompra.setModel(new javax.swing.table.DefaultTableModel(
                new Object [][] {
                    {null, null, null, null},
                    {null, null, null, null},
                    {null, null, null, null},
                    {null, null, null, null}
                },
                new String [] {
                    "Title 1", "Title 2", "Title 3", "Title 4"
                }
            ));
            jScrollPane1.setViewportView(tablaCompra);

            btnProducto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/producto.png"))); // NOI18N
            btnProducto.setToolTipText("Nuevo producto");
            btnProducto.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    btnProductoActionPerformed(evt);
                }
            });

            btnGuardar.setText("Guardar");
            btnGuardar.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    btnGuardarActionPerformed(evt);
                }
            });

            btnSalir.setText("Cancelar");
            btnSalir.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    btnSalirActionPerformed(evt);
                }
            });

            jLabel3.setText("Rif. Prov:");

            txtRif.addFocusListener(new java.awt.event.FocusAdapter() {
                public void focusLost(java.awt.event.FocusEvent evt) {
                    txtRifFocusLost(evt);
                }
            });

            jLabel4.setText("Razón Social:");

            txtRS.addFocusListener(new java.awt.event.FocusAdapter() {
                public void focusLost(java.awt.event.FocusEvent evt) {
                    txtRSFocusLost(evt);
                }
            });

            javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
            getContentPane().setLayout(layout);
            layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel1)
                                .addComponent(txtNum, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 132, Short.MAX_VALUE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel2)
                                .addComponent(dcFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(txtRif, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jLabel4)
                                    .addGap(0, 0, Short.MAX_VALUE))
                                .addComponent(txtRS)))
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(btnProducto)
                                .addComponent(jLabel3))
                            .addGap(0, 0, Short.MAX_VALUE)))
                    .addContainerGap())
                .addGroup(layout.createSequentialGroup()
                    .addGap(107, 107, 107)
                    .addComponent(btnGuardar)
                    .addGap(70, 70, 70)
                    .addComponent(btnSalir)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            );
            layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(21, 21, 21)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(jLabel2))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(txtNum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(dcFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(18, 18, 18)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3)
                        .addComponent(jLabel4))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtRif, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtRS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                    .addComponent(btnProducto)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnGuardar)
                        .addComponent(btnSalir))
                    .addContainerGap())
            );

            pack();
        }// </editor-fold>//GEN-END:initComponents

    private void btnProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProductoActionPerformed
        // TODO add your handling code here:
        new ModalProducto(null, true).setVisible(true);
    }//GEN-LAST:event_btnProductoActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        // TODO add your handling code here:
        String numFact= txtNum.getText().trim();
        java.util.Date fecha= dcFecha.getDate();
        String rifProv= this.txtRif.getText().trim().toUpperCase();
        String rsProv= this.txtRS.getText().trim();
        String [][]data;
        if(tablaCompra.getValueAt(tablaCompra.getRowCount()-1, 3).toString().equals("0.0")){
            data= new String[tablaCompra.getRowCount()-1][4];
        }
        else{
            data= new String[tablaCompra.getRowCount()][4];
        }
        
        if(!this.validarCampos()){
            for(int i=0; i < data.length; i++){
                if(!tablaCompra.getValueAt(i, 3).toString().equals("0.0")){
                    data[i][0]=numFact;
                    data[i][1]=tablaCompra.getValueAt(i, 0).toString();
                    data[i][2]=tablaCompra.getValueAt(i, 2).toString();
                    data[i][3]=tablaCompra.getValueAt(i, 3).toString();
                }
                else{
                    JOptionPane.showMessageDialog(this, "Por favor verifique que los litros sean mayores a 0", 
                                                      "Advertencia", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
            }
            if(this.btnGuardar.getText().equals("Guardar")){
                boolean ok= false;
                if(this.pro == null){
                    this.pro= new Proveedor(rifProv, rsProv);
                    if(this.pro.insertProveedor() && this.cp.insertCompra(numFact, fecha, rifProv, data)){
                        this.provs.add(pro);
                        ok= true;
                    }
                }
                else{
                    if(this.cp.insertCompra(numFact, fecha, rifProv, data)){
                        ok=true;
                    }
                }
                if(ok){
                    JOptionPane.showMessageDialog(this, "Exito.. La nueva compra se ha registrado", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
                    this.limpiarCompra();
                }

                else{
                    JOptionPane.showMessageDialog(this, "Error.. La compra no se pudo registrar", "Mensaje", JOptionPane.ERROR_MESSAGE);
                }
            }
            else{
                String msj="La factura no fue actualizada..\nNo se encontraron cambios en los registros de la base de datos";
                if(!fecha.equals(this.cp.getFechaFac())){
                    this.cp.setFechaFac(fecha);
                    msj="La fecha de la factura se actualizo correctamente";
                }
                if(this.cp.setItemsCompra(data)){
                    msj="La factura se actualizo correctamente";
                }
                this.actualizarProvs(rifProv, rsProv);
                JOptionPane.showMessageDialog(this, msj, "Informacion", JOptionPane.INFORMATION_MESSAGE);
                this.limpiarCompra();
            }
        }
        else{
            JOptionPane.showMessageDialog(this, "Verifique los campos", "Error", JOptionPane.ERROR_MESSAGE);
        }
        
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void txtNumFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNumFocusLost
        // TODO add your handling code here:
        if(!txtNum.getText().isEmpty()){
            txtNum.setBorder(new javax.swing.JTextField().getBorder());
            txtNum.setToolTipText(null);
            
            cp= Compra.existeFact(txtNum.getText().trim());
            if(cp != null){
                dcFecha.setDate(cp.getFechaFac());
                this.llenarCamposProv(cp.getRifProv());
                this.model.setItems(cp.getItems());
                this.btnGuardar.setText("Actualizar");
            }
            else{
                this.cp= new Compra();
            }
        }
    }//GEN-LAST:event_txtNumFocusLost

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        // TODO add your handling code here:
        this.limpiarCompra();
    }//GEN-LAST:event_btnSalirActionPerformed

    private void txtRifFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtRifFocusLost
        // TODO add your handling code here:
        if(!txtRif.getText().isEmpty()){
            txtRif.setBorder(new javax.swing.JTextField().getBorder());
            txtRif.setToolTipText(null);
            this.llenarCamposProv(txtRif.getText().trim().toUpperCase());
        }
    }//GEN-LAST:event_txtRifFocusLost

    private void txtRSFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtRSFocusLost
        // TODO add your handling code here:
        if(!txtRS.getText().isEmpty()){
            txtRS.setBorder(new javax.swing.JTextField().getBorder());
            txtRS.setToolTipText(null);
        }
    }//GEN-LAST:event_txtRSFocusLost

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnProducto;
    private javax.swing.JButton btnSalir;
    public static com.jidesoft.swing.AutoCompletionComboBox cmbProductos;
    private com.toedter.calendar.JDateChooser dcFecha;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tablaCompra;
    private javax.swing.JTextField txtNum;
    private javax.swing.JTextField txtRS;
    private javax.swing.JTextField txtRif;
    // End of variables declaration//GEN-END:variables
}

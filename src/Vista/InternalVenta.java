/*
 * MaskFormatted:
 * # -> un número
 * U -> letra mayúscula
 * L -> letra minúscula
 * * -> cualquier caracter
 * ' -> caracteres de escape
 * A -> cualquier letra o número
 * ? -> cualquier caracter
 * H -> cualquier caracter hexagonal (0-9, a-f or A-F).
*/
package Vista;

import Clases.Cliente;
import Clases.Producto;
import Clases.Venta;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.EventObject;
import java.util.HashSet;
import javax.swing.*;
import javax.swing.text.JTextComponent;

/**
 *
 * @author richard
 */
public class InternalVenta extends javax.swing.JInternalFrame {
    private ModeloTabla model;
    private Venta venta;
    private Cliente cliente;
    private HashSet<Cliente> clientes;

    /**
     * Creates new form InternalVenta
     */
    public InternalVenta() {
        initComponents();
        this.cargarComboBoxProducto();
        this.cargarTabla();
        this.clientes= new HashSet();
        this.txtRif.setFocusLostBehavior(JFormattedTextField.COMMIT);
    }
    
    private void cargarComboBoxProducto(){
        cmbProductos.removeAll();
        String [] productos= Producto.nameProductos();
        for (String pro : productos) {
            cmbProductos.addItem(pro);
        }
    }
    
    private void inicializarTabla(){
        this.model= new ModeloTabla();
        this.tablaVenta.setModel(model);
        this.tablaVenta.getTableHeader().setReorderingAllowed(false);
        this.tablaVenta.getColumnModel().getColumn(1).setCellEditor(new DefaultCellEditor(cmbProductos));
        KeyStroke tab = KeyStroke.getKeyStroke(KeyEvent.VK_TAB, 0);
        KeyStroke enter = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0);
        InputMap im = tablaVenta.getInputMap(JTable.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        im.put(enter, im.get(tab));
        
        tablaVenta.addKeyListener(new KeyListener(){
            @Override
            public void keyPressed(KeyEvent ke) {
            }

            @Override
            public void keyTyped(KeyEvent ke) {
            }

            @Override
            public void keyReleased(KeyEvent ke) {
                if (model.rowAdd) {
                    tablaVenta.changeSelection ( tablaVenta.getRowCount () - 1, 0, false, false );
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
        if(txtRif.getText().equals(" -        - ")){
            txtRif.setBorder(javax.swing.BorderFactory.createEtchedBorder(Color.lightGray, Color.red));
            txtRif.setToolTipText("Por favor digite el RIF del cliente.");
            error= true;
        }
        if(txtRS.getText().isEmpty()){
            txtRS.setBorder(javax.swing.BorderFactory.createEtchedBorder(Color.lightGray, Color.red));
            txtRS.setToolTipText("Por favor digite la Razón social o Nombre del Cliente");
            error= true;
        }
        if(txtDir.getText().isEmpty()){
            txtDir.setBorder(javax.swing.BorderFactory.createEtchedBorder(Color.lightGray, Color.red));
            txtDir.setToolTipText("Por favor digite la Dirección del Cliente");
            error= true;
        }
        
        if(error)
            JOptionPane.showMessageDialog(this, "Por favor verifique que esten todos los campos completos");
        
        return error;
    }
    
    private String rifCliente(){
       String rif []= this.txtRif.getText().split(" ");
       
        char l= rif[0].charAt(rif[0].length()-1);
        if(l == '-'){
            rif[0]= rif[0].substring(0, rif[0].length()-1);
            return rif[0];
        }
        else{
            return this.txtRif.getText();
        }
    }
    
    private void limpiarVenta(){
        txtNum.setText("");
        dcFecha.setDate(null);
        txtRif.setText("");
        txtRS.setText("");
        txtDir.setText("");
        this.cargarTabla();
    }
    
    private void llenarCampoCliente(String rifCliente){
        for (Cliente cliente1 : clientes) {
            if(cliente1.getRifCliente().equals(rifCliente)){
                txtRS.setBorder(new javax.swing.JTextField().getBorder());
                txtRS.setToolTipText(null);
                txtDir.setBorder(new javax.swing.JTextField().getBorder());
                txtDir.setToolTipText(null);
                
                txtRif.setText(rifCliente);
                txtRS.setText(cliente1.getRsCliente());
                txtDir.setText(cliente1.getDirCliente());
                this.cliente= cliente1;
                return;
            }
        }
        this.cliente= Cliente.searchCliente(rifCliente);
        if(this.cliente != null){
            txtRS.setBorder(new javax.swing.JTextField().getBorder());
            txtRS.setToolTipText(null);
            txtDir.setBorder(new javax.swing.JTextField().getBorder());
            txtDir.setToolTipText(null);
            
            txtRif.setText(rifCliente);
            txtRS.setText(this.cliente.getRsCliente());
            txtDir.setText(this.cliente.getDirCliente());
            this.clientes.add(this.cliente);
            btnGuardar.setText("Actualizar");
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
        dcFecha = new com.toedter.calendar.JDateChooser();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaVenta = new javax.swing.JTable(){
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
            btnGuardar = new javax.swing.JButton();
            btnCancelar = new javax.swing.JButton();
            jLabel3 = new javax.swing.JLabel();
            jLabel4 = new javax.swing.JLabel();
            txtRS = new javax.swing.JTextField();
            jLabel5 = new javax.swing.JLabel();
            txtDir = new javax.swing.JTextField();
            txtRif = new javax.swing.JFormattedTextField();

            setClosable(true);
            setIconifiable(true);
            setTitle("Salida de productos");

            jLabel1.setText("Numero fact.:");

            jLabel2.setText("Fecha de salida:");

            txtNum.addFocusListener(new java.awt.event.FocusAdapter() {
                public void focusLost(java.awt.event.FocusEvent evt) {
                    txtNumFocusLost(evt);
                }
            });

            tablaVenta.setModel(new javax.swing.table.DefaultTableModel(
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
            jScrollPane1.setViewportView(tablaVenta);

            btnGuardar.setText("Guardar");
            btnGuardar.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    btnGuardarActionPerformed(evt);
                }
            });

            btnCancelar.setText("Cancelar");
            btnCancelar.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    btnCancelarActionPerformed(evt);
                }
            });

            jLabel3.setText("R.I.F Cliente:");

            jLabel4.setText("Razón Social:");

            txtRS.addFocusListener(new java.awt.event.FocusAdapter() {
                public void focusLost(java.awt.event.FocusEvent evt) {
                    txtRSFocusLost(evt);
                }
            });

            jLabel5.setText("Dirección del Cliente:");

            txtDir.addFocusListener(new java.awt.event.FocusAdapter() {
                public void focusLost(java.awt.event.FocusEvent evt) {
                    txtDirFocusLost(evt);
                }
            });

            try {
                txtRif.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("U-########-#")));
            } catch (java.text.ParseException ex) {
                ex.printStackTrace();
            }
            txtRif.addFocusListener(new java.awt.event.FocusAdapter() {
                public void focusLost(java.awt.event.FocusEvent evt) {
                    txtRifFocusLost(evt);
                }
            });

            javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
            getContentPane().setLayout(layout);
            layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                    .addGap(114, 114, 114)
                                    .addComponent(btnGuardar)
                                    .addGap(81, 81, 81)
                                    .addComponent(btnCancelar))
                                .addGroup(layout.createSequentialGroup()
                                    .addContainerGap()
                                    .addComponent(jLabel5)))
                            .addGap(0, 0, Short.MAX_VALUE))
                        .addGroup(layout.createSequentialGroup()
                            .addContainerGap()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(txtDir)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(txtNum)
                                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(dcFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel3)
                                        .addComponent(txtRif, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGap(30, 30, 30)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(jLabel4)
                                            .addGap(0, 0, Short.MAX_VALUE))
                                        .addComponent(txtRS))))))
                    .addContainerGap())
            );
            layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(jLabel2))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(txtNum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(dcFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3)
                        .addComponent(jLabel4))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtRS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtRif, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jLabel5)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(txtDir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnGuardar)
                        .addComponent(btnCancelar))
                    .addContainerGap())
            );

            pack();
        }// </editor-fold>//GEN-END:initComponents

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        // TODO add your handling code here:
        
        String numFact= txtNum.getText().trim();
        java.util.Date fecha= dcFecha.getDate();
        String rifCliente= this.rifCliente();
        String rsCliente= txtRS.getText().trim();
        String dirCliente= txtDir.getText().trim();
        String [][]data;
        if(tablaVenta.getValueAt(tablaVenta.getRowCount()-1, 3).toString().equals("0.0")){
            data= new String[tablaVenta.getRowCount()-1][4];
        }
        else{
            data= new String[tablaVenta.getRowCount()][4];
        }
        
        if(!this.validarCampos()){
            for(int i=0; i < data.length; i++){
                if(!tablaVenta.getValueAt(i, 3).toString().equals("0.0")){
                    data[i][0]=tablaVenta.getValueAt(i, 0).toString();
                    data[i][1]=tablaVenta.getValueAt(i, 1).toString();
                    data[i][2]=tablaVenta.getValueAt(i, 2).toString();
                    data[i][3]=tablaVenta.getValueAt(i, 3).toString();
                }
                else{
                    JOptionPane.showMessageDialog(this, "Por favor verifique que los litros sean mayores a 0", 
                                                      "Advertencia", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
            }
            
            if(!btnGuardar.getText().equals("Actualizar")){
                ArrayList<String> inspeccionarTabla= this.venta.getProductosNegativos(data);
                if(!inspeccionarTabla.isEmpty()){
                    String msj= "La factura no puede ser guardada.\n Los siguientes productos quedaran con existencia negativa:";
                    for (String prod : inspeccionarTabla) {
                        msj+= "\n"+prod;
                    }
                    JOptionPane.showMessageDialog(this, msj, "Advertencia", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                boolean ok= false;
                if(this.cliente==null){
                    this.cliente= new Cliente(rifCliente, rsCliente, dirCliente);
                    if(this.cliente.insertCliente() && this.venta.insertarVenta(numFact, fecha, this.cliente.getRifCliente(), data)){
                        this.clientes.add(this.cliente);
                        ok=true;
                    }
                }
                else{

                    if(this.venta.insertarVenta(numFact, fecha, this.cliente.getRifCliente(), data)){
                        ok=true;
                    }
                }
                if(ok){
                    JOptionPane.showMessageDialog(this, "Exito.. Factura Venta guardada.", "Registro exitoso", JOptionPane.INFORMATION_MESSAGE);
                    this.limpiarVenta();
                }
            }
            else{
                
                Calendar fechaSistema= Calendar.getInstance();
                Calendar fechaFactura= dcFecha.getCalendar();
                // Conseguir la representafción de la fecha en milisegundos.
                long milisFechaSistema= fechaSistema.getTimeInMillis();
                long milisFechaFactura= fechaFactura.getTimeInMillis();
                // Calcular la diferencía en milisegundos
                long diferencia= milisFechaSistema - milisFechaFactura;
                // Calcular la diferencía en días
                long difDias= diferencia / (24 * 60 * 60 * 1000);
                                
                if(difDias <= 7){
                    this.venta.setItemsVenta(data);
                }
                else{
                    JOptionPane.showMessageDialog(this, 
                                                  "No se puede modificar los datos de la venta \n porque excede el limite de días para la modificación", 
                                                  "Error en modificación", 
                                                  JOptionPane.INFORMATION_MESSAGE);
                }
            }
            
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void txtNumFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNumFocusLost
        // TODO add your handling code here:
        if(!txtNum.getText().isEmpty()){
            txtNum.setBorder(new javax.swing.JTextField().getBorder());
            txtNum.setToolTipText(null);
            
            this.venta= Venta.searchVenta(txtNum.getText().trim());
            if(this.venta != null){
                txtRif.setBorder(new javax.swing.JTextField().getBorder());
                txtRif.setToolTipText(null);
                txtRS.setBorder(new javax.swing.JTextField().getBorder());
                txtRS.setToolTipText(null);
                txtDir.setBorder(new javax.swing.JTextField().getBorder());
                txtDir.setToolTipText(null);
                
                dcFecha.setDate(this.venta.getFecha());
                this.llenarCampoCliente(this.venta.getRifCliente());
                this.model.setItems(this.venta.getItemsVenta());
                this.btnGuardar.setText("Actualizar");
            }
            else{
                this.venta= new Venta(txtNum.getText().trim());
            }
        }
    }//GEN-LAST:event_txtNumFocusLost

    private void txtRSFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtRSFocusLost
        // TODO add your handling code here:
        if(!txtRS.getText().isEmpty()){
            txtRS.setBorder(new javax.swing.JTextField().getBorder());
            txtRS.setToolTipText(null);
        }
    }//GEN-LAST:event_txtRSFocusLost

    private void txtDirFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDirFocusLost
        // TODO add your handling code here:
        if(!txtDir.getText().isEmpty()){
            txtDir.setBorder(new javax.swing.JTextField().getBorder());
            txtDir.setToolTipText(null);
        }
    }//GEN-LAST:event_txtDirFocusLost

    private void txtRifFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtRifFocusLost
        // TODO add your handling code here:
        if(!txtRif.getText().equals(" -        - ")){
            txtRif.setBorder(new javax.swing.JTextField().getBorder());
            txtRif.setToolTipText(null);
        }
        this.llenarCampoCliente(this.rifCliente());
    }//GEN-LAST:event_txtRifFocusLost

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        // TODO add your handling code here:
        this.venta= null;
        this.limpiarVenta();
        this.btnGuardar.setText("Guardar");
    }//GEN-LAST:event_btnCancelarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnGuardar;
    private com.jidesoft.swing.AutoCompletionComboBox cmbProductos;
    private com.toedter.calendar.JDateChooser dcFecha;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tablaVenta;
    private javax.swing.JTextField txtDir;
    private javax.swing.JTextField txtNum;
    private javax.swing.JTextField txtRS;
    private javax.swing.JFormattedTextField txtRif;
    // End of variables declaration//GEN-END:variables
}

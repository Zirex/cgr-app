package Vista;

import Clases.Producto;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

public class ModeloTabla extends AbstractTableModel{
    private String [] ColumnName= {"Id.", "Nombre item", "Cantidad", "Total litros"};    
    public boolean rowAdd= false;
    // <editor-fold defaultstate="collapsed" desc="Clase que se utiliza para manejar los datos de la tabla.">
    private class itemModelo {
        private int id;
        private String nombreItem;
        private int cantidad;
        private float ttlCont;

        public itemModelo(int id, String nombreItem, int cantidad, float ttlCont) {
            this.id = id;
            this.nombreItem = nombreItem;
            this.cantidad = cantidad;
            this.ttlCont = ttlCont;
        }
        
        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getNombreItem() {
            return nombreItem;
        }

        public void setNombreItem(String nombreItem) {
            this.nombreItem = nombreItem;
        }

        public int getCantidad() {
            return cantidad;
        }

        public void setCantidad(int cantidad) {
            this.cantidad = cantidad;
        }

        public float getTtlCont() {
            return ttlCont;
        }

        public void setTtlCont(float ttlCont) {
            this.ttlCont = ttlCont;
        }        
        
    } // </editor-fold>
    private ArrayList<itemModelo> items= new ArrayList<itemModelo>();

    @Override
    public int getRowCount() {
        return items.size();
    }

    @Override
    public int getColumnCount() {
        return ColumnName.length;
    }
    
    @Override
    public String getColumnName(int c){
        return ColumnName[c];
    }
    
    @Override
    public Class<?> getColumnClass(int c){
        // Devuelve la clase que hay en cada columna.
        switch (c)
        {
            case 0:
                // La columna cero contiene el id del item, que es
                // un Integer
                return Integer.class;
            case 1:
                // La columna uno contiene el nombre del item, que es
                // un String
                return String.class;
            case 2:
                // La columna dos contine la cantidad del item, que es un
                // Integer (no vale int, debe ser una clase)
                return Integer.class;
            case 3:
                //La columna tres contiene los grados de alcohol total del item que es un
                // Float (no vale float, debe ser una clase)
                return Float.class;
            default:
                // Devuelve una clase Object por defecto.
                return Object.class;
        }
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        itemModelo item= items.get(rowIndex);
        switch(columnIndex){
            case 0:
                return new Integer(item.getId());
            case 1:
                return item.getNombreItem();
            case 2:
                return new Integer(item.getCantidad());
            case 3:
                return new Float(item.getTtlCont());
            default:
                return null;
        }
    }
    
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex){
        itemModelo item= items.get(rowIndex);
        if (aValue == null) {
            this.setValueAt(0, rowIndex, columnIndex);
            return;
        }
        String value = aValue.toString();
        switch(columnIndex){
            case 0:
                this.LlenarCeldas(item, value);
                break;
            case 1:
                this.LlenarCeldas(item, value);
                break;
            case 2:
                int cant= Integer.parseInt(value);
                float ttlCont=0;
                if(item.getCantidad()==0){
                    item.setCantidad(cant);
                    ttlCont= cant*item.getTtlCont();
                    item.setTtlCont(ttlCont);
                }
                else{
                    ttlCont=cant*(item.getTtlCont()/item.getCantidad());
                    item.setCantidad(cant);
                    item.setTtlCont(ttlCont);
                }
                break;
            case 3:
                item.getTtlCont();
                break;
            default:
                break;
        }
        
        this.insertarFilaNueva(rowIndex);
        // Disparamos el Evento TableDataChanged (La tabla ha cambiado)
        fireTableDataChanged();
    }
    
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        if(columnIndex !=3){
            return true;
        }
        else{
            return false;
        }
    }
    
    private void LlenarCeldas(itemModelo item, String value){
        String [] producto= Producto.searchProducto(value);
        if(producto == null || this.itemExiste(Integer.parseInt(producto[0]))){
            return;
        }
        else{
            item.setId(Integer.parseInt(producto[0]));
            item.setNombreItem(producto[1]);
            item.setCantidad(0);
            item.setTtlCont(Float.parseFloat(producto[4].toString()));
        }
    }
    
    private boolean itemExiste(int id){
        if(items.size()!=1){
            for (itemModelo item : items) {
                if(item.getId()==id){
                    return true;
                }
            }
        }
        return false;
    }
    
    public void insertarFilaNueva(int rowIndex){
        int fila= rowIndex+1;
        if(fila == 0){
            items.add(new itemModelo(0, "", 0, 0));
            fireTableDataChanged();
        }
        else{
            if(fila == this.items.size()){
                itemModelo item= this.items.get(rowIndex);
                if(!item.getNombreItem().isEmpty() && item.getCantidad() != 0){
                    items.add(new itemModelo(0, "", 0, 0));
                    this.rowAdd = true;
                    fireTableDataChanged();
                }
            }
        }
    }
    
    public void setItems(String[][] datos){
        items.removeAll(items);
        for (String[] item : datos) {
            int id= Integer.valueOf(item[0].toString());
            String nombre= item[1].toString();
            int cant= Integer.valueOf(item[2].toString());
            float grados= Float.valueOf(item[3].toString());
            items.add(new itemModelo(id, nombre, cant, grados));
        }
        items.add(new itemModelo(0, "", 0, 0));
        fireTableDataChanged();
    }
    
    public void borraItem(int fila){
        // Se borra la fila 
        this.items.remove(fila);
        fireTableDataChanged();
    }
}

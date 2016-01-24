package upc.edu.pe.type;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Miguel Cardoso on 10/10/2015.
 */
public class Familia implements Serializable {

    private int id_familia;
    private String descripcion;

    private List<Producto> listProductos;

    public Familia() {
    }

    public int getId_familia() {
        return this.id_familia;
    }

    public void setId_familia(int id_familia) {
        this.id_familia = id_familia;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<Producto> getListProductos() {
        return listProductos;
    }

    public void setListProductos(List<Producto> listProductos) {
        this.listProductos = listProductos;
    }
}
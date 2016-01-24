package upc.edu.pe.type;

import java.io.Serializable;

/**
 * Created by Miguel Cardoso on 10/10/2015.
 */
public class DetallePedido  implements Serializable {


    private int item;

    private int cantidad;

    private Double precio;

    private Double total;

    private Producto Producto;

    private Pedido Pedido;

    public DetallePedido() {
    }

    public int getItem() {
        return item;
    }

    public void setItem(int item) {
        this.item = item;
    }

    public int getCantidad() {
        return this.cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Producto getProducto() {
        return Producto;
    }

    public void setProducto(Producto Producto) {
        this.Producto = Producto;
    }

    public Pedido getPedido() {
        return Pedido;
    }

    public void setPedido(Pedido Pedido) {
        this.Pedido = Pedido;
    }
}

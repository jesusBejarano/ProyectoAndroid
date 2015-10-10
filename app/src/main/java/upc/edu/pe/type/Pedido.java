package upc.edu.pe.type;

/**
 * Created by Miguel Cardoso on 09/10/2015.
 */
public class Pedido {
    private int id_pedido;
    private int cantidad;
    private String direccion;
    private String estado;
    private String fecha;
    private Double monto;

   /* private List<DetallePedido> listDetallePedidos;*/
    private Distrito Distrito;

    private Cliente Cliente;

    public Pedido() {
    }

    public int getId_pedido() {
        return this.id_pedido;
    }

    public void setId_pedido(int id_pedido) {
        this.id_pedido = id_pedido;
    }

    public int getCantidad() {
        return this.cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getDireccion() {
        return this.direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getEstado() {
        return this.estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getFecha() {
        return this.fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

  /*  public List<DetallePedido> getListDetallePedidos() {
        return listDetallePedidos;
    }

    public void setListDetallePedidos(List<DetallePedido> listDetallePedidos) {
        this.listDetallePedidos = listDetallePedidos;
    }*/

    public Distrito getDistrito() {
        return Distrito;
    }

    public void setDistrito(Distrito Distrito) {
        this.Distrito = Distrito;
    }

    public Cliente getCliente() {
        return Cliente;
    }

    public void setCliente(Cliente Cliente) {
        this.Cliente = Cliente;
    }
}

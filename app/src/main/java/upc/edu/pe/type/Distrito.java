package upc.edu.pe.type;

import java.io.Serializable;

/**
 * Created by Miguel Cardoso on 07/10/2015.
 */
public class Distrito implements Serializable {
    private int id_distrito;
    private String nombre;
/*
    private List<Sucursal> listSucursal2;
    private List<Pedido> listPedidos;
    private List<Sucursal> listSucursal;*/

    public Distrito() {
    }

    public Distrito(Integer id_distrito,String nombre) {
        this.id_distrito = id_distrito;
        this.nombre = nombre;
    }

    public int getId_distrito() {
        return this.id_distrito;
    }

    public void setId_distrito(int id_distrito) {
        this.id_distrito = id_distrito;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

 /*   public List<Sucursal> getListSucursal2() {
        return listSucursal2;
    }

    public void setListSucursal2(List<Sucursal> listSucursal2) {
        this.listSucursal2 = listSucursal2;
    }

    public List<Pedido> getListPedidos() {
        return listPedidos;
    }

    public void setListPedidos(List<Pedido> listPedidos) {
        this.listPedidos = listPedidos;
    }

    public List<Sucursal> getListSucursal() {
        return listSucursal;
    }

    public void setListSucursal(List<Sucursal> listSucursal) {
        this.listSucursal = listSucursal;
    }*/
}

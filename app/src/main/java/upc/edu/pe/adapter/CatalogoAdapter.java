package upc.edu.pe.adapter;

import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import upc.edu.pe.proyecto.R;
import upc.edu.pe.type.Carrito;
import upc.edu.pe.type.Producto;
import upc.edu.pe.utils.CarritoDAO;
import upc.edu.pe.utils.DAOExcepcion;

/**
 * Created by Miguel Cardoso on 05/02/2016.
 */
public class CatalogoAdapter extends RecyclerView.Adapter<CatalogoAdapter.CatalogoViewHolder>  {

    private List<Producto> items;
    private CarritoDAO carritoDao;
    private Carrito carrito;

    public static class CatalogoViewHolder extends RecyclerView.ViewHolder {
        // Campos respectivos de un item
        public ImageView imagen;
        public TextView nombre;
        public TextView descripcion;
        public TextView precio;
        public ImageView carrito;

        public CatalogoViewHolder(View v) {
            super(v);
            imagen = (ImageView) v.findViewById(R.id.imagen);
            nombre = (TextView) v.findViewById(R.id.nombre);
            descripcion = (TextView) v.findViewById(R.id.descripcion);
            precio = (TextView) v.findViewById(R.id.precio);
            carrito = (ImageView) v.findViewById(R.id.carrito);
        }
    }

    public CatalogoAdapter(List<Producto> items) {
        this.items = items;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public CatalogoViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.content_catalogo, viewGroup, false);
        return new CatalogoViewHolder(v);
    }

    @Override
    public void onBindViewHolder(CatalogoViewHolder viewHolder, int i) {
        final int posicion = i;
        viewHolder.imagen.setImageResource(getImage(items.get(i).getImagen()));
        viewHolder.nombre.setText(items.get(i).getNombre());
        viewHolder.descripcion.setText(items.get(i).getDescripcion());
        viewHolder.precio.setText("Precio : s/." + String.valueOf(items.get(i).getPrecio()));

        viewHolder.carrito.setClickable(true);
        viewHolder.carrito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Producto prod = items.get(posicion);
                    carritoDao = CarritoDAO.getInstance(v.getContext());
                    carrito = carritoDao.obtener(prod.getId_producto());
                    if (carrito != null) {
                        carrito.setCantidad(carrito.getCantidad() + 1);
                        carrito.setTotal(carrito.getTotal() * carrito.getCantidad());
                        carritoDao.actualizar(carrito);
                        mostrarMensaje(v, "Se Actualizó su carrito con una unidad más");

                    } else {
                        carrito = new Carrito();
                        carrito.setIdProducto(prod.getId_producto());
                        carrito.setNombre(prod.getNombre());
                        carrito.setCantidad(1);
                        carrito.setPrecio(prod.getPrecio());
                        carrito.setTotal(prod.getPrecio() * 1);
                        carritoDao.insertar(carrito);
                        mostrarMensaje(v, "Se Agregó producto a su carrito");
                    }
                } catch (DAOExcepcion daoExcepcion) {
                    Log.i("Error Agregar Carrito", "====> " + daoExcepcion.getMessage());
                }


            }
        });
    }

    public int getImage(String imagen){
        int imageURL = R.drawable.pendiente;
        if(imagen.equalsIgnoreCase("corona.png")){
            imageURL = R.drawable.corona;
        }else if(imagen.equalsIgnoreCase("appleton.png")){
            imageURL = R.drawable.appleton;
        }else if(imagen.equalsIgnoreCase("baileys.png")){
            imageURL = R.drawable.baileys;
        }else if(imagen.equalsIgnoreCase("chivas_regal.png")){
            imageURL = R.drawable.chivasregal;
        }else if(imagen.equalsIgnoreCase("cuatro_gallos.png")){
            imageURL = R.drawable.cuatrogallos;
        }else if(imagen.equalsIgnoreCase("skyy_vodka.png")){
            imageURL = R.drawable.skyyvodka;
        }
        return imageURL;
    }

    private void mostrarMensaje(View view,String msg){
        Snackbar snackbar = Snackbar.make(view, msg, Snackbar.LENGTH_SHORT);
        snackbar.show();
    }


}

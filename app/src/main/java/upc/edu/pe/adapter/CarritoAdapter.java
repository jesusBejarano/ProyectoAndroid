package upc.edu.pe.adapter;

import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import upc.edu.pe.proyecto.R;
import upc.edu.pe.type.Carrito;
import upc.edu.pe.utils.CarritoDAO;
import upc.edu.pe.utils.DAOExcepcion;

/**
 * Created by Miguel Cardoso on 16/02/2016.
 */
public class CarritoAdapter extends RecyclerView.Adapter<CarritoAdapter.CarritoViewHolder>  {

    private List<Carrito> items;
    private CarritoDAO carritoDao;
    private Carrito carrito;

    public static class CarritoViewHolder extends RecyclerView.ViewHolder{
        // Campos respectivos de un item
        public ImageView imagen;
        public TextView nombre;
        public TextView cantidad;
        public TextView total;
        public ImageView carrito;


        public CarritoViewHolder(View v) {
            super(v);
            imagen = (ImageView) v.findViewById(R.id.imagenC);
            nombre = (TextView) v.findViewById(R.id.txtProductoC);
            cantidad = (TextView) v.findViewById(R.id.txtCantidadC);
            total = (TextView) v.findViewById(R.id.txtTotalC);
            carrito = (ImageView) v.findViewById(R.id.carritoC);
        }

    }

    public CarritoAdapter(List<Carrito> items) {
        this.items = items;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public CarritoViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.content_carrito, viewGroup, false);
        return new CarritoViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final CarritoViewHolder viewHolder, int i) {
        final int posicion = i;
        viewHolder.imagen.setImageResource(getImage(items.get(i).getNombre()));
        viewHolder.nombre.setText(items.get(i).getNombre());
        viewHolder.cantidad.setText("Cantidad : " + String.valueOf(items.get(i).getCantidad()));
        viewHolder.total.setText("subtotal : s/." + String.valueOf(items.get(i).getTotal()));


        viewHolder.carrito.setClickable(true);
        viewHolder.carrito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "El producto es : " + items.get(posicion).getNombre(), Toast.LENGTH_SHORT).show();
                try {
                    Carrito carrit = items.get(posicion);
                    carritoDao = CarritoDAO.getInstance(v.getContext());
                    if(carrit.getCantidad() == 1){
                        carritoDao.eliminar(carrit.getIdProducto());
                        mostrarMensaje(v, "Se Eliminó el producto de su carrito");
                        items.remove(posicion);
                        notifyItemRemoved(posicion);

                    }else{
                        carrit.setCantidad(carrit.getCantidad() - 1);
                        carrit.setTotal(carrit.getPrecio() * carrit.getCantidad());
                        carritoDao.actualizar(carrit);
                        mostrarMensaje(v, "Se Actualizó su carrito con una unidad menos");
                        items.set(posicion,carrit);
                        notifyItemChanged(posicion);
                    }
                } catch (DAOExcepcion daoExcepcion) {
                    Log.i("Error Eliminar  Carrito", "====> " + daoExcepcion.getMessage());
                }


            }
        });
    }

    private int getImage(String imagen){
        int imageURL = R.drawable.pendiente;
        if(imagen.equalsIgnoreCase("CORONA")){
            imageURL = R.drawable.corona;
        }else if(imagen.equalsIgnoreCase("APPLETON")){
            imageURL = R.drawable.appleton;
        }else if(imagen.equalsIgnoreCase("BAILEYS")){
            imageURL = R.drawable.baileys;
        }else if(imagen.equalsIgnoreCase("CHIVAS REGAL")){
            imageURL = R.drawable.chivasregal;
        }else if(imagen.equalsIgnoreCase("CUATRO GALLOS")){
            imageURL = R.drawable.cuatrogallos;
        }else if(imagen.equalsIgnoreCase("SKYY VODKA")){
            imageURL = R.drawable.skyyvodka;
        }
        return imageURL;
    }

    private void mostrarMensaje(View view,String msg){
        Snackbar snackbar = Snackbar.make(view, msg, Snackbar.LENGTH_SHORT);
        snackbar.show();
    }


}
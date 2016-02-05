package upc.edu.pe.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import upc.edu.pe.proyecto.R;
import upc.edu.pe.type.Producto;

/**
 * Created by Miguel Cardoso on 05/02/2016.
 */
public class CatalogoAdapter extends RecyclerView.Adapter<CatalogoAdapter.CatalogoViewHolder>  {

    private List<Producto> items;

    public static class CatalogoViewHolder extends RecyclerView.ViewHolder {
        // Campos respectivos de un item
        public ImageView imagen;
        public TextView nombre;
        public TextView precio;

        public CatalogoViewHolder(View v) {
            super(v);
            imagen = (ImageView) v.findViewById(R.id.imagen);
            nombre = (TextView) v.findViewById(R.id.nombre);
            precio = (TextView) v.findViewById(R.id.precio);
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
        viewHolder.imagen.setImageResource(getImage(items.get(i).getImagen()));
        viewHolder.nombre.setText(items.get(i).getNombre());
        viewHolder.precio.setText("Precio (s/.):"+String.valueOf(items.get(i).getPrecio()));
    }

    private int getImage(String imagen){
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



}

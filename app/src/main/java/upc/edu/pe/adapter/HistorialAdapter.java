package upc.edu.pe.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import upc.edu.pe.proyecto.R;
import upc.edu.pe.type.Pedido;

/**
 * Created by Miguel Cardoso on 09/10/2015.
 */
public class HistorialAdapter extends ArrayAdapter<Pedido> {
    public HistorialAdapter(Context context, List<Pedido> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        //Obteniendo una instancia del inflater
        LayoutInflater inflater = (LayoutInflater)getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //Salvando la referencia del View de la fila
        View listItemView = convertView;

        //Comprobando si el View no existe
        if (null == convertView) {
            //Si no existe, entonces inflarlo con image_list_view.xml
            listItemView = inflater.inflate(
                    R.layout.lista_personalizada,
                    parent,
                    false);
        }

        //Obteniendo instancias de los elementos
        TextView titulo = (TextView)listItemView.findViewById(R.id.text1);
        TextView subtitulo = (TextView)listItemView.findViewById(R.id.text2);
        ImageView categoria = (ImageView)listItemView.findViewById(R.id.category);


        //Obteniendo instancia de la Tarea en la posición actual
        Pedido item = getItem(position);

        int imageURL = R.drawable.pendiente;
        if(item.getEstado().equalsIgnoreCase("A")){
            imageURL = R.drawable.atendido;
        }else{
            imageURL = R.drawable.pendiente;
        }

        titulo.setText(item.getDireccion() + "-" + item.getDistrito().getNombre());
        subtitulo.setText(item.getFecha());
        categoria.setImageResource(imageURL);

        //Devolver al ListView la fila creada
        return listItemView;

    }
}

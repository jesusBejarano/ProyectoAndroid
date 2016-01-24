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
import upc.edu.pe.type.DetallePedido;
import upc.edu.pe.type.Pedido;

/**
 * Created by Miguel Cardoso on 10/10/2015.
 */
public class DetalleAdapter extends ArrayAdapter<DetallePedido> {
    public DetalleAdapter(Context context, List<DetallePedido> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //Obteniendo una instancia del inflater
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //Salvando la referencia del View de la fila
        View listItemView = convertView;

        //Comprobando si el View no existe
        if (null == convertView) {
            //Si no existe, entonces inflarlo con image_list_view.xml
            listItemView = inflater.inflate(
                    R.layout.lista_personalizada_detalle,
                    parent,
                    false);
        }

        //Obteniendo instancias de los elementos
        TextView nombre = (TextView) listItemView.findViewById(R.id.text1);
        TextView cantidad = (TextView) listItemView.findViewById(R.id.text2);
        TextView subtotal = (TextView) listItemView.findViewById(R.id.text3);


        //Obteniendo instancia de la Tarea en la posición actual
        DetallePedido item = getItem(position);


        nombre.setText(item.getProducto().getNombre());
        cantidad.setText(item.getCantidad());
        subtotal.setText(item.getTotal() + "");

        //Devolver al ListView la fila creada
        return listItemView;

    }
}

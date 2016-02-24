package upc.edu.pe.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import upc.edu.pe.proyecto.DetalleActivity;
import upc.edu.pe.proyecto.R;
import upc.edu.pe.task.ActualizarPedidoTask;
import upc.edu.pe.type.Pedido;

/**
 * Created by jesus on 21/02/2016.
 */
public class PedidoPendienteAdapter extends RecyclerView.Adapter<PedidoPendienteAdapter.PedidoViewHolder>   {



    private List<Pedido> items;
    private Context context;

    public static class PedidoViewHolder extends RecyclerView.ViewHolder {
        // Campos respectivos de un item
        public TextView nombre;
        public TextView precio;
        public Button btnatenderpedido;


        private Button btnVer;

        public PedidoViewHolder(View v) {
            super(v);
            nombre = (TextView) v.findViewById(R.id.nombre);
            precio = (TextView) v.findViewById(R.id.precio);
            btnatenderpedido=(Button) v.findViewById(R.id.btnAtenderPedido);

            btnVer=(Button) v.findViewById(R.id.btnVerPedido);
        }
    }

    public PedidoPendienteAdapter(List<Pedido> items, Context context) {
        this.items = items;
        this.context=context;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public PedidoViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.content_pedidopendiente, viewGroup, false);
        return new PedidoViewHolder(v);
    }

    @Override
    public void onBindViewHolder(PedidoViewHolder viewHolder, int i) {
        final int posicion = i;
        viewHolder.nombre.setText(items.get(i).getCliente().getNombreApe());
        viewHolder.precio.setText("Total (s/.):"+String.valueOf(items.get(i).getMonto()));

        viewHolder.btnatenderpedido.setClickable(true);
        viewHolder.btnatenderpedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "El pedido  : " + items.get(posicion).getId_pedido(), Toast.LENGTH_SHORT).show();
                try {
                    new ActualizarPedidoTask(context, items.get(posicion)).execute();
                    items.remove(posicion);
                    notifyDataSetChanged();
                    //new CatalogoTask(getActivity(),reciclador,adaptador).execute();


                } catch (Exception e) {
                    Log.i("Error MOdificar Pedido", "====> " + e.getMessage());
                }


            }
        });


        viewHolder.btnVer.setClickable(true);
        viewHolder.btnVer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Parametro : ",items.get(posicion).getId_pedido()+"");
                Intent intent = new Intent(context, DetalleActivity.class);
                intent.putExtra("pedidoId",items.get(posicion).getId_pedido()+"");
                context.startActivity(intent);
            }
        });




    }


}

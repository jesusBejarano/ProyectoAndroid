package upc.edu.pe.Fragmentos;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import upc.edu.pe.proyecto.R;
import upc.edu.pe.task.PedidoTask;

/**
 * Created by jesus on 21/02/2016.
 */
public class FragmentoPedidos extends Fragment {

    private RecyclerView reciclador;
    private LinearLayoutManager layoutManager;
    private RecyclerView.Adapter adaptador;

    private Button btnAtender;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.pedido_main, container, false);



        reciclador = (RecyclerView) view.findViewById(R.id.reciclerpedidos);
        reciclador.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        reciclador.setLayoutManager(layoutManager);





        // new CatalogoTask(getActivity(),reciclador).execute("");
        new PedidoTask(getActivity(),reciclador,adaptador).execute();



        /*adaptador = new CatalogoAdapter();
        reciclador.setAdapter(adaptador);*/

        /** Obtener el Recycler
         recycler = (RecyclerView) view.findViewById(R.id.reciclador);
         recycler.setHasFixedSize(true);
         // Usar un administrador para LinearLayout
         lManager = new LinearLayoutManager(getActivity());
         recycler.setLayoutManager(lManager);

         new CatalogoTask(getActivity(),recycler,adapter).execute();**/

        return view;
    }

}

package upc.edu.pe.Fragmentos;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import upc.edu.pe.proyecto.R;
import upc.edu.pe.task.CatalogoTask;

/**
 * Created by jesus on 7/02/2016.
 */
public class FragmentoProductos extends Fragment {


    //private RecyclerView recycler;
   // private RecyclerView.Adapter adapter;
   // private RecyclerView.LayoutManager lManager;
    private RecyclerView reciclador;
    private LinearLayoutManager layoutManager;
    private RecyclerView.Adapter adaptador;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.catalogo_main, container, false);


        reciclador = (RecyclerView) view.findViewById(R.id.reciclador);
        reciclador.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        reciclador.setLayoutManager(layoutManager);

       // new CatalogoTask(getActivity(),reciclador).execute("");
        new CatalogoTask(getActivity(),reciclador,adaptador).execute();



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

package upc.edu.pe.Fragmentos;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import upc.edu.pe.proyecto.R;
import upc.edu.pe.task.HistorialTask;

/**
 * Created by jesus on 23/02/2016.
 */
public class FragmentoHistorial extends Fragment {

    //Variables
    private ListView listView;
    private ArrayAdapter arrayAdapter;
    private Button btnRegresar;
    //Otros
    public String clienteId;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.consulta_main, container, false);


        SharedPreferences prefs =  getActivity().getSharedPreferences("MyCliente", Context.MODE_PRIVATE);
        //Obetener Intent
        Intent intent = getActivity().getIntent();
        //Extrayendo el extra de tipo cadena
        clienteId = prefs.getString("idCliente", "0");
        //Inicializamos los tipos de la variables
        //btnRegresar = (Button) view.findViewById(R.id.btnRegresar);
        listView = (ListView) view.findViewById(R.id.lista);

        new HistorialTask(getActivity(),listView,arrayAdapter).execute(clienteId);

        return view;
    }

}

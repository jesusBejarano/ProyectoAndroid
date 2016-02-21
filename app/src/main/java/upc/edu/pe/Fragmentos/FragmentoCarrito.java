package upc.edu.pe.Fragmentos;


import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import upc.edu.pe.adapter.CarritoAdapter;
import upc.edu.pe.proyecto.R;
import upc.edu.pe.type.Carrito;
import upc.edu.pe.utils.CarritoDAO;
import upc.edu.pe.utils.DAOExcepcion;


/**
 * Created by jesus on 21/02/2016.
 */
public class FragmentoCarrito extends Fragment {


    private RecyclerView reciclador;
    private LinearLayoutManager layoutManager;
    private RecyclerView.Adapter adaptador;
    private CarritoDAO carritoDao;
    private List<Carrito> listCarrito;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.carrito_main, container, false);


        reciclador = (RecyclerView) view.findViewById(R.id.reciclador_carrito);
        reciclador.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        reciclador.setLayoutManager(layoutManager);


        //Obtener Carrito
        listCarrito = obtenerCarrito();
        //Inicializar el adaptador
        adaptador = new CarritoAdapter(listCarrito);
        reciclador.setAdapter(adaptador);
        //new CarritoTask(getActivity(),reciclador,adaptador).execute();




        return view;
    }

    private List<Carrito> obtenerCarrito(){
        List<Carrito> list = new ArrayList<Carrito>();
        carritoDao = CarritoDAO.getInstance(getActivity());
        try {
            list = carritoDao.obtenerTodos();
        } catch (DAOExcepcion daoExcepcion) {
            Log.i("Listar Carrito", "====> " + daoExcepcion.getMessage());
        }
        return list;
    }

    private void eliminarCarrrito(){
        carritoDao = CarritoDAO.getInstance(getActivity());
        try {
            carritoDao.eliminarTodos();
            listCarrito.clear();
            adaptador.notifyDataSetChanged();
        } catch (DAOExcepcion daoExcepcion) {
            Log.i("Eliminar Carrito", "====> " + daoExcepcion.getMessage());
        }
    }
}

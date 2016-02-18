package upc.edu.pe.proyecto;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import upc.edu.pe.adapter.CarritoAdapter;
import upc.edu.pe.type.Carrito;
import upc.edu.pe.utils.CarritoDAO;
import upc.edu.pe.utils.DAOExcepcion;

public class CarritoActivity extends AppCompatActivity {

    private RecyclerView recycler;
    private List<Carrito> listCarrito;
    private RecyclerView.LayoutManager lManager;
    private RecyclerView.Adapter mAdapter;
    private CarritoDAO carritoDao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.carrito_main);
        setToolbar();
        // Obtener el Recycler
        recycler = (RecyclerView) findViewById(R.id.reciclador_carrito);
        recycler.setHasFixedSize(true);
        // Usar un administrador para LinearLayout
        lManager = new LinearLayoutManager(this);
        recycler.setLayoutManager(lManager);
        //Obtener Carrito
        listCarrito = obtenerCarrito();
        //Inicializar el adaptador
        mAdapter = new CarritoAdapter(listCarrito);
        recycler.setAdapter(mAdapter);

    }

    private void setToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_carrito, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_settings:
                return true;
            case R.id.action_comprar:
                showSnackBar("Comprar");
                return true;
            case R.id.action_eliminar:
                eliminarCarrrito();
                showSnackBar("Se eliminó todo su carrito");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private List<Carrito> obtenerCarrito(){
        List<Carrito> list = new ArrayList<Carrito>();
        carritoDao = CarritoDAO.getInstance(this);
        try {
            list = carritoDao.obtenerTodos();
        } catch (DAOExcepcion daoExcepcion) {
            Log.i("Listar Carrito", "====> " + daoExcepcion.getMessage());
        }
        return list;
    }

    private void eliminarCarrrito(){
        carritoDao = CarritoDAO.getInstance(getBaseContext());
        try {
            carritoDao.eliminarTodos();
            listCarrito.clear();
            mAdapter.notifyDataSetChanged();
        } catch (DAOExcepcion daoExcepcion) {
            Log.i("Eliminar Carrito", "====> " + daoExcepcion.getMessage());
        }
    }

    private void showSnackBar(String msg) {
        Snackbar.make(findViewById(R.id.coordinator), msg, Snackbar.LENGTH_LONG).show();
    }
}

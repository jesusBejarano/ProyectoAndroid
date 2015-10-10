package upc.edu.pe.proyecto;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import upc.edu.pe.task.DistritoTask;
import upc.edu.pe.task.HistorialTask;

/**
 * Created by Miguel Cardoso on 09/10/2015.
 */
public class HistorialActivity extends Activity {

    //Variables
    private ListView listView;
    private ArrayAdapter arrayAdapter;
    private Button btnRegresar;
    //Otros
    public String clienteId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.consulta_main);
        SharedPreferences prefs =  getSharedPreferences("MyCliente", Context.MODE_PRIVATE);
        //Obetener Intent
        Intent intent = getIntent();
        //Extrayendo el extra de tipo cadena
        clienteId = prefs.getString("idCliente", "0");
        //Inicializamos los tipos de la variables
        btnRegresar = (Button) findViewById(R.id.btnRegresar);
        listView = (ListView) findViewById(R.id.lista);

        new HistorialTask(HistorialActivity.this,listView,arrayAdapter).execute(clienteId);

        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarActivity(MenuActivity.class);
            }
        });

    }

    private void mostrarActivity(Class view){
        Intent i = new Intent(this, view);
        startActivity(i);
    }
}

package upc.edu.pe.proyecto;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;

import upc.edu.pe.type.Cliente;

/**
 * Created by Miguel Cardoso on 19/09/2015.
 */
public class MenuActivity extends Activity {

    //Variables
    private Button btnMantenimiento;
    private TextView btnSalir;
    private Button btnConsultas;
    private Button btnCatalogo;

    //Otros
    private Cliente cliente = new Cliente();
    private Gson gson = new Gson();
    String idCliente;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_main);
        final SharedPreferences prefs =  getSharedPreferences("MyCliente", Context.MODE_PRIVATE);
        //Obetener Intent
        Intent intent = getIntent();
        //Extrayendo el extra de tipo cadena
        String clienteId = intent.getStringExtra("cliente");

        if(clienteId != null && !clienteId.isEmpty()){
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("idCliente",clienteId.trim());
            editor.commit();
        }


        //Inicializamos las variables
        btnMantenimiento = (Button)findViewById(R.id.btnMantenimiento);
        btnSalir = (TextView)findViewById(R.id.btnExit);
        btnConsultas = (Button)findViewById(R.id.btnConsultar);
        btnCatalogo = (Button)findViewById(R.id.btnCatalogo);
        //Capturamos evento del Boton
        btnMantenimiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarActivity(MantenimientoActivity.class);
            }
        });

        btnConsultas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarActivity(HistorialActivity.class);
            }
        });

        btnCatalogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarActivity(CatalogoActivity.class);
            }
        });

        btnSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = prefs.edit();
                editor.clear();
                editor.commit();
                mostrarActivity(MainActivity.class);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void mostrarActivity(Class view){
        Intent i = new Intent(this, view);
        startActivity(i);
    }
}

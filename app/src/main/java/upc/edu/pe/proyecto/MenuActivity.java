package upc.edu.pe.proyecto;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Miguel Cardoso on 19/09/2015.
 */
public class MenuActivity extends Activity {

    //Variables
    private Button btnMantenimiento;
    private Button btnProductos;
    private Button btnConsultas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_main);

        //Inicializamos las variables
        btnMantenimiento = (Button)findViewById(R.id.buttonMantenimiento);
        btnProductos = (Button)findViewById(R.id.buttonProductos);
        btnConsultas = (Button)findViewById(R.id.buttonConsultas);

    }

    @Override
    protected void onResume() {
        super.onResume();
        //Capturamos evento del Boton
        btnMantenimiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarActivity(MantenimientoActivity.class);
            }
        });
    }

    private void mostrarActivity(Class view){
        Intent i = new Intent(this, view);
        startActivity(i);
    }
}

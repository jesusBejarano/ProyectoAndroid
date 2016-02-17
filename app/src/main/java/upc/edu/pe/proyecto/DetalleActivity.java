package upc.edu.pe.proyecto;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TextView;

import upc.edu.pe.task.DetalleTask;
import upc.edu.pe.task.HistorialTask;

/**
 * Created by Miguel Cardoso on 10/10/2015.
 */
public class DetalleActivity extends Activity {
    //Variables
    private TableLayout layout;
    private TextView total;
    //Otros
    public String pedidoId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detalle_pedido_main);
        //Obetener Intent
        Intent intent = getIntent();
        //Extrayendo el extra de tipo cadena
        pedidoId = intent.getStringExtra("pedidoId");
        Log.d("PEDIDO : ", pedidoId);

        layout = (TableLayout) findViewById(R.id.contenedor);

        total = (TextView) findViewById(R.id.txtTotal);
        new DetalleTask(DetalleActivity.this,layout,total).execute(pedidoId);
    }

    private void mostrarActivity(Class view){
        Intent i = new Intent(this, view);
        startActivity(i);
    }
}

package upc.edu.pe.task;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import upc.edu.pe.adapter.HistorialAdapter;
import upc.edu.pe.proyecto.MainActivity;
import upc.edu.pe.proyecto.MenuActivity;
import upc.edu.pe.proyecto.R;
import upc.edu.pe.type.Distrito;
import upc.edu.pe.type.Pedido;
import upc.edu.pe.utils.HttpClientUtil;

/**
 * Created by Miguel Cardoso on 09/10/2015.
 */
public class HistorialTask extends AsyncTask<String,Void,String> {

    private Context context;
    private ListView listView;
    private ArrayAdapter arrayAdapter;
    private ProgressDialog progressDialog;

    //Variables
    private Gson json = new Gson();
    private List<Pedido> listPedidos;

    public HistorialTask(Context context,ListView listView, ArrayAdapter arrayAdapter) {
        this.context = context;
        this.listView = listView;
        this.arrayAdapter = arrayAdapter;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = ProgressDialog.show(
                context, "Por favor espere", "Procesando...");
    }


    @Override
    protected String doInBackground(String... params) {
        HttpClientUtil RestClient = new HttpClientUtil();
        String mensaje="";
        try {
            String result = RestClient.GET("pedidos/todo/"+params[0]);
            if(result != null || !result.isEmpty()){
                Type type = new TypeToken<List<Pedido>>(){}.getType();
                listPedidos = json.fromJson(result, type);
                mensaje = result;
            }

        } catch (Exception e) {
            mensaje = null;
            e.printStackTrace();
            Log.d("Error en Task Pedido:", e.getMessage());
        }
        return mensaje;
    }

    @Override
    protected void onPostExecute(String result) {
        if(result != null){
            progressDialog.dismiss();
            if(listPedidos.isEmpty()){
                AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                dialog.setTitle(R.string.dialog_header);
                dialog.setMessage("No tiene historial para consultar.");
                dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent i = new Intent(context, MenuActivity.class);
                            context.startActivity(i);
                        }
                    });
                dialog.show();
            }else{
                arrayAdapter = new HistorialAdapter(context,listPedidos);
                listView.setAdapter(arrayAdapter);
            }
        } else {
            AlertDialog.Builder dialog = new AlertDialog.Builder(context);
            dialog.setTitle(R.string.dialog_header);
            dialog.setMessage("Error en cargar Historial de Pedidos.");
            dialog.show();
        }
    }
}

package upc.edu.pe.task;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import upc.edu.pe.adapter.CatalogoAdapter;
import upc.edu.pe.adapter.PedidoAdapter;
import upc.edu.pe.proyecto.MenuActivity;
import upc.edu.pe.type.Pedido;
import upc.edu.pe.utils.HttpClientUtil;

/**
 * Created by jesus on 21/02/2016.
 */
public class PedidosTask extends AsyncTask<String,Void,String> {

    private Context context;
    private ProgressDialog progressDialog;
    private RecyclerView recycler;
    private RecyclerView.Adapter adapter;


    //Variables
    private Gson json = new Gson();
    private List<Pedido> listPedidos;


    public PedidosTask(Context context, RecyclerView listView, RecyclerView.Adapter arrayAdapter) {

        this.context = context;
        this.recycler = listView;
        this.adapter = arrayAdapter;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = ProgressDialog.show(
                context, "Por favor espere", "Cargando...");
    }


    @Override
    protected String doInBackground(String... params) {
        HttpClientUtil RestClient = new HttpClientUtil();
        String mensaje="";
        try {
            String result = RestClient.GET("pedidos/todos");
            Thread.sleep(2000);
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


            if(listPedidos.isEmpty()){

                AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                dialog.setTitle("Mensaje");
                dialog.setMessage("No hay Pedidos En Estado Pendiente.");
                dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = new Intent(context, MenuActivity.class);
                        context.startActivity(i);
                    }
                });
                dialog.show();
            }
            else{
                //Creamos el adaptador
                adapter = new PedidoAdapter(listPedidos,context);
                recycler.setAdapter(adapter);
                progressDialog.dismiss();
            }
        } else {
            progressDialog.dismiss();
            AlertDialog.Builder dialog = new AlertDialog.Builder(context);
            dialog.setTitle("Mensaje");
            dialog.setMessage("Error en Cargar Las Ordenes de Pedido.");
            dialog.show();
        }
    }
}

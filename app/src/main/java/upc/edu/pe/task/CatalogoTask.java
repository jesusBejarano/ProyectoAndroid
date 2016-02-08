package upc.edu.pe.task;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import upc.edu.pe.adapter.CatalogoAdapter;
import upc.edu.pe.adapter.HistorialAdapter;
import upc.edu.pe.proyecto.MenuActivity;
import upc.edu.pe.proyecto.R;
import upc.edu.pe.type.Pedido;
import upc.edu.pe.type.Producto;
import upc.edu.pe.utils.HttpClientUtil;

/**
 * Created by Miguel Cardoso on 05/02/2016.
 */
public class CatalogoTask extends AsyncTask<String,Void,String> {

    private Context context;
    private ProgressDialog progressDialog;
    private RecyclerView recycler;
    private RecyclerView.Adapter adapter;


    //Variables
    private Gson json = new Gson();
    private List<Producto> listProductos;


    public CatalogoTask(Context context,RecyclerView listView, RecyclerView.Adapter arrayAdapter) {
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
            String result = RestClient.GET("productos");
            if(result != null || !result.isEmpty()){
                Type type = new TypeToken<List<Producto>>(){}.getType();
                listProductos = json.fromJson(result, type);
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
            if(listProductos.isEmpty()){
                AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                dialog.setTitle(R.string.dialog_header);
                dialog.setMessage("No productos para mostrar.");
                dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = new Intent(context, MenuActivity.class);
                        context.startActivity(i);
                    }
                });
                dialog.show();
            }else{
                //Creamos el adaptador
                adapter = new CatalogoAdapter(listProductos);
                recycler.setAdapter(adapter);
            }
        } else {
            AlertDialog.Builder dialog = new AlertDialog.Builder(context);
            dialog.setTitle(R.string.dialog_header);
            dialog.setMessage("Error en Cargar Catalogo de Productos.");
            dialog.show();
        }
    }
}

package upc.edu.pe.task;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.gson.Gson;
import upc.edu.pe.type.Pedido;
import upc.edu.pe.utils.HttpClientUtil;

/**
 * Created by jesus on 21/02/2016.
 */
public class ActualizarPedidoTask extends AsyncTask<String,Void,String> {

    private ProgressDialog progressDialog;


    private Pedido pedido;

    private Context context;
    Gson gson = new Gson();



    public ActualizarPedidoTask(Context context,Pedido pedido) {
        this.context=context;
        this.pedido=pedido;
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
            String json = gson.toJson(pedido);
            String result = RestClient.PUT("pedidos/actualizar",json );

            if(result.equalsIgnoreCase("OK")){
                mensaje = "El Pedido se Encuentra Aprobado";
            }else{
                mensaje = "No se pudo Actualizar";
            }

        } catch (Exception e) {
            mensaje = "No se pudo Modificar";
            e.printStackTrace();
            Log.d("Error en Task Update :", e.getMessage());
        }

        return mensaje;
    }
    @Override
    protected void onPostExecute(String result) {
        progressDialog.dismiss();

        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setTitle("Mensaje");
        dialog.setMessage(result);
        dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialog.show();

    }


}

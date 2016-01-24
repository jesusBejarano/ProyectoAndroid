package upc.edu.pe.task;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import upc.edu.pe.adapter.DetalleAdapter;
import upc.edu.pe.adapter.HistorialAdapter;
import upc.edu.pe.proyecto.MenuActivity;
import upc.edu.pe.proyecto.R;
import upc.edu.pe.type.DetallePedido;
import upc.edu.pe.type.Pedido;
import upc.edu.pe.utils.HttpClientUtil;

/**
 * Created by Miguel Cardoso on 10/10/2015.
 */
public class DetalleTask extends AsyncTask<String,Void,String> {

    private Context context;
    ListView listView;
    ArrayAdapter arrayAdapter;

    //Variables
    private Gson json = new Gson();
    private List<DetallePedido> listDetallePedidos;

    public DetalleTask(Context context,ListView listView, ArrayAdapter arrayAdapter) {
        this.context = context;
        this.listView = listView;
        this.arrayAdapter = arrayAdapter;
    }

    @Override
    protected String doInBackground(String... params) {
        HttpClientUtil RestClient = new HttpClientUtil();
        String mensaje="";
        try {
            String result = RestClient.GET("pedidos/detalle/"+params[0]);
            if(result != null || !result.isEmpty()){
                Type type = new TypeToken<List<DetallePedido>>(){}.getType();
                listDetallePedidos = json.fromJson(result, type);
                mensaje = result;
            }

        } catch (Exception e) {
            mensaje = null;
            e.printStackTrace();
            Log.d("Error en Task Detalle:", e.getMessage());
        }
        return mensaje;
    }

    @Override
    protected void onPostExecute(String result) {
        if(result != null){
            arrayAdapter = new DetalleAdapter(context,listDetallePedidos);
            listView.setAdapter(arrayAdapter);

            if(listDetallePedidos.isEmpty()){
                AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                dialog.setTitle(R.string.dialog_header);
                dialog.setMessage("No tiene Detalle de pedido para consultar.");
                dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = new Intent(context, MenuActivity.class);
                        context.startActivity(i);
                    }
                });
                dialog.show();
            }

        } else {
            AlertDialog.Builder dialog = new AlertDialog.Builder(context);
            dialog.setTitle(R.string.dialog_header);
            dialog.setMessage("Error en cargar Detalle de Pedido.");
            dialog.show();
        }
    }
}

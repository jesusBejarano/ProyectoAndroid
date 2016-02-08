package upc.edu.pe.task;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import upc.edu.pe.proyecto.MenuActivity;
import upc.edu.pe.proyecto.R;
import upc.edu.pe.type.DetallePedido;
import upc.edu.pe.utils.HttpClientUtil;

/**
 * Created by Miguel Cardoso on 10/10/2015.
 */
public class DetalleTask extends AsyncTask<String,Void,String> {

    private Context context;
    private ProgressDialog progressDialog;
    private TableLayout table;
    private TextView textView;

    //ListView listView;
    //ArrayAdapter arrayAdapter;

    //Variables
    private Gson json = new Gson();
    private List<DetallePedido> listDetallePedidos;

    public DetalleTask(Context context,TableLayout table,TextView textView) {
        this.context = context;
        this.table = table;
        this.textView = textView;
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
            String result = RestClient.GET("pedidos/detalle/"+params[0].trim());
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
        /*    arrayAdapter = new DetalleAdapter(context,listDetallePedidos);
            listView.setAdapter(arrayAdapter);*/
            progressDialog.dismiss();
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
            }else{
                Integer x = 0;
                Double total = 0.0;
                for(DetallePedido deta : listDetallePedidos){

                    TableRow row = (TableRow) LayoutInflater.from(context).inflate(R.layout.lista_personalizada_detalle, null);

                    if(x % 2 != 0) {
                        row.setBackgroundColor(Color.LTGRAY);
                    }

                    //Obteniendo instancias de los elementos
                    TextView nombre = (TextView) row.findViewById(R.id.txtProducto);
                    TextView cantidad = (TextView) row.findViewById(R.id.txtCantidad);
                    TextView subtotal = (TextView) row.findViewById(R.id.txtSubtotal);

                    //Seteamos los Valores
                    nombre.setText(deta.getProducto().getNombre());
                    cantidad.setText(deta.getCantidad()+"");
                    subtotal.setText(deta.getTotal().toString());
                    table.addView(row);
                    total += deta.getTotal();
                    x++;
                }
                textView.setText(total.toString());
            }

        } else {
            AlertDialog.Builder dialog = new AlertDialog.Builder(context);
            dialog.setTitle(R.string.dialog_header);
            dialog.setMessage("Error en cargar Detalle de Pedido.");
            dialog.show();
        }
    }
}

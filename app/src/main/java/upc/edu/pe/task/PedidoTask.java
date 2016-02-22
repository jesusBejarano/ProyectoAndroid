package upc.edu.pe.task;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import upc.edu.pe.proyecto.CarritoActivity;
import upc.edu.pe.proyecto.MainActivity;
import upc.edu.pe.proyecto.MenuActivity;
import upc.edu.pe.proyecto.R;
import upc.edu.pe.utils.CarritoDAO;
import upc.edu.pe.utils.DAOExcepcion;
import upc.edu.pe.utils.HttpClientUtil;

/**
 * Created by Miguel Cardoso on 21/02/2016.
 */
public class PedidoTask extends AsyncTask<String,Void,String> {

    private ProgressDialog progressDialog;
    private Context context;
    private CarritoDAO carritoDao;

    public PedidoTask(Context context) {
        this.context = context;
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

            String result = RestClient.POST("pedidos/insertar",params[0]);
            if(result.equalsIgnoreCase("OK")){
                mensaje = "Pedido Registrado";
            }else{
                mensaje = "No se pudo Registrar su pedido";
            }

        } catch (Exception e) {
            mensaje = "No se pudo Registrar su Pedido";
            e.printStackTrace();
            Log.d("Error en Task Pedido :", e.getMessage());
        }

        return mensaje;
    }

    @Override
    protected void onPostExecute(final String result) {
        progressDialog.dismiss();
        final String mensaje = result;
        final AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setTitle(R.string.dialog_header);
        dialog.setMessage(mensaje);
        if(result.equalsIgnoreCase("Pedido Registrado")) {
            dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if(mensaje.equalsIgnoreCase("Pedido Registrado")) {
                        eliminarCarrrito();
                        Intent i = new Intent(context, MenuActivity.class);
                        context.startActivity(i);
                    }else{
                        Intent i = new Intent(context, CarritoActivity.class);
                        context.startActivity(i);
                    }
                }
            });}
        dialog.show();

    }

    private void eliminarCarrrito(){
        carritoDao = CarritoDAO.getInstance(context);
        try {
            carritoDao.eliminarTodos();
        } catch (DAOExcepcion daoExcepcion) {
            Log.i("Eliminar Carrito Pedido", "====> " + daoExcepcion.getMessage());
        }
    }
}
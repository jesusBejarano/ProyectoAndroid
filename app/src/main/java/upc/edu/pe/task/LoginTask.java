package upc.edu.pe.task;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.gson.Gson;

import upc.edu.pe.Global.Globals;
import upc.edu.pe.proyecto.MainActivity;
import upc.edu.pe.proyecto.R;
import upc.edu.pe.type.Cliente;
import upc.edu.pe.utils.CarritoDAO;
import upc.edu.pe.utils.HttpClientUtil;

/**
 * Created by Miguel Cardoso on 09/10/2015.
 */
public class LoginTask extends AsyncTask<String,Void,String> {

    private ProgressDialog progressDialog;
    private Context context;
    private Cliente cliente;
    private Gson gson;

    private GoogleCloudMessaging gcm;
    private String regid;
    //ID Proyecto
    String SENDER_ID = "950263543030";


    public LoginTask(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = ProgressDialog.show(
                context, "Por favor espere", "Verificando Cliente");
    }

    @Override
    protected String doInBackground(String... params) {
        HttpClientUtil RestClient = new HttpClientUtil();
        String result="";
        gson = new Gson();
        try {
            result = RestClient.POST("usuarios/login",params[0]);
            if(!result.equalsIgnoreCase("ERROR")) {
                if (gcm == null) {
                    gcm = GoogleCloudMessaging.getInstance(context);
                }
                // Nos registramos en los servidores de GCM
                regid = gcm.register(SENDER_ID);
                Log.d("GCM", "Registrado en GCM: registration_id = " + regid);
                cliente = gson.fromJson(result,Cliente.class);
                cliente.setCodigoGCM(regid);
                String json = gson.toJson(cliente);
                String resultGCM = RestClient.PUT("usuarios/actualizar", json);
                if(resultGCM.equalsIgnoreCase("ERROR")){
                    result = "ERROR";
                }
            }
        } catch (Exception e) {
            result = "ERROR";
            e.printStackTrace();
            Log.d("Error en Task Login :", e.getMessage());
        }

        return result;
    }

    @Override
    protected void onPostExecute(String result) {
        progressDialog.dismiss();

        if(result.equalsIgnoreCase("ERROR")) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(context);
            dialog.setTitle(R.string.dialog_header);
            dialog.setMessage("Usuario o Contraseña no son válidos.");
            dialog.show();
        }else{
            Intent i = new Intent(context, MainActivity.class);
            Gson gson = new Gson();
            Cliente cli = gson.fromJson(result,Cliente.class);
            Globals.cliente_login=cli;
            Log.d("Enviar",cli.getId_cliente()+"");
            i.putExtra("cliente",cli.getId_cliente()+"");
            //Creacion de la BD Interna
            CarritoDAO carrito=  CarritoDAO.getInstance(context);
            context.startActivity(i);
        }


    }
}

package upc.edu.pe.task;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import upc.edu.pe.proyecto.R;
import upc.edu.pe.type.Distrito;
import upc.edu.pe.utils.HttpClientUtil;

/**
 * Created by Miguel Cardoso on 07/10/2015.
 */
public class DistritoTask extends AsyncTask<String,Void,String> {

    private Context context;
    Spinner spinner;

    //Variables
  //  private Distrito distrito;
    private Gson json = new Gson();
    private static String formatoJson;
    private List<String> listNombres = new ArrayList<String>();
    private List<Distrito> listDistritos;

    public DistritoTask(Context context,Spinner spinner) {
        this.context = context;
        this.spinner = spinner;
    }


    @Override
    protected String doInBackground(String... params) {
        HttpClientUtil RestClient = new HttpClientUtil();
        String mensaje="";
        try {
            Type type = new TypeToken<List<Distrito>>(){}.getType();
            String result = RestClient.GET("distritos");
            formatoJson = result;
            if(result != null || !result.isEmpty()){
                listDistritos = json.fromJson(result, type);
                for (int i = 0; i < listDistritos.size(); i++) {
                    String nombreDistrito = listDistritos.get(i).getNombre();
                    listNombres.add(nombreDistrito);
                }

                mensaje = result;
            }

        } catch (Exception e) {
            mensaje = null;
            e.printStackTrace();
            Log.d("Error en Task Distrito:", e.getMessage());
            Log.d("JSOn : ",formatoJson);
        }

        return mensaje;
    }

    @Override
    protected void onPostExecute(String result) {
        if(result != null){

            spinner.setAdapter(new ArrayAdapter<String>(context,
                    android.R.layout.simple_spinner_dropdown_item,
                    listNombres));

            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(context, "Selección actual: " + listDistritos.get(position).getNombre(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }

        });

        } else {
            AlertDialog.Builder dialog = new AlertDialog.Builder(context);
            dialog.setTitle(R.string.dialog_header);
            dialog.setMessage("Error en cargar distritos.");
            dialog.show();
        }
    }
}

package upc.edu.pe.proyecto;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import upc.edu.pe.task.DistritoTask;
import upc.edu.pe.task.MantenimientoTask;
import upc.edu.pe.task.UsuarioTask;
import upc.edu.pe.type.Cliente;
import upc.edu.pe.type.Distrito;
import upc.edu.pe.utils.HttpClientUtil;

/**
 * Created by Miguel Cardoso on 19/09/2015.
 */
public class MantenimientoActivity extends Activity  {

    //Variables
    Spinner spinnerDistrito;
    private EditText txtUsuario;
    private EditText txtContrasena;
    private EditText txtNombre;
    private EditText txtApellido;
    private EditText txtCorreo;
    private EditText txtTelefono;
    private EditText txtDireccion;
    private Button btnActualizar;
    private Button btnCancelar;
    //Type
    public Cliente cliente;
    //JSON
    private Gson gson;
    //Otros
    public String clienteId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mantenimiento_main);
        final SharedPreferences prefs =  getSharedPreferences("MyCliente", Context.MODE_PRIVATE);
        cliente = new Cliente();
        cliente.setDistrito(new Distrito());
        gson = new Gson();

        //Obetener Intent
        Intent intent = getIntent();
        //Extrayendo el extra de tipo cadena
        clienteId = prefs.getString("idCliente", "0");

        //Inicializamos los tipos de la variables
        spinnerDistrito = (Spinner)findViewById(R.id.spinnerDistrito);
        txtUsuario = (EditText) findViewById(R.id.txtUsuarioM);
        txtContrasena = (EditText) findViewById(R.id.txtContrasenaM);
        txtNombre = (EditText) findViewById(R.id.txtNombreM);
        txtApellido = (EditText) findViewById(R.id.txtApellidoM);
        txtCorreo= (EditText) findViewById(R.id.txtCorreoM);
        txtTelefono  = (EditText) findViewById(R.id.txtTelefonoM);
        txtDireccion =   (EditText) findViewById(R.id.txtDireccionM);
        btnActualizar = (Button)findViewById(R.id.btnActualizarM);
        btnCancelar = (Button)findViewById(R.id.btnCancelarM);

        new DistritoTask(MantenimientoActivity.this,spinnerDistrito).execute("");
        new ClienteJSON().execute("");

        //Capturamos evento del Boton
        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(camposVacios()){
                    cliente.setNombre(txtNombre.getText().toString().trim());
                    cliente.setApellidos(txtApellido.getText().toString().trim());
                    cliente.setCorreo(txtCorreo.getText().toString().trim());
                    cliente.setUsuario(txtUsuario.getText().toString().trim());
                    cliente.setContrasena(txtContrasena.getText().toString().trim());
                    cliente.setTelefono(txtTelefono.getText().toString().trim());
                    cliente.setDireccion(txtDireccion.getText().toString().trim());
                    cliente.getDistrito().setId_distrito(spinnerDistrito.getSelectedItemPosition());
                    String json = gson.toJson(cliente);
                    new MantenimientoTask(MantenimientoActivity.this).execute(json);
                }else{
                    mensaje();
                }
            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarActivity(MenuActivity.class);
            }
        });


    }

    private void cargarInformacion() {
        txtUsuario.setText(cliente.getUsuario());
        txtDireccion.setText(cliente.getDireccion());
        txtTelefono.setText(cliente.getTelefono());
        txtApellido.setText(cliente.getApellidos());
        txtNombre.setText(cliente.getNombre());
        txtContrasena.setText(cliente.getContrasena());
        txtCorreo.setText(cliente.getCorreo());
        spinnerDistrito.setSelection(cliente.getDistrito().getId_distrito());
    }

    private void mostrarActivity(Class view){
        Intent i = new Intent(this, view);
        startActivity(i);
    }

    public boolean camposVacios() {

        boolean result = true;
        String usuario = txtUsuario.getText().toString().trim();
        String constrasena = txtContrasena.getText().toString().trim();
        String nombre = txtNombre.getText().toString().trim();
        String apellido = txtApellido.getText().toString().trim();
        String correo = txtCorreo.getText().toString().trim();
        String direccion = txtDireccion.getText().toString().trim();
        String telefono = txtTelefono.getText().toString().trim();

        if(usuario == null || usuario.isEmpty()){
            result = false;
        }else if(constrasena == null || constrasena.isEmpty()){
            result = false;
        }else if(nombre ==null || nombre.isEmpty()){
            result = false;
        }else if(apellido == null || apellido.isEmpty()){
            result = false;
        }else if(correo == null || correo.isEmpty()){
            result = false;
        }else if(telefono == null || telefono.isEmpty()){
            result = false;
        }else if(direccion == null || direccion.isEmpty()){
            result = false;
        }

        return result;
    }

    public void mensaje(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(MantenimientoActivity.this);
        dialog.setTitle(R.string.dialog_header);
        dialog.setMessage("Debe ingresar todo los campos.");
        dialog.show();
    }

    class ClienteJSON extends AsyncTask<String,Void,String> {
        @Override
        protected String doInBackground(String... params){
        HttpClientUtil RestClient = new HttpClientUtil();
            try {
                Thread.sleep(4000);
                String result = RestClient.GET("usuarios/info/"+clienteId);
                if(result != null || !result.isEmpty()){
                    cliente = gson.fromJson(result,Cliente.class);
                    if(cliente.getDistrito() == null){
                        cliente.setDistrito(new Distrito());
                        cliente.getDistrito().setId_distrito(1);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
                Log.d("Error en Task JSON:", e.getMessage());
            }
            return "";
        }
        @Override
        protected void onPostExecute(String result) {
        cargarInformacion();
    }
}
}

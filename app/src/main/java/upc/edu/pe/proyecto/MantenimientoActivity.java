package upc.edu.pe.proyecto;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
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
    private ProgressDialog progressDialogMantenimiento;
    Spinner spinnerDistrito;
    private EditText txtUsuario;
    private EditText txtContrasena;
    private EditText txtNombre;
    private EditText txtApellido;
    private EditText txtCorreo;
    private EditText txtTelefono;
    private EditText txtDireccion;
    private Button btnActualizar;
    private TextInputLayout inputLayoutCorreoM,inputLayoutNombreM,inputLayoutApellidoM,inputLayoutUsuarioM,inputLayoutContrasenaM,inputLayoutTelefonoM,inputLayoutDireccionM;
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

        inputLayoutCorreoM = (TextInputLayout) findViewById(R.id.input_layout_correoM);
        inputLayoutNombreM = (TextInputLayout) findViewById(R.id.input_layout_nombreM);
        inputLayoutApellidoM = (TextInputLayout) findViewById(R.id.input_layout_apellidoM);
        inputLayoutUsuarioM = (TextInputLayout) findViewById(R.id.input_layout_usuarioM);
        inputLayoutContrasenaM = (TextInputLayout) findViewById(R.id.input_layout_contrasenaM);
        inputLayoutTelefonoM = (TextInputLayout) findViewById(R.id.input_layout_telefonoM);
        inputLayoutDireccionM = (TextInputLayout) findViewById(R.id.input_layout_direccionM);

        txtUsuario.addTextChangedListener(new MyTextWatcher(txtUsuario));
        txtContrasena.addTextChangedListener(new MyTextWatcher(txtContrasena));
        txtNombre.addTextChangedListener(new MyTextWatcher(txtNombre));
        txtApellido.addTextChangedListener(new MyTextWatcher(txtApellido));
        txtCorreo.addTextChangedListener(new MyTextWatcher(txtCorreo));
        txtTelefono.addTextChangedListener(new MyTextWatcher(txtTelefono));
        txtDireccion.addTextChangedListener(new MyTextWatcher(txtDireccion));

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
                }
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

        if(!validarNombre()){
            result =  false;
        } else if(!validarApellidos()){
            result =  false;
        }else  if(!validarEmail()){
            result = false;
        }else if(!validarUsuario()){
            result = false;
        }else if(!validarContrasena()){
            result = false;
        }else if(!validarDireccion()){
            result = false;
        }else if(!validarTelefono()){
            result = false;
        }

        return result;
    }

    private boolean validarNombre() {
        if (txtNombre.getText().toString().trim().isEmpty()) {
            inputLayoutNombreM.setError("Ingrese nombre");
            requestFocus(txtNombre);
            return false;
        } else {
            inputLayoutNombreM.setError(null);
            inputLayoutNombreM.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validarApellidos() {
        if (txtApellido.getText().toString().trim().isEmpty()) {
            inputLayoutApellidoM.setError("Ingrese apellidos");
            requestFocus(txtApellido);
            return false;
        } else {
            inputLayoutApellidoM.setError(null);
            inputLayoutApellidoM.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validarUsuario() {
        if (txtUsuario.getText().toString().trim().isEmpty()) {
            inputLayoutUsuarioM.setError("Ingrese usuario");
            requestFocus(txtUsuario);
            return false;
        } else {
            inputLayoutUsuarioM.setError(null);
            inputLayoutUsuarioM.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validarContrasena() {
        if (txtContrasena.getText().toString().trim().isEmpty()) {
            inputLayoutContrasenaM.setError("Ingrese contraseña");
            requestFocus(txtContrasena);
            return false;
        } else {
            inputLayoutContrasenaM.setError(null);
            inputLayoutContrasenaM.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validarEmail() {
        String email = txtCorreo.getText().toString().trim();
        if (email.isEmpty() || !isValidEmail(email)) {
            inputLayoutCorreoM.setError("Ingrese formato válido de correo");
            requestFocus(txtCorreo);
            return false;
        } else {
            inputLayoutCorreoM.setError(null);
            inputLayoutCorreoM.setErrorEnabled(false);
        }

        return true;
    }

    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean validarDireccion() {
        if (txtDireccion.getText().toString().trim().isEmpty()) {
            inputLayoutDireccionM.setError("Ingrese dirección");
            requestFocus(txtDireccion);
            return false;
        } else {
            inputLayoutDireccionM.setError(null);
            inputLayoutDireccionM.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validarTelefono() {
        if (txtTelefono.getText().toString().trim().isEmpty()) {
            inputLayoutTelefonoM.setError("Ingrese telefono");
            requestFocus(txtTelefono);
            return false;
        } else {
            inputLayoutTelefonoM.setError(null);
            inputLayoutTelefonoM.setErrorEnabled(false);
        }

        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.txtNombreM:
                    validarNombre();
                    break;
                case R.id.txtApellidoM:
                    validarApellidos();
                    break;
                case R.id.txtCorreoM:
                    validarEmail();
                    break;
                case R.id.txtUsuarioM:
                    validarUsuario();
                    break;
                case R.id.txtContrasenaM:
                    validarContrasena();
                    break;
                case R.id.txtTelefonoM:
                    validarContrasena();
                    break;
                case R.id.txtDireccionM:
                    validarContrasena();
                    break;
            }
        }
    }

    class ClienteJSON extends AsyncTask<String,Void,String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialogMantenimiento = ProgressDialog.show(
                    MantenimientoActivity.this, "Por favor espere", "Procesando...");
        }


        @Override
        protected String doInBackground(String... params){
        HttpClientUtil RestClient = new HttpClientUtil();
            try {
                String result = RestClient.GET("usuarios/info/"+clienteId);
                Thread.sleep(3000);
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
            progressDialogMantenimiento.dismiss();
            cargarInformacion();
    }
}
}

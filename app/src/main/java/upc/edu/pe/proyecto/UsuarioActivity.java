package upc.edu.pe.proyecto;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import upc.edu.pe.type.Cliente;
import upc.edu.pe.task.UsuarioTask;

/**
 * Created by lcardoso on 06/10/2015.
 */
public class UsuarioActivity extends Activity {

    //Variables
    private EditText txtUsuario;
    private EditText txtContrasena;
    private EditText txtNombre;
    private EditText txtApellido;
    private EditText txtCorreo;
    private Button btnRegistrar;
    private Button btnCancelar;
    private TextInputLayout inputLayoutCorreo,inputLayoutNombre,inputLayoutApellido,inputLayoutUsuario,inputLayoutContrasena;
    //Type
    public Cliente cliente;
    //JSON
    private Gson gson;

    public UsuarioActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usuario_main);

        //Inicializamos los tipos de la variables
        txtUsuario = (EditText) findViewById(R.id.txtUsuarioR);
        txtContrasena = (EditText) findViewById(R.id.txtContrasenaR);
        txtNombre = (EditText) findViewById(R.id.txtNombreR);
        txtApellido = (EditText) findViewById(R.id.txtApellidosR);
        txtCorreo= (EditText) findViewById(R.id.txtCorreoR);
        btnRegistrar = (Button)findViewById(R.id.btnRegistrarR);
        btnCancelar = (Button)findViewById(R.id.btnCancelarR);

        inputLayoutCorreo = (TextInputLayout) findViewById(R.id.input_layout_correo);
        inputLayoutNombre = (TextInputLayout) findViewById(R.id.input_layout_nombre);
        inputLayoutApellido = (TextInputLayout) findViewById(R.id.input_layout_apellido);
        inputLayoutUsuario = (TextInputLayout) findViewById(R.id.input_layout_usuario);
        inputLayoutContrasena = (TextInputLayout) findViewById(R.id.input_layout_contrasena);

        txtUsuario.addTextChangedListener(new MyTextWatcher(txtUsuario));
        txtContrasena.addTextChangedListener(new MyTextWatcher(txtContrasena));
        txtNombre.addTextChangedListener(new MyTextWatcher(txtNombre));
        txtApellido.addTextChangedListener(new MyTextWatcher(txtApellido));
        txtCorreo.addTextChangedListener(new MyTextWatcher(txtCorreo));

        //otros
        cliente = new Cliente();
        gson = new Gson();

        //Capturamos evento del Boton
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(camposVacios()){
                    cliente.setNombre(txtNombre.getText().toString().trim());
                    cliente.setApellidos(txtApellido.getText().toString().trim());
                    cliente.setCorreo(txtCorreo.getText().toString().trim());
                    cliente.setUsuario(txtUsuario.getText().toString().trim());
                    cliente.setContrasena(txtContrasena.getText().toString().trim());
                    String json = gson.toJson(cliente);
                    Log.d("Json Cliente : " , json );
                    new UsuarioTask(UsuarioActivity.this).execute(json);
                }
            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarActivity(MainActivity.class);
            }
        });


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
        }

        return result;
    }

    private boolean validarNombre() {
        if (txtNombre.getText().toString().trim().isEmpty()) {
            inputLayoutNombre.setError("Ingrese nombre");
            requestFocus(txtNombre);
            return false;
        } else {
            inputLayoutNombre.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validarApellidos() {
        if (txtApellido.getText().toString().trim().isEmpty()) {
            inputLayoutApellido.setError("Ingrese apellidos");
            requestFocus(txtApellido);
            return false;
        } else {
            inputLayoutApellido.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validarUsuario() {
        if (txtUsuario.getText().toString().trim().isEmpty()) {
            inputLayoutUsuario.setError("Ingrese usuario");
            requestFocus(txtUsuario);
            return false;
        } else {
            inputLayoutUsuario.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validarContrasena() {
        if (txtContrasena.getText().toString().trim().isEmpty()) {
            inputLayoutContrasena.setError("Ingrese contraseña");
            requestFocus(txtContrasena);
            return false;
        } else {
            inputLayoutContrasena.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validarEmail() {
        String email = txtCorreo.getText().toString().trim();
        if (email.isEmpty() || !isValidEmail(email)) {
            inputLayoutCorreo.setError("Ingrese formato válido de correo");
            requestFocus(txtCorreo);
            return false;
        } else {
            inputLayoutCorreo.setErrorEnabled(false);
        }

        return true;
    }



    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
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
                case R.id.txtNombreR:
                    validarNombre();
                    break;
                case R.id.txtApellidosR:
                    validarApellidos();
                    break;
                case R.id.txtCorreoR:
                    validarEmail();
                    break;
                case R.id.txtUsuarioR:
                    validarUsuario();
                    break;
                case R.id.txtContrasenaR:
                    validarContrasena();
                    break;
            }
        }
    }
}
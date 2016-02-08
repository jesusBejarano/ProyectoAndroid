package upc.edu.pe.proyecto;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
     //Type
    public Cliente cliente;
    //JSON
    private Gson gson;

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
                }else{
                    mensaje();
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
        String usuario = txtUsuario.getText().toString().trim();
        String constrasena = txtContrasena.getText().toString().trim();
        String nombre = txtNombre.getText().toString().trim();
        String apellido = txtApellido.getText().toString().trim();
        String correo = txtCorreo.getText().toString().trim();

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
        }

        return result;
    }

    public void mensaje(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(UsuarioActivity.this);
        dialog.setTitle(R.string.dialog_header);
        dialog.setMessage("Debe ingresar todo los campos.");
  /*      dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent i = new Intent(context, MainActivity.class);
                context.startActivity(i);
            }
        });*/
        dialog.show();
    }
}

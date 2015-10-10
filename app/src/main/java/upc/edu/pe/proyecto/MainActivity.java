package upc.edu.pe.proyecto;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;

import upc.edu.pe.task.LoginTask;
import upc.edu.pe.type.Cliente;

public class MainActivity extends AppCompatActivity {

    //Variables
    private EditText txtUsuario;
    private EditText txtContrasena;
    private Button btnIngresar;
    private TextView viewRegistrate;

    //Otros
    Cliente cliente;
    Gson gson = new Gson();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        setContentView(R.layout.login_main);

        //Inicializamos los tipos de la variables
        txtUsuario = (EditText) findViewById(R.id.txtUsuario);
        txtContrasena = (EditText) findViewById(R.id.txtContrasena);
        btnIngresar = (Button)findViewById(R.id.btnIngresar);
        viewRegistrate = (TextView) findViewById(R.id.texto_registrate);

        //Capturamos evento del Boton
        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(camposVacios()){
                Log.d("Varoles Capturados ",txtUsuario.getText().toString() + " - " +txtContrasena.getText().toString());
                    cliente = new Cliente();
                    cliente.setUsuario(txtUsuario.getText().toString().trim());
                    cliente.setContrasena(txtContrasena.getText().toString().trim());
                    String json = gson.toJson(cliente);
                    new LoginTask(MainActivity.this).execute(json);
              //  mostrarActivity(MenuActivity.class);
                }else{

                }
            }
        });

        viewRegistrate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarActivity(UsuarioActivity.class);
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

    if(usuario == null || usuario.isEmpty()){
        result = false;
        mensaje("Ingrese su usuario.");
    }else if(constrasena == null || constrasena.isEmpty()){
        result = false;
        mensaje("Ingrese su contraseña.");
    }

    return result;
}

    public void mensaje(String mensj){
        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
        dialog.setTitle(R.string.dialog_header);
        dialog.setMessage(mensj);
        dialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

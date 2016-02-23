package upc.edu.pe.proyecto;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import upc.edu.pe.Fragmentos.FragmentoCarrito;
import upc.edu.pe.Fragmentos.FragmentoHistorial;
import upc.edu.pe.Fragmentos.FragmentoMantenimientoUsuario;
import upc.edu.pe.Fragmentos.FragmentoPedidosPendientes;
import upc.edu.pe.Fragmentos.FragmentoProductos;
import upc.edu.pe.Global.Globals;

public class MainActivity extends AppCompatActivity {


    /**
     * Instancia del drawer
     */

    TextView txtuser;
    TextView txtmail;
    private DrawerLayout drawerLayout;

    NavigationView navigationView;

    /**
     * Titulo inicial del drawer
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

             final SharedPreferences prefs =  getSharedPreferences("MyCliente", Context.MODE_PRIVATE);
        //Obetener Intent
        Intent intent = getIntent();
        //Extrayendo el extra de tipo cadena
        String clienteId = intent.getStringExtra("cliente");
        if(clienteId != null && !clienteId.isEmpty()){
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("idCliente",clienteId.trim());
            editor.commit();
        }

        setToolbar(); // Setear Toolbar como action bar

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);



        if (Globals.cliente_login.getId_cliente()==1 || Globals.cliente_login.getId_cliente()==2)
        {
            navigationView.getMenu().getItem(3).setVisible(true);
        }
        else
        {
            navigationView.getMenu().getItem(3).setVisible(false);

        }





        if (navigationView != null) {
            prepararDrawer(navigationView);
            // Seleccionar item por defecto
            seleccionarItem(navigationView.getMenu().getItem(1));
        }
    }
    public void SelecionarMenu(int indexmenu)
    {
        seleccionarItem(navigationView.getMenu().getItem(indexmenu));
    }
    private void setToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ActionBar ab = getSupportActionBar();
        if (ab != null) {
            // Poner ícono del drawer toggle
            ab.setHomeAsUpIndicator(R.drawable.ic_menu);
            ab.setDisplayHomeAsUpEnabled(true);
        }
        txtuser=(TextView) findViewById(R.id.username);
        txtuser.setText(Globals.cliente_login.getNombreApe());
        txtmail=(TextView) findViewById(R.id.email);
        txtmail.setText(Globals.cliente_login.getCorreo());

        txtuser.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                CargarFragmento();
            }

        });





    }

    private void seleccionarItem(MenuItem itemDrawer) {
        Fragment fragmentoGenerico = null;
        FragmentManager fragmentManager = getSupportFragmentManager();

        switch (itemDrawer.getItemId()) {
            case R.id.nav_productos:
                fragmentoGenerico = new FragmentoProductos();
                break;
            case R.id.nav_home:
                fragmentoGenerico = new FragmentoProductos();
                break;
            case R.id.nav_ordenes:
                fragmentoGenerico = new FragmentoPedidosPendientes();
                break;
            case R.id.nav_carrito:
                fragmentoGenerico = new FragmentoCarrito();
                break;
            case R.id.nav_historial:
                fragmentoGenerico = new FragmentoHistorial();
                break;
            case R.id.nav_log_out:
               // finish();
                //Intent intent = new Intent(Intent.ACTION_MAIN);
                //intent.addCategory(Intent.CATEGORY_HOME);
                //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                //startActivity(intent);
                Intent i =new Intent(MainActivity.this,LoginActivity.class);
                startActivity(i);


                break;


        }
        if (fragmentoGenerico != null) {
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.main_content, fragmentoGenerico)
                    .commit();
        }

        // Setear título actual
        setTitle(itemDrawer.getTitle());
    }

    private void prepararDrawer(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);
                        seleccionarItem(menuItem);
                        drawerLayout.closeDrawers();
                        return true;
                    }
                });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    //evento del boton back
    @Override
    public void onBackPressed() {
    }

    public void CargarFragmento() {
        Fragment fragmentoGenerico = null;
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentoGenerico = new FragmentoMantenimientoUsuario();
        if (fragmentoGenerico != null) {
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.main_content, fragmentoGenerico)
                    .commit();
        }


    }


}

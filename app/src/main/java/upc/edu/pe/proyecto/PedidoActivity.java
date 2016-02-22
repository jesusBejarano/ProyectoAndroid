package upc.edu.pe.proyecto;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import upc.edu.pe.task.PedidoTask;
import upc.edu.pe.type.Carrito;
import upc.edu.pe.type.Cliente;
import upc.edu.pe.type.DetallePedido;
import upc.edu.pe.type.Distrito;
import upc.edu.pe.type.Pedido;
import upc.edu.pe.type.Producto;
import upc.edu.pe.utils.CarritoDAO;
import upc.edu.pe.utils.DAOExcepcion;
import upc.edu.pe.utils.HttpClientUtil;

import static upc.edu.pe.utils.Tools.convertirDateToStringLong;
import static upc.edu.pe.utils.Tools.formatearDecimales;

public class PedidoActivity extends AppCompatActivity {

    //Variables
    private TextView txtNombre,txtApellido,txtFecha,txtDireccion,txtTotal;
    private Button btnPedir,btnCancelar,btnDireccion;
    private TableLayout layoutDetalle;

    //Otros
    public String clienteId;
    public String nuevaDireccion;
    public Cliente cliente;
    public Pedido pedido;
    public List<DetallePedido> detallePedido;
    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pedido_main);

        SharedPreferences prefs =  getSharedPreferences("MyCliente", Context.MODE_PRIVATE);
       //Extrayendo el extra de tipo cadena
       clienteId = prefs.getString("idCliente", "0");

        //Obetener Intent
        Intent intent = getIntent();
        //Extrayendo el extra de tipo cadena
        nuevaDireccion = intent.getStringExtra("Direccion");
        Log.d("Nueva Direccion : ", nuevaDireccion);

        //Inicializamos variables
        txtNombre = (TextView) findViewById(R.id.txtNombre);
        txtApellido = (TextView) findViewById(R.id.txtApellido);
        txtFecha = (TextView) findViewById(R.id.txtFecha);
        txtDireccion = (TextView) findViewById(R.id.txtDireccion);
        txtTotal = (TextView) findViewById(R.id.txtTotalP);

        layoutDetalle = (TableLayout) findViewById(R.id.contenedorPedido);

        btnPedir = (Button) findViewById(R.id.btnPedir);
        btnDireccion = (Button) findViewById(R.id.btnDireccion);
        btnCancelar = (Button) findViewById(R.id.btnCancelar);

        cliente = new Cliente();
        gson = new Gson();

        new ClienteTask().execute("1"/*clienteId*/);

        obtenerFecha();
        armarDetallePedido();

        btnPedir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enviarInformacion();
            }
        });


        btnDireccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PedidoActivity.this, MapaActivity.class);
                intent.putExtra("Llamado", "Pedido");
                startActivity(intent);
            }
        });


        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarActivity(CarritoActivity.class);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_pedido, menu);
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


    private void obtenerFecha(){
        Calendar cal = Calendar.getInstance();
        txtFecha.setText(convertirDateToStringLong(cal.getTime()));
    }

    private void armarDetallePedido(){
        Double total = 0.0;
        int item = 1;
        try {
            CarritoDAO carritoDao = CarritoDAO.getInstance(this);
            List<Carrito> listCarrito = carritoDao.obtenerTodos();
            detallePedido = new ArrayList<DetallePedido>();
            for (Carrito carrito : listCarrito) {
                TableRow row = (TableRow) LayoutInflater.from(this).inflate(R.layout.content_pedido, null);

                //Obteniendo instancias de los elementos
                TextView nombre = (TextView) row.findViewById(R.id.txtProductoP);
                TextView cantidad = (TextView) row.findViewById(R.id.txtCantidadP);
                TextView subtotal = (TextView) row.findViewById(R.id.txtSubtotalP);

                nombre.setText(carrito.getNombre());
                cantidad.setText(carrito.getCantidad()+"");
                subtotal.setText(formatearDecimales(carrito.getTotal()));

                total += carrito.getTotal();

                layoutDetalle.addView(row);

                //Lenemos nuestro array de detalle pedido
                DetallePedido dp = new DetallePedido();
                dp.setProducto(new Producto());
                dp.setCantidad(carrito.getCantidad());
                dp.setTotal(carrito.getTotal());
                dp.setItem(item);
                dp.getProducto().setId_producto(carrito.getIdProducto());
                dp.getProducto().setPrecio(carrito.getPrecio());

                detallePedido.add(dp);


                item++;
            }
            txtTotal.setText("S/." + formatearDecimales(total));
        } catch (DAOExcepcion daoExcepcion) {
            Log.i("Listar Carrito Pedido", "====> " + daoExcepcion.getMessage());
        }
    }

    private void mostrarActivity(Class view){
        Intent i = new Intent(this, view);
        startActivity(i);
    }

    private void enviarInformacion(){

        try {
            pedido = new Pedido();

            pedido.setCliente(new Cliente());
            pedido.getCliente().setId_cliente(cliente.getId_cliente());
            pedido.setDistrito(new Distrito());
            pedido.getDistrito().setId_distrito(1);

            pedido.setCantidad(detallePedido.size());
            pedido.setDireccion(txtDireccion.getText().toString());
            pedido.setEstado("P");
            pedido.setMonto(Double.parseDouble(txtTotal.getText().toString()));

            pedido.setListDetallePedidos(detallePedido);

            String json = gson.toJson(pedido);
            Log.i("Json Pedido : ", json);

            new PedidoTask(PedidoActivity.this).execute(json);

        }catch (Exception e){
            Log.d("Error Pedido",e.getMessage());
        }



    }

    class ClienteTask extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... params) {
            HttpClientUtil RestClient = new HttpClientUtil();
            String mensaje="ERROR";
            try {
                String result = RestClient.GET("usuarios/info/"+params[0].trim());
                if(result != null || !result.isEmpty()){
                    cliente = gson.fromJson(result,Cliente.class);
                    mensaje = "OK";
                }

            } catch (Exception e) {
                mensaje = "ERROR";
                Log.d("Error en Task Cliente:", e.getMessage());
            }
            return mensaje;
        }

        @Override
        protected void onPostExecute(String result) {
            if(result.equalsIgnoreCase("OK")){
                txtNombre.setText(cliente.getNombre());
                txtApellido.setText(cliente.getApellidos());

                if(!nuevaDireccion.isEmpty()){
                    txtDireccion.setText(nuevaDireccion);
                }else{
                    txtDireccion.setText(cliente.getDireccion());
                }

            } else {
                AlertDialog.Builder dialog = new AlertDialog.Builder(PedidoActivity.this);
                dialog.setTitle(R.string.dialog_header);
                dialog.setMessage("Error en cargar Pedido.");
                dialog.show();
            }
        }
    }

}
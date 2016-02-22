package upc.edu.pe.proyecto;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapaActivity extends FragmentActivity implements LocationListener, GoogleMap.OnMarkerDragListener {

    //Variables
    EditText txtDireccionMapa;
    Button btnAceptar;

    //Variables Mapa
    LocationManager locationManager;
    GoogleMap googleMap;
    Location location;
    Marker marcador;


    //Otros
    Double latitud,longitud;
    String baseActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mapa_main);

        //Obetener Intent
        Intent intent = getIntent();
        //Extrayendo el extra de tipo cadena
        baseActivity = intent.getStringExtra("Llamado");
        Log.d("Base : ", baseActivity);

        txtDireccionMapa = (EditText) findViewById(R.id.txtDireccionMapa);

        btnAceptar = (Button) findViewById(R.id.btnAceptarMapa);

        googleMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapa_inicial)).getMap();
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        googleMap.setOnMarkerDragListener(this);
        //      googleMap.setOnMarkerClickListener(this);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        obtenerLocalizacionActual();



        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapaActivity.this, PedidoActivity.class);
                intent.putExtra("Direccion",txtDireccionMapa.getText().toString());
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_mapa, menu);
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

    private void obtenerLocalizacionActual(){
        // Estado GPS
        boolean isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        // Estado Internet
        boolean isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        //Si el GPS no está habilitado
        if (!isGPSEnabled && !isNetworkEnabled) {
            Toast.makeText(MapaActivity.this, "Active su GPS para obtener su ubicación precisa", Toast.LENGTH_LONG).show();
        } else {
            if ( Build.VERSION.SDK_INT >= 23 &&
                    ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission( this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return  ;
            }

            if (isNetworkEnabled) {
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,15000, 0, this);
                Log.d("activity", "Network Enabled");
                if (locationManager != null) {
                    location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    if (location != null) {
                        Log.d("activity", "LOC by Network");
                        latitud = location.getLatitude();
                        longitud = location.getLongitude();
                    }
                }
            }

            if (isGPSEnabled) {
                if (location == null) {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,15000,0, this);
                    Log.d("activity", "GPS Enabled");
                    if (locationManager != null) {
                        location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        if (location != null) {
                            Log.d("activity", "RLOC: loc by GPS");
                            latitud = location.getLatitude();
                            longitud = location.getLongitude();
                        }
                    }
                }
            }
        }

        obtenerDireccion(latitud, longitud);
        pintarMarcador(latitud, longitud);
    }

    private void pintarMarcador(double latitud,double longitud) {
        Log.d("Latitud y Longitud: ", latitud + " - " + longitud);

        LatLng latLng = new LatLng(latitud, longitud);
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(17);

        MarkerOptions options = new MarkerOptions()
                .position(latLng)
                .title("Ubicación Actual")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.marcadorcerveza));
        marcador = googleMap.addMarker(options);
        marcador.setDraggable(true);
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        googleMap.animateCamera(zoom);

    }

    private void obtenerDireccion(double latitud,double longitud){
        try {
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            List<Address> list = geocoder.getFromLocation(latitud, longitud, 1);
            if (!list.isEmpty()) {
                Address address = list.get(0);
                txtDireccionMapa.setText(address.getAddressLine(0) + "," + address.getLocality());
            }
        } catch (IOException e) {
            Log.d("Error Obtener Direccion",e.getMessage());
        }
    }

    @Override
    public void onLocationChanged(Location location) {
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onMarkerDrag(Marker marker) {
        Toast.makeText(this,"Seleccione una nueva dirección", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
        LatLng position = marker.getPosition();
        obtenerDireccion(position.latitude,position.longitude);

    }

    @Override
    public void onMarkerDragStart(Marker marker) {
    }
}

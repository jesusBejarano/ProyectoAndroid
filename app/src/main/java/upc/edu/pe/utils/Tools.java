package upc.edu.pe.utils;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Miguel Cardoso on 07/02/2016.
 */
public class Tools {

    public static String formatearDecimales(Double numero){
        return  String.format(Locale.US, "%.2f", numero);
    }

    public static String convertirDateToStringLong(Date datoDate) {
        if (datoDate == null) {
            return "";
        } else {
            Format formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            String fechaejecucion = formatter.format(datoDate);

            return fechaejecucion;
        }
    }
}

package upc.edu.pe.utils;

import java.util.Locale;

/**
 * Created by Miguel Cardoso on 07/02/2016.
 */
public class Tools {

    public static String formatearDecimales(Double numero){
        return  String.format(Locale.US, "%.2f", numero);
    }
}

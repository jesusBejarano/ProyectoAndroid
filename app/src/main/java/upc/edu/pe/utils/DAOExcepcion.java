package upc.edu.pe.utils;

/**
 * Created by Miguel Cardoso on 10/02/2016.
 */
public class DAOExcepcion extends Exception {

    private static final long serialVersionUID = 1L;

    public DAOExcepcion() {
        super();
    }

    public DAOExcepcion(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public DAOExcepcion(String detailMessage) {
        super(detailMessage);
    }

    public DAOExcepcion(Throwable throwable) {
        super(throwable);
    }
}
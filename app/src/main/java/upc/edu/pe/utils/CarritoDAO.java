package upc.edu.pe.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import upc.edu.pe.type.Carrito;

import static upc.edu.pe.utils.Tools.formatearDecimales;

/**
 * Created by Miguel Cardoso on 10/02/2016.
 */
public class CarritoDAO {

    private static DBHelper _dbHelper;
    private static CarritoDAO singleton = null;


    private CarritoDAO(Context c) {
        _dbHelper = new DBHelper(c);
    }

    public static CarritoDAO getInstance(Context c) {
       if (singleton == null) {
            singleton = new CarritoDAO(c);
        }
        return singleton;
    }

    public void insertar(Carrito carrito) throws DAOExcepcion {
        Log.i("CarritoDAO", "insertar()");
        SQLiteDatabase db = _dbHelper.getWritableDatabase();
        String idProducto = carrito.getIdProducto() + "";
        String cantidad =  carrito.getCantidad() + "" ;
        String precio = carrito.getPrecio() + "";
        String total = formatearDecimales(carrito.getCantidad() * carrito.getPrecio());
        try {
            String[] args = new String[]{idProducto.trim() ,carrito.getNombre(), cantidad.toString() , precio.trim(), total};
            db.execSQL("INSERT INTO TB_CARRITO(idProducto, nombre, cantidad, precio, total) VALUES(?,?,?,?,?)", args);
            Log.i("CarritoDAO", "Se insertó");
        } catch (Exception e) {
            throw new DAOExcepcion("CarritoDAO: Error al insertar: " + e.getMessage());
        } finally {
            if (db != null) {
                db.close();
            }
        }
    }

    public List<Carrito> obtenerTodos() throws DAOExcepcion {
        Log.i("CarritoDAO", "obtenerTodos()");
        SQLiteDatabase db = _dbHelper.getReadableDatabase();
        Carrito carrito;
        List<Carrito> listCarrito = new ArrayList<Carrito>();
        try {
            Cursor c = db.rawQuery("select idProducto, nombre, cantidad, precio, total from TB_CARRITO", null);
            if (c.getCount() > 0) {
                c.moveToFirst();
                do {
                    carrito = new Carrito();
                    Integer id = c.getInt(c.getColumnIndex("idProducto"));
                    String nombre = c.getString(c.getColumnIndex("nombre"));
                    Integer cantidad = c.getInt(c.getColumnIndex("cantidad"));
                    Double precio = c.getDouble(c.getColumnIndex("precio"));
                    Double total = c.getDouble(c.getColumnIndex("total"));
                    carrito.setIdProducto(id);
                    carrito.setNombre(nombre);
                    carrito.setCantidad(cantidad);
                    carrito.setPrecio(precio);
                    carrito.setTotal(total);
                    listCarrito.add(carrito);
                } while (c.moveToNext());
            }
            c.close();
        } catch (Exception e) {
            throw new DAOExcepcion("CarritoDAO: Error al ObtenerTodos: " + e.getMessage());
        } finally {
            if (db != null) {
                db.close();
            }
        }
        return listCarrito;
    }

    public Carrito obtener(int idProducto) throws DAOExcepcion {
        Log.i("CarritoDAO", "obtenerById()");
        SQLiteDatabase db = _dbHelper.getReadableDatabase();
        Carrito carrito = null;
        String[] args = new String[] {String.valueOf(idProducto)};
        try {
            Cursor c = db.rawQuery("select idProducto, nombre, cantidad, precio, total from TB_CARRITO WHERE idProducto=?", args);
            if (c.getCount() > 0) {
                c.moveToFirst();
                do {
                    Integer id = c.getInt(c.getColumnIndex("idProducto"));
                    String nombre = c.getString(c.getColumnIndex("nombre"));
                    Integer cantidad = c.getInt(c.getColumnIndex("cantidad"));
                    Double precio = c.getDouble(c.getColumnIndex("precio"));
                    Double total = c.getDouble(c.getColumnIndex("total"));

                    carrito = new Carrito();
                    carrito.setIdProducto(id);
                    carrito.setNombre(nombre);
                    carrito.setCantidad(cantidad);
                    carrito.setPrecio(precio);
                    carrito.setTotal(total);
                } while (c.moveToNext());
            }
            c.close();
        } catch (Exception e) {
            carrito = null;
            throw new DAOExcepcion("CarritoDAO: Error al obtenerById: " + e.getMessage());
        } finally {
            if (db != null) {
                db.close();
            }
        }
        return carrito;
    }

    public void actualizar(Carrito carrito) throws DAOExcepcion {
        Log.i("CarritoDAO", "actualizar()");
        SQLiteDatabase db = _dbHelper.getWritableDatabase();

        try {
            String[] args = new String[]{String.valueOf(carrito.getIdProducto())};
            ContentValues values = new ContentValues();
            values.put("cantidad", String.valueOf(carrito.getCantidad()));
            values.put("total", formatearDecimales(carrito.getTotal()));

           // db.execSQL("UPDATE TB_CARRITO SET cantidad=?, total=?  WHERE idProducto=?", args);
            db.update("TB_CARRITO", values, "idProducto = ?", args);
        } catch (Exception e) {
            throw new DAOExcepcion("CarritoDAO: Error al Actualizar: " + e.getMessage());
        } finally {
            if (db != null) {
                db.close();
            }
        }
    }

    public void eliminar(int id) throws DAOExcepcion {
        Log.i("CarritoDAO", "eliminar()");
        SQLiteDatabase db = _dbHelper.getWritableDatabase();

        try {
            String[] args = new String[]{String.valueOf(id)};
            db.execSQL("DELETE FROM TB_CARRITO WHERE idProducto=?", args);
        } catch (Exception e) {
            throw new DAOExcepcion("CarritoDAO: Error al Eliminar: " + e.getMessage());
        } finally {
            if (db != null) {
                db.close();
            }
        }
    }

    public void eliminarTodos() throws DAOExcepcion {
        Log.i("CarritoDAO", "eliminarTodos()");
        SQLiteDatabase db = _dbHelper.getWritableDatabase();

        try {
            db.execSQL("DELETE FROM TB_CARRITO");
        } catch (Exception e) {
            throw new DAOExcepcion("CarritoDAO: Error al Eliminar Todos: " + e.getMessage());
        } finally {
            if (db != null) {
                db.close();
            }
        }
    }

}
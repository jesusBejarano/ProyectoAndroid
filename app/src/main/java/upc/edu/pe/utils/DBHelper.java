package upc.edu.pe.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Miguel Cardoso on 10/02/2016.
 */
public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DBHelper(Context context) {
        // null porque se va a usar el SQLiteCursor
        super(context, "licoreria.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE IF NOT EXISTS TB_CARRITO " +
                "(idCarrito INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "idProducto INTEGER NOT NULL, " +
                "nombre     TEXT NOT NULL,"    +
                "cantidad   INTEGER NOT NULL, " +
                "precio     REAL NOT NULL, " +
                "total      REAL NOT NULL)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS TB_CARRITO");
        onCreate(db);
    }
}


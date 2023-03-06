package com.example.juegos;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class BaseDeDatos extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "BaseDeDatosJuegos";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "puntuaciones";
    private static final String COLUMN_NAME_ID = "id";
    private static final String COLUMN_NAME_JUEGO = "juego";
    private static final String COLUMN_NAME_PUNTUACION = "puntuacion";

    private SQLiteDatabase mWritableDB;
    private SQLiteDatabase mReadableDB;

    public BaseDeDatos(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE puntuaciones (id INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT, puntuacion INTEGER)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS puntuaciones";
        db.execSQL(sql);
        onCreate(db);
    }




    public List<PuntuacionItem> getScores(){
        List<PuntuacionItem> llista = new ArrayList<PuntuacionItem>();
        String query = "SELECT * FROM puntuaciones";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()){
            do {
                PuntuacionItem puntuacionItem = new PuntuacionItem();
                puntuacionItem.setNombre(cursor.getString(1));
                puntuacionItem.setPuntuacion(cursor.getInt(2));

                llista.add(puntuacionItem);

            }while (cursor.moveToNext());
        }
        return llista;
    }


}

package com.example.juegos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class ConsultarBaseDeDatos extends AppCompatActivity {

   List<String> lista = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_base_de_datos);

        BaseDeDatos dbHelper = new BaseDeDatos(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        List<PuntuacionItem> lista = dbHelper.getScores();

        Adaptador adaptador = new Adaptador(lista);
        RecyclerView recyclerView = findViewById(R.id.recyclerview);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.setAdapter(adaptador);


    }
}
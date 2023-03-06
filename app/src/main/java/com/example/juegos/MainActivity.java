package com.example.juegos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private ListView mListView;

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public  boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.action_search:
                startActivity(new Intent (Intent.ACTION_VIEW, Uri.parse("https://www.rafelshearling.com/")));

        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] items = {getResources().getString(R.string.play3x3),
                getResources().getString(R.string.play4x4),
                getResources().getString(R.string.play5x5),
                getResources().getString(R.string.playLights),
                getResources().getString(R.string.view_scores),
                getResources().getString(R.string.settings),
        };

        ListView menuList = (ListView) findViewById(R.id.ListView_menu);

        ArrayAdapter<String> adapt = new ArrayAdapter<String>(this,R.layout.menu_item,items);
        menuList.setAdapter(adapt);

        menuList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View itemClicked,
                                    int position, long id) {
                TextView textView = (TextView) itemClicked;
                String strText = textView.getText().toString();
                if (strText.equalsIgnoreCase(getResources().getString(R.string.play3x3))) {
// Launch the Game Activity 2048 3x3
                    startActivity(new Intent(MainActivity.this, J2048_3X3.class));

                } else if (strText.equalsIgnoreCase(getResources().getString(R.string.play4x4))) {
// Launch the Game Activity 2048 4x4
                    startActivity(new Intent(MainActivity.this, J2048_4X4.class));

                } else if (strText.equalsIgnoreCase(getResources().getString(R.string.play5x5))) {
// Launch the Game Activity 2048 5x5
                    startActivity(new Intent(MainActivity.this, J2048_5x5.class));
                } else if (strText.equalsIgnoreCase(getResources().getString(R.string.playLights))) {
// Launch the Ligts On Activity
                    startActivity(new Intent(MainActivity.this, Light_Game.class));
                }else if (strText.equalsIgnoreCase(getResources().getString(R.string.settings))) {
// Launch Settings Activity
                    startActivity(new Intent (getApplicationContext(),Settings.class));
                } else if (strText.equalsIgnoreCase(getResources().getString(R.string.view_scores))) {
// Launch PuntuacionActivity
                    startActivity(new Intent (getApplicationContext(), ConsultarBaseDeDatos.class));
                }
            }
        });
    }
}


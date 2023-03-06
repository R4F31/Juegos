package com.example.juegos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Random;


public class Light_Game extends AppCompatActivity {
    private Button mButton_Solucion;
    TextView[][] lucesArray = new TextView[5][5];
    TextView[][] solucion = new TextView[5][5];
    int[][] arrayRandom = new int[5][5];
    int[][] arrayContador = new int[5][5];
    private Button mButtonNewGame;
    String [][] arrayRespaldo = new String[5][5];
    private Button mButtonBack;

    //private ActivityLightGameBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //binding = ActivityLightGameBinding.inflate(getLayoutInflater());
        //setContentView(binding.getRoot());
        setContentView(R.layout.activity_light_game);

        lucesArray[0][0] = findViewById(R.id.P00);
        lucesArray[0][1] = findViewById(R.id.P01);
        lucesArray[0][2] = findViewById(R.id.P02);
        lucesArray[0][3] = findViewById(R.id.P03);
        lucesArray[0][4] = findViewById(R.id.P04);
        lucesArray[1][0] = findViewById(R.id.P10);
        lucesArray[1][1] = findViewById(R.id.P11);
        lucesArray[1][2] = findViewById(R.id.P12);
        lucesArray[1][3] = findViewById(R.id.P13);
        lucesArray[1][4] = findViewById(R.id.P14);
        lucesArray[2][0] = findViewById(R.id.P20);
        lucesArray[2][1] = findViewById(R.id.P21);
        lucesArray[2][2] = findViewById(R.id.P22);
        lucesArray[2][3] = findViewById(R.id.P23);
        lucesArray[2][4] = findViewById(R.id.P24);
        lucesArray[3][0] = findViewById(R.id.P30);
        lucesArray[3][1] = findViewById(R.id.P31);
        lucesArray[3][2] = findViewById(R.id.P32);
        lucesArray[3][3] = findViewById(R.id.P33);
        lucesArray[3][4] = findViewById(R.id.P34);
        lucesArray[4][0] = findViewById(R.id.P40);
        lucesArray[4][1] = findViewById(R.id.P41);
        lucesArray[4][2] = findViewById(R.id.P42);
        lucesArray[4][3] = findViewById(R.id.P43);
        lucesArray[4][4] = findViewById(R.id.P44);

        mButton_Solucion = findViewById(R.id.solution);
        mButtonNewGame = findViewById(R.id.button_newGame);
        mButtonBack = findViewById(R.id.button_back);



        for(int i=0;i<5;i++){
            for(int j=0;j<5;j++){
                solucion[i][j] = lucesArray[i][j];
                solucion[i][j].setText("0");
                Random rand = new Random();
                int num = rand.nextInt(2);
                arrayRandom[i][j] = num;

            }
        }


        crearTableroJuego(arrayRandom, arrayContador,lucesArray, solucion);


        for (int i = 0;i<5;i++){
            for(int j = 0;j<5;j++){
                colorInicial(lucesArray[i][j]);
            }
        }


        mButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });


       mButton_Solucion.setOnTouchListener(new View.OnTouchListener() {
           @Override
           public boolean onTouch(View v, MotionEvent event) {
               switch (event.getAction()){
                   //Evento cuando el boton se mantiene pulsado
                   case MotionEvent.ACTION_DOWN:
                        mostrarSolucion();
                        return true;

                   case MotionEvent.ACTION_UP:
                       //Evento cuando el boton se deja de pulsar
                       volverJuego();
                       return  true;
                   default:
                       return false;
               }
           }
       });

        mButtonNewGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Light_Game.class);
                startActivity(intent);
            }
        });


    }

    /**
     *
     * @param arrayRandom
     * @param arrayContador
     * @param lucesArray
     * @param solucion
     *
     * In this method the arrayRandom created in the OnCreate() method is looked at and depending on
     * the value it has, 1 is added to the arrayContador. It also adds 1 to all the numbers around
     * it if the value of ArrayRandom is equal to 1. Later it goes through the matrix of arrayContador
     * and if the dividend of the result divided by 2 is equal to 0 then we will assign the value of
     * 0 but the value will be 1. And these results will be passed to the lucesArray matrix, which
     * will be the matrix that will be displayed on the screen.
     */

    public void crearTableroJuego(int[][]arrayRandom, int [][]arrayContador, TextView[][] lucesArray, TextView[][] solucion){
           for (int i=0;i<5;i++){
               for(int j=0;j<5;j++){
                   if (arrayRandom[i][j] ==1){
                       //Se suma en el mismo  que esta "pulsado"
                       arrayContador[i][j] +=1;
                       //Se suma 1 al de la arriba
                       if (i-1>=0){
                           arrayContador[i - 1][j] +=1;
                       }
                       //Se suma 1 al de la abajo
                       if (i+1<5){
                           arrayContador[i + 1][j] +=1;
                       }
                       //Se suma 1 al de la arriba
                       if (j-1>=0){
                           arrayContador[i][j - 1] +=1;
                       }
                       //Se suma 1 al de la abajo
                       if (j+1<5){
                           arrayContador[i][j + 1] +=1;
                       }
                   }
               }
           }

           for (int i = 0;i<5;i++){
               for (int j=0;j<5;j++){
                   int res = arrayContador[i][j];
                   if ((res%2) ==0){
                       solucion[i][j].setText("0");
                   }else{
                       solucion[i][j].setText("1");
                   }

                   if (solucion[i][j].getText().equals("1")){
                       lucesArray[i][j].setText("0");
                   }else {
                       lucesArray[i][j].setText("1");
                   }

               }
           }


    }

    /**
     *
     * @param view
     *
     * In this method, depending on the value of that view, it will be assigned an on or off shape.
     * The shapes are located in the res/drawable folder
     */

    public void colorInicial(View view) {
        TextView vista = (TextView) view;

        if (vista.getText().equals("1")) {
            vista.setBackgroundResource(R.drawable.text_shape_on);
        } else {
            vista.setBackgroundResource(R.drawable.text_shape);
        }
    }


    /**
     *
     * @param view
     *
     * In this method, depending on the value of that view, it will be assigned an on or off shape.
     * In addition, it will also modify the value of that textView.
     */

    public void canviarColor(View view) {
        TextView vista = (TextView) view;

        if (vista.getText().equals("0")) {
            vista.setText("1");
            vista.setBackgroundResource(R.drawable.text_shape_on);
        } else {
            vista.setText("0");
            vista.setBackgroundResource(R.drawable.text_shape);
        }
    }


    /**
     *
     * @param view
     * This method is used to modify the color of the circles on the sides depending on the color
     * they are when one of them has been clicked.
     * In addition, this method calls the methods actualizarSolucion() and canviarColor().
     */
    public void clicks(View view) {
        TextView vista = (TextView) view;
        canviarColor(vista);
        //Para llamar al metodo actualizar solucion
        actualizarSolucion(vista);

        int id = vista.getId();
        String nombreBoton = vista.getResources().getResourceName(id);
        String stringCords = nombreBoton.replaceAll("[^0-9]", "");

        int id_i = Integer.parseInt(String.valueOf(stringCords.charAt(0)));
        int id_j = Integer.parseInt(String.valueOf(stringCords.charAt(1)));

        //Mira hacia arriba
        if (id_i - 1 >= 0) {
            int newId_i = id_i - 1;
            int id_arriba = this.getResources().getIdentifier("P" + newId_i + id_j, "id", this.getPackageName());
            TextView id_boton_arriba = findViewById(id_arriba);
            canviarColor(id_boton_arriba);
        }

        //Mira hacia abajo
        if (id_i + 1 < 5) {
            int newId_i = id_i + 1;
            int id_abajo = this.getResources().getIdentifier("P" + newId_i + id_j, "id", this.getPackageName());
            TextView id_boton_abajo = findViewById(id_abajo);
            canviarColor(id_boton_abajo);
        }

        //Mira hacia el lado derecho
        if (id_j + 1 < 5) {
            int newId_j = id_j + 1;
            int id_derecho = this.getResources().getIdentifier("P" + id_i + newId_j, "id", this.getPackageName());
            TextView id_boton_derecho = findViewById(id_derecho);
            canviarColor(id_boton_derecho);
        }

        //Mira hacia el lado izquierdo
        if (id_j - 1 >= 0) {
            int newId_j = id_j - 1;
            int id_izq = this.getResources().getIdentifier("P" + id_i + newId_j, "id", this.getPackageName());
            TextView id_boton_izq = findViewById(id_izq);
            canviarColor(id_boton_izq);
        }
    }

    /**
     * This method saves the values in the current game array into a backup array and displays the
     * solution in the game array, that is, the buttons to press to win the game.
     */
    public void mostrarSolucion() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                arrayRespaldo[i][j] = lucesArray[i][j].getText().toString();
                lucesArray[i][j].setText(String.valueOf(arrayRandom[i][j]));
                colorInicial(lucesArray[i][j]);
            }
        }
    }

    /**
     *This method will take the values stored in the backup array and assign them to the game array.
     */
    public void volverJuego(){
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                lucesArray[i][j].setText(arrayRespaldo[i][j]);
                colorInicial(lucesArray[i][j]);
            }
        }
    }

    /**
     *
     * @param view
     *  This method checks if the textview that they have clicked is correct or not, if it is
     *  correct it removes it from the buttons that have to be clicked to win and if it is incorrect
     *  it is added to the buttons that have to be clicked to win.
     */
    public void actualizarSolucion(View view){
        TextView vista = (TextView) view;
        int id = vista.getId();

        String nombreBoton = vista.getResources().getResourceName(id);
        String stringCords = nombreBoton.replaceAll("[^0-9]", "");

        int id_i = Integer.parseInt(String.valueOf(stringCords.charAt(0)));
        int id_j = Integer.parseInt(String.valueOf(stringCords.charAt(1)));

        if (arrayRandom[id_i][id_j] == 1){
            arrayRandom[id_i][id_j] =0;
        }else {
            arrayRandom[id_i][id_j] =1;
        }
    }

}
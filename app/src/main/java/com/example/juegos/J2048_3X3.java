package com.example.juegos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class J2048_3X3 extends AppCompatActivity {

        private TextView[][] arrayJuego = new TextView[3][3];
        private int[][] valores = new int[3][3];
        private Button newGame;
        private TextView puntos;
        private Button atras;
        private int puntuacion = 0;
        private String[][] arrayRespaldo = new String[3][3];


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_j20483_x3);


            //Detecta los movimientos que se hagan dentro del gridbagLayout
            findViewById(R.id.gridLayout).setOnTouchListener(new OnSwipeTouchListener(J2048_3X3.this) {
                public void onSwipeTop() {
                    actualizarVolverAtras();
                    moverArriba(arrayJuego);
                    asignarNumero();
                    cambiarColor(arrayJuego);

                }

                //Deslizar hacia la derecha
                public void onSwipeRight() {
                    actualizarVolverAtras();
                    deslizarALadoDerecha(arrayJuego);
                    asignarNumero();
                    cambiarColor(arrayJuego);
                }

                //Hacia la izquierda
                public void onSwipeLeft() {
                    actualizarVolverAtras();
                    deslizarALadoIzq(arrayJuego);
                    asignarNumero();
                    cambiarColor(arrayJuego);
                }

                public void onSwipeBottom() {
                    actualizarVolverAtras();
                    moverAbajo(arrayJuego);
                    asignarNumero();
                    cambiarColor(arrayJuego);
                }
            });

            puntos = findViewById(R.id.puntos);

            arrayJuego[0][0] = findViewById(R.id.P00);
            arrayJuego[0][1] = findViewById(R.id.P01);
            arrayJuego[0][2] = findViewById(R.id.P02);
            arrayJuego[1][0] = findViewById(R.id.P10);
            arrayJuego[1][1] = findViewById(R.id.P11);
            arrayJuego[1][2] = findViewById(R.id.P12);
            arrayJuego[2][0] = findViewById(R.id.P20);
            arrayJuego[2][1] = findViewById(R.id.P21);
            arrayJuego[2][2] = findViewById(R.id.P22);





            atras = findViewById(R.id.button_back);
            atras.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    movimientoAtras();
                    cambiarColor(arrayJuego);
                }
            });

            newGame = findViewById(R.id.button_newGame);
            newGame.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), J2048_3X3.class);
                    startActivity(intent);
                }
            });

            asignarNumero();
            cambiarColor(arrayJuego);
        }


    /**
     * This method assigns the number 2 to a random position that is empty. What it does is check if
     * there are empty positions, if there are, it creates two random numbers that will be X and Y
     * and if the XY position is an empty position, it will assign the number 2 there, otherwise it
     * will generate another random position.
     *
     * In addition, this method checks if the game is over by checking if there are empty positions
     * and calling the moveUp() and moveToSide() methods, checking that none of the pieces can be joined.
     * In this case the game would be terminated and the score would be saved in the database.
     */
    public void asignarNumero() {
            boolean hay_posiciones_vacias = false;
            //Para comprobar si queda alguna posicion vacia en el array Bidimensional
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (arrayJuego[i][j].getText().equals("")) {
                        //Si entra aqui pondremos la variable booleana a true ya que significara
                        //que hay posiciones vacias
                        hay_posiciones_vacias = true;

                    }
                }
            }
            if (hay_posiciones_vacias == true) {
                Random rand = new Random();
                boolean sin_asignar = true;
                while (sin_asignar == true) {
                    int columna_rand = rand.nextInt(3);
                    int fila_rand = rand.nextInt(3);

                    if (arrayJuego[columna_rand][fila_rand].getText().equals("")) {
                        arrayJuego[columna_rand][fila_rand].setText("2");
                        sin_asignar = false;
                    }
                }
            }

            boolean guardarDatos = true;
            if (hay_posiciones_vacias == true || movimientoAlLado(arrayJuego) == true || movimientoArriba(arrayJuego) == true) {

            } else {
                Toast.makeText(this, "Has perdido", Toast.LENGTH_SHORT).show();
            }
            if (guardarDatos == true){
                BaseDeDatos db = new BaseDeDatos(this);
                SQLiteDatabase writableDB = db.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("nombre","3X3");
                values.put("puntuacion",Integer.parseInt(String.valueOf(puntuacion)));
                long resultado = writableDB.insert("puntuaciones", null,values);
                db.close();
                guardarDatos = false;
            }
        }

    /**
     *
     * @param arrayJuego
     * This method goes through the ArrayJuego matrix and if the text is empty it moves the numbers
     * upwards until it is the limit of the board or it is already occupied by another number.
     *
     * In addition, if two pieces are next to each other in the direction of movement, add the value
     * of the two equal pieces and leave one of the pieces with an empty value.
     */

    public void moverArriba(TextView arrayJuego[][]) {
            for (int i = 0; i < arrayJuego.length; i++) {
                for (int j = 0; j < arrayJuego[i].length; j++) {
                    for (int k = j + 1; k < 3; k++) {
                        if (arrayJuego[k][i].getText() != "") {

                            if (arrayJuego[j][i].getText() == "") {
                                arrayJuego[j][i].setText(arrayJuego[k][i].getText());
                                arrayJuego[k][i].setText("");
                                j--;

                            } else if (arrayJuego[j][i].getText().equals(arrayJuego[k][i].getText())) {
                                puntuacion = puntuacion + Integer.parseInt((String) arrayJuego[j][i].getText()) * 2;
                                arrayJuego[j][i].setText(String.valueOf((Integer.parseInt((String) arrayJuego[j][i].getText()) * 2)));
                                arrayJuego[k][i].setText("");

                            }
                            break;

                        }
                    }

                }
            }
            puntos.setText(String.valueOf(puntuacion));
        }

    /**
     *
     * @param arrayJuego
     * This method goes through the ArrayJuego matrix and if the text is empty it moves the numbers
     * down until it is the limit of the board or it is already occupied by another number.
     *
     * In addition, if two pieces are next to each other in the direction of movement, add the value
     * of the two equal pieces and leave one of the pieces with an empty value.
     */

    public void moverAbajo(TextView arrayJuego[][]) {
            for (int i = 0; i < arrayJuego.length; i++) {
                for (int j = arrayJuego[i].length - 1; j >= 0; j--) {
                    for (int k = j - 1; k >= 0; k--) {
                        if (arrayJuego[k][i].getText() != "") {

                            if (arrayJuego[j][i].getText() == "") {
                                arrayJuego[j][i].setText(arrayJuego[k][i].getText());
                                arrayJuego[k][i].setText("");
                                j++;

                            } else if (arrayJuego[j][i].getText().equals(arrayJuego[k][i].getText())) {
                                puntuacion = puntuacion + Integer.parseInt((String) arrayJuego[j][i].getText()) * 2;
                                arrayJuego[j][i].setText(String.valueOf((Integer.parseInt((String) arrayJuego[j][i].getText()) * 2)));

                                arrayJuego[k][i].setText("");
                            }

                            break;
                        }
                    }

                }
            }
            puntos.setText(String.valueOf(puntuacion));

        }

    /**
     *
     * @param arrayJuego
     * This method goes through the ArrayJuego matrix and if the text is empty it moves the numbers to the right side until it
     * is the limit of the board on the right or it is already occupied by another number.
     *
     * In addition, if two pieces are next to each other in the direction of movement, add the value
     * of the two equal pieces and leave one of the pieces with an empty value.
     */

    public void deslizarALadoDerecha(TextView[][] arrayJuego) {
            for (int i = 0; i < arrayJuego.length; i++) {
                for (int j = arrayJuego[i].length - 1; j >= 0; j--) {
                    for (int k = j - 1; k >= 0; k--) {
                        if (arrayJuego[i][k].getText() != "") {

                            if (arrayJuego[i][j].getText() == "") {
                                arrayJuego[i][j].setText(arrayJuego[i][k].getText());
                                arrayJuego[i][k].setText("");
                                j++;

                            } else if (arrayJuego[i][j].getText().equals(arrayJuego[i][k].getText())) {
                                puntuacion = puntuacion + Integer.parseInt((String) arrayJuego[i][j].getText()) * 2;
                                arrayJuego[i][j].setText(String.valueOf((Integer.parseInt((String) arrayJuego[i][j].getText()) * 2)));

                                arrayJuego[i][k].setText("");
                            }
                            break;
                        }
                    }
                }
            }
            puntos.setText(String.valueOf(puntuacion));

        }

    /**
     *
     * @param arrayJuego
     * This method goes through the ArrayJuego matrix and if the text is empty it moves the numbers to the left side
     * until it is the limit of the board on the left or it is already occupied by another number.
     *
     * In addition, if two pieces are next to each other in the direction of movement, add the value
     * of the two equal pieces and leave one of the pieces with an empty value.
     */

    public void deslizarALadoIzq(TextView arrayJuego[][]) {
            for (int i = 0; i < arrayJuego.length; i++) {
                for (int j = 0; j < arrayJuego[i].length; j++) {
                    for (int k = j + 1; k < 3; k++) {
                        if (arrayJuego[i][k].getText() != "") {

                            if (arrayJuego[i][j].getText() == "") {
                                arrayJuego[i][j].setText(arrayJuego[i][k].getText());
                                arrayJuego[i][k].setText("");
                                j--;

                            } else if (arrayJuego[i][j].getText().equals(arrayJuego[i][k].getText())) {
                                puntuacion = puntuacion + Integer.parseInt((String) arrayJuego[i][j].getText()) * 2;

                                arrayJuego[i][j].setText(String.valueOf((Integer.parseInt((String) arrayJuego[i][j].getText()) * 2)));

                                arrayJuego[i][k].setText("");
                            }
                            break;
                        }
                    }
                }
            }
            puntos.setText(String.valueOf(puntuacion));

        }

    /**
     *
     * @param arrayJuego
     *  This method goes through the ArrayJuego matrix and depending on the value it contains in each position, it assigns
     *  a different shape for each value.
     */

    public void cambiarColor(TextView arrayJuego[][]) {
            for (int i = 0; i < arrayJuego.length; i++) {
                for (int j = 0; j < arrayJuego[i].length; j++) {
                    if (arrayJuego[i][j].getText().equals("")) {
                        arrayJuego[i][j].setBackgroundResource(R.drawable.text_shape_2048);
                    } else if (arrayJuego[i][j].getText().equals("2")) {
                        arrayJuego[i][j].setBackgroundResource(R.drawable.text_shape_2048_value2);
                    } else if (arrayJuego[i][j].getText().equals("4")) {
                        arrayJuego[i][j].setBackgroundResource(R.drawable.text_shape_2048_value4);
                    } else if (arrayJuego[i][j].getText().equals("8")) {
                        arrayJuego[i][j].setBackgroundResource(R.drawable.text_shape_2048_value8);
                    } else if (arrayJuego[i][j].getText().equals("16")) {
                        arrayJuego[i][j].setBackgroundResource(R.drawable.text_shape_2048_value16);
                    } else if (arrayJuego[i][j].getText().equals("32")) {
                        arrayJuego[i][j].setBackgroundResource(R.drawable.text_shape_2048_value32);
                    } else if (arrayJuego[i][j].getText().equals("64")) {
                        arrayJuego[i][j].setBackgroundResource(R.drawable.text_shape_2048_value64);
                    } else if (arrayJuego[i][j].getText().equals("128")) {
                        arrayJuego[i][j].setBackgroundResource(R.drawable.text_shape_2048_value128);
                    } else if (arrayJuego[i][j].getText().equals("256")) {
                        arrayJuego[i][j].setBackgroundResource(R.drawable.text_shape_2048_value256);
                    } else if (arrayJuego[i][j].getText().equals("512")) {
                        arrayJuego[i][j].setBackgroundResource(R.drawable.text_shape_2048_value512);
                    } else if (arrayJuego[i][j].getText().equals("1024")) {
                        arrayJuego[i][j].setBackgroundResource(R.drawable.text_shape_2048_value1024);
                    } else if (arrayJuego[i][j].getText().equals("2048")) {
                        arrayJuego[i][j].setBackgroundResource(R.drawable.text_shape_2048_value2048);
                    }
                }
            }
        }


    /**
     *
     * @param arrayJuego
     * @return Boolean
     *
     * This method checks if two pieces could be joined by moving upwards. This method returns a
     * boolean value and is used to check if the game has finished in the assigarNumero() method.
     */
    public Boolean movimientoArriba(TextView arrayJuego[][]) {
            Boolean movimientoDisponible = false;
            for (int i = 0; i < arrayJuego.length; i++) {
                for (int j = arrayJuego[i].length - 1; j >= 0; j--) {
                    for (int k = j - 1; k >= 0; k--) {
                        if (arrayJuego[k][i].getText() != "") {
                            if (arrayJuego[j][i].getText().equals(arrayJuego[k][i].getText())) {
                                movimientoDisponible = true;
                            }
                            break;
                        }
                    }

                }
            }
            return movimientoDisponible;
    }

    /**
     *
     * @param arrayJuego
     * @return boolean value
     *
     * This method checks if two pieces could be joined by moving to the side. This method returns a
     * boolean value and is used to check if the game has finished in the asignarNumero() method.
     */
    public Boolean movimientoAlLado(TextView arrayJuego[][]) {
            Boolean movimientoDisponible = false;
            for (int i = 0; i < arrayJuego.length; i++) {
                for (int j = 0; j < arrayJuego[i].length; j++) {
                    for (int k = j + 1; k < 3; k++) {
                        if (arrayJuego[i][k].getText() != "") {
                            if (arrayJuego[i][j].getText().equals(arrayJuego[i][k].getText())) {
                                movimientoDisponible = true;
                            }
                            break;
                        }
                    }
                }
            }
            return movimientoDisponible;
    }


    /**
     * This method is used to update the support matrix with a movement prior to the current matrix
     * where the game is executed.
     */
    public void actualizarVolverAtras(){
            for (int i=0;i<arrayJuego.length;i++){
                for (int j=0;j<arrayJuego[i].length;j++){
                    arrayRespaldo[i][j] = arrayJuego[i][j].getText().toString();
                }
            }
    }



    /**
     * What this method will do is return the current matrix that is being played to the backup
     * matrix that saves the matrix from a previous movement.
     */
    public void movimientoAtras(){
            for (int i=0;i<arrayJuego.length;i++){
                for (int j=0;j<arrayJuego[i].length;j++){
                    arrayJuego[i][j].setText(arrayRespaldo[i][j]);
                }
            }
    }

}



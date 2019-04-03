package com.example.tetris;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayout;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    /*Datos de matriz:
    *   0 = nada
    *   1 = L {(x,y),(x+1,y),(x+2,y),(x+2,y-1)}
    *   2 = J {(x,y),(x+1,y),(x+2,y),(x+2,y+1)}
    *   3 = S {(x,y),(x+1,y),(x+1,y-1),(x+2,y-1)}
    *   4 = Z {(x,y),(x+1,y),(x+1,y+1),(x+2,y+1)}
    *   5 = T {(x,y),(x+1,y-1),(x+1,y),(x+1,y+1)}
    *   6 = I {(x,y),(x+1,y),(x+2,y),(x+3,y)}
    *   7 = O {(x,y),(x,y+1),(x+1,y),(x+1,y+1)}*/

    GridLayout grid;
    boolean pausado;
    boolean iniciado;
    boolean primerFicha;
    Button btnPausar;
    Button btnIniciar;
    int tipoFichaActual;
    ArrayList<ArrayList<Integer>> matrizLogica;
    ArrayList<ArrayList<Integer>> matrizGrafica;

    void pintarTablero(){
        int child = 0;
        for(int i=0; i<11; i++){
            for(int j=0; j<11; j++){
                ImageView view = (ImageView) grid.getChildAt(child);
                if(i==0 || i==10 || j==0 || j==10){
                    view.setImageResource(android.R.color.darker_gray);
                }
                else{
                    switch (matrizGrafica.get(i).get(j)){
                        case 1: view.setImageResource(android.R.color.holo_orange_dark);break;
                        case 2: view.setImageResource(android.R.color.holo_blue_dark);break;
                        case 3: view.setImageResource(android.R.color.holo_green_dark);break;
                        case 4: view.setImageResource(android.R.color.holo_red_dark);break;
                        case 5: view.setImageResource(android.R.color.holo_purple);break;
                        case 6: view.setImageResource(android.R.color.holo_blue_bright);break;
                        case 7: view.setImageResource(R.color.amarillo);break;
                        default: view.setImageResource(R.color.fondo);break;
                    }
                }
                child++;
            }
        }
    }

    public void onBtnIniciar(View view){
        if(iniciado){
            matrizGrafica.clear();
            inicializarMatriz();
            iniciado = pausado = primerFicha = false;
            btnIniciar.setText("Iniciar");
            btnPausar.setText("Detener");
        }
        else{
            iniciado = pausado = primerFicha = true;
            btnIniciar.setText("Reiniciar");
        }
    }

    public void onBtnPausar(View view){
        if(pausado){
            pausado = false;
            btnPausar.setText("Reanudar");
        }
        else{
            pausado = true;
            btnPausar.setText("Detener");
        }
    }

    public void onBtnIzq(View view){
        int tamano = matrizLogica.size();
        boolean izquierda = false;
        for(int i = 0; i < tamano; i++){
            for(int j = 0; j < tamano; j++){
                if(matrizLogica.get(i).get(j) == 1){
                    if(j == 1 || matrizLogica.get(i).get(j-1) == 2 || izquierda){
                        izquierda = true;
                    }
                    else {
                        matrizGrafica.get(i).set(j, 0);
                        matrizLogica.get(i).set(j, 0);
                        matrizLogica.get(i).set(j-1, 1);
                    }
                }
            }
        }
        for(int i = 0; i < tamano; i++){
            for(int j = 0; j < tamano; j++){
                if(matrizLogica.get(i).get(j) == 1){
                    matrizGrafica.get(i).set(j, tipoFichaActual);
                }
            }
        }
        pintarTablero();
    }

    public void onBtnDer(View view){
        int tamano = matrizLogica.size()-1;
        boolean derecha = false;
        for(int i = tamano; i >= 0; i--){
            for(int j = tamano; j >= 0; j--){
                if(matrizLogica.get(i).get(j) == 1){
                    if(j == tamano|| matrizLogica.get(i).get(j+1) == 2 || derecha){
                        derecha = true;
                    }
                    else {
                        matrizGrafica.get(i).set(j, 0);
                        matrizLogica.get(i).set(j, 0);
                        matrizLogica.get(i).set(j+1, 1);
                    }
                }
            }
        }
        for(int i = tamano; i >= 0; i--){
            for(int j = tamano; j >= 0; j--){
                if(matrizLogica.get(i).get(j) == 1){
                    matrizGrafica.get(i).set(j, tipoFichaActual);
                }
            }
        }
        pintarTablero();
    }

    public void onBtnGirar(View view){

    }

    void bajarFicha(){
        int tamano = matrizLogica.size()-1;
        boolean fondo = false;
        for(int i = tamano; i >= 0; i--){
            for(int j = tamano; j >= 0; j--){
                if(matrizLogica.get(i).get(j) == 1){
                    if(i == tamano || matrizLogica.get(i+1).get(j) == 2||fondo){
                        fondo = true;
                        matrizLogica.get(i).set(j, 2);
                    }
                    else {
                        matrizGrafica.get(i).set(j, 0);
                        matrizLogica.get(i).set(j, 0);
                        matrizLogica.get(i + 1).set(j, 1);
                    }
                }
            }
        }
        for(int i = tamano; i >= 0; i--){
            for(int j = tamano; j >= 0; j--){
                if(matrizLogica.get(i).get(j) == 1){
                    matrizGrafica.get(i).set(j, tipoFichaActual);
                }
            }
        }
        if(fondo){
            borrarFilas();
            int a = new Random().nextInt(7) + 1;
            setFichaActual(a, 1, 5);
        }
    }

    void borrarFilas(){
        int tamano = matrizLogica.size()-2;
        ArrayList<Integer> columna;
        for(int i = tamano; i >= 0; i--){
            columna = matrizLogica.get(i);
            Log.i("Info", columna.toString());
            for(int j = tamano; j >= 0; j--)
                Log.i("Info",columna.get(j).toString());
        }
    }

    boolean tocaTecho(){
        return false;
    }

    void pierde(){

    }

    void setFichaActual(int modo, int fil, int col){
        tipoFichaActual = modo;
        switch (modo){
            case 1://L
                matrizGrafica.get(fil).set(col,1);      matrizLogica.get(fil).set(col,1);
                matrizGrafica.get(fil+1).set(col,1);    matrizLogica.get(fil+1).set(col,1);
                matrizGrafica.get(fil+2).set(col,1);    matrizLogica.get(fil+2).set(col,1);
                matrizGrafica.get(fil+2).set(col+1,1);  matrizLogica.get(fil+2).set(col+1,1);
                break;
            case 2://J
                matrizGrafica.get(fil).set(col,2);      matrizLogica.get(fil).set(col,1);
                matrizGrafica.get(fil+1).set(col,2);    matrizLogica.get(fil+1).set(col,1);
                matrizGrafica.get(fil+2).set(col,2);    matrizLogica.get(fil+2).set(col,1);
                matrizGrafica.get(fil+2).set(col-1,2);  matrizLogica.get(fil+2).set(col-1,1);
                break;
            case 3: //S
                matrizGrafica.get(fil).set(col,3);      matrizLogica.get(fil).set(col,1);
                matrizGrafica.get(fil+1).set(col,3);    matrizLogica.get(fil+1).set(col,1);
                matrizGrafica.get(fil+1).set(col+1,3);  matrizLogica.get(fil+1).set(col+1,1);
                matrizGrafica.get(fil+2).set(col+1,3);  matrizLogica.get(fil+2).set(col+1,1);
                break;
            case 4: //Z
                matrizGrafica.get(fil).set(col,4);      matrizLogica.get(fil).set(col,1);
                matrizGrafica.get(fil+1).set(col,4);    matrizLogica.get(fil+1).set(col,1);
                matrizGrafica.get(fil+1).set(col-1,4);  matrizLogica.get(fil+1).set(col-1,1);
                matrizGrafica.get(fil+2).set(col-1,4);  matrizLogica.get(fil+2).set(col-1,1);
                break;
            case 5: //T
                matrizGrafica.get(fil).set(col,5);      matrizLogica.get(fil).set(col,1);
                matrizGrafica.get(fil+1).set(col-1,5);  matrizLogica.get(fil+1).set(col-1,1);
                matrizGrafica.get(fil+1).set(col,5);    matrizLogica.get(fil+1).set(col,1);
                matrizGrafica.get(fil+1).set(col+1,5);  matrizLogica.get(fil+1).set(col+1,1);
                break;
            case 6: //I
                matrizGrafica.get(fil).set(col,6);      matrizLogica.get(fil).set(col,1);
                matrizGrafica.get(fil+1).set(col,6);    matrizLogica.get(fil+1).set(col,1);
                matrizGrafica.get(fil+2).set(col,6);    matrizLogica.get(fil+2).set(col,1);
                matrizGrafica.get(fil+3).set(col,6);    matrizLogica.get(fil+3).set(col,1);
                break;
            case 7: // O
                matrizGrafica.get(fil).set(col,7);      matrizLogica.get(fil).set(col,1);
                matrizGrafica.get(fil).set(col+1,7);    matrizLogica.get(fil).set(col+1,1);
                matrizGrafica.get(fil+1).set(col,7);    matrizLogica.get(fil+1).set(col,1);
                matrizGrafica.get(fil+1).set(col+1,7);  matrizLogica.get(fil+1).set(col+1,1);
                break;
            default:
                break;
        }
    }

    void inicializarMatriz(){
        matrizGrafica = new ArrayList<>();
        matrizLogica = new ArrayList<>();
        ArrayList<Integer> col;
        ArrayList<Integer> col2;
        for(int i=0; i<10; i++){
            col = new ArrayList<>();
            col2 = new ArrayList<>();
            for(int j=0; j<10; j++){
                col.add(0);
                col2.add(0);
            }
            matrizLogica.add(col);
            matrizGrafica.add(col2);
        }
    }

    void leerMatriz(ArrayList<ArrayList<Integer>> matriz){
        for(int i=0; i<10; i++){
            Log.i("Info", String.format("%s", matriz.get(i).toString()));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnIniciar = findViewById(R.id.btnIniciar);
        btnPausar = findViewById(R.id.btnParar);
        grid = findViewById(R.id.gridLayout);
        inicializarMatriz();

        int colCount = grid.getColumnCount();
        int rowCount = grid.getRowCount();
        for (int i=0; i < colCount*rowCount; i++){
            ImageView cuadro = new ImageView(this);
            cuadro.setId(i);
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            cuadro.setLayoutParams(params);
            ViewGroup.MarginLayoutParams marginParams = new ViewGroup.MarginLayoutParams(cuadro.getLayoutParams());
            marginParams.setMargins(2, 2, 2, 2);
            cuadro.setLayoutParams(marginParams);
            cuadro.requestLayout();
            cuadro.getLayoutParams().height = (grid.getLayoutParams().height/11)-3;
            cuadro.getLayoutParams().width = (grid.getLayoutParams().width/11)-3;
            grid.addView(cuadro);
        }

        pintarTablero();
        primerFicha = iniciado = pausado = false;

        new Thread(){
            public void run(){
                while(true){
                    try{
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if(primerFicha){
                                    int a = new Random().nextInt(7) + 1;
                                    setFichaActual(a, 1, 5);
                                    primerFicha = false;
                                }
                                pintarTablero();
                                if(iniciado){
                                    if (pausado) {
                                        bajarFicha();
                                        if(tocaTecho())
                                            pierde();
                                    }
                                }
                            }
                        });
                        Thread.sleep(1200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }
}

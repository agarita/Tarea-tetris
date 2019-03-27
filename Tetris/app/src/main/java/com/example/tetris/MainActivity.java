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

    ArrayList<ArrayList<Integer>> matriz;
    GridLayout grid;
    ArrayList<ArrayList<Integer>> fichaActual;
    Button btnIniciar;
    Button btnPausar;
    int tipoFichaActual;
    boolean activo;
    boolean pausado;

    void pintarTablero(){
        int child = 0;
        for(int i=0; i<11; i++){
            for(int j=0; j<11; j++){
                ImageView view = (ImageView) grid.getChildAt(child);
                if(i==0 || i==10 || j==0 || j==10){
                    view.setImageResource(android.R.color.darker_gray);
                }
                else{
                    switch (matriz.get(i).get(j)){
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
        if(!activo){
            inicializarMatriz();
            activo = true;
            pausado = false;
            btnIniciar.setText("Reiniciar");
        }
    }

    public void onBtnPausar(View view){
        if(!pausado){
            pausado = true;
            btnPausar.setText("Reanudar");
        }
        else{
            pausado = false;
            btnPausar.setText("Detener");
        }
    }

    void bajarFicha(){
        int fil,col;
        for(int i=0; i<4; i++){
            fil = fichaActual.get(i).get(0);
            col = fichaActual.get(i).get(1);
            matriz.get(fil).set(col,0);
            int valor = fichaActual.get(i).get(1);
            fichaActual.get(i).set(1, valor+1);
            fil = fichaActual.get(i).get(0);
            col = fichaActual.get(i).get(1);
            Log.i("Info", String.format("%d,%d", fil, col));
            matriz.get(fil).set(col,tipoFichaActual);
        }
    }

    void setFichaActual(int modo, int fil, int col){
        ArrayList<Integer> pos;
        pos = new ArrayList<>();
        fichaActual.clear();
        tipoFichaActual = modo;
        switch (modo){
            case 1://L
                pos.clear();pos.add(fil); pos.add(col);
                fichaActual.add(pos);
                pos.clear();pos.add(fil+1); pos.add(col);
                fichaActual.add(pos);
                pos.clear();pos.add(fil+2); pos.add(col);
                fichaActual.add(pos);
                pos.clear();pos.add(fil+2); pos.add(col+1);
                fichaActual.add(pos);
                break;
            case 2://J
                pos.clear();pos.add(fil); pos.add(col);
                fichaActual.add(pos);
                pos.clear();pos.add(fil+1); pos.add(col);
                fichaActual.add(pos);
                pos.clear();pos.add(fil+2); pos.add(col);
                fichaActual.add(pos);
                pos.clear();pos.add(fil+2); pos.add(col-1);
                fichaActual.add(pos);
                break;
            case 3: //S
                pos.clear();pos.add(fil); pos.add(col);
                fichaActual.add(pos);
                pos.clear();pos.add(fil+1); pos.add(col);
                fichaActual.add(pos);
                pos.clear();pos.add(fil+1); pos.add(col+1);
                fichaActual.add(pos);
                pos.clear();pos.add(fil+2); pos.add(col+1);
                fichaActual.add(pos);
                break;
            case 4: //Z
                pos.clear();pos.add(fil); pos.add(col);
                fichaActual.add(pos);
                pos.clear();pos.add(fil+1); pos.add(col);
                fichaActual.add(pos);
                pos.clear();pos.add(fil+1); pos.add(col-1);
                fichaActual.add(pos);
                pos.clear();pos.add(fil+2); pos.add(col-1);
                fichaActual.add(pos);
                break;
            case 5: //T
                pos.clear();pos.add(fil); pos.add(col);
                fichaActual.add(pos);
                pos.clear();pos.add(fil+1); pos.add(col-1);
                fichaActual.add(pos);
                pos.clear();pos.add(fil+1); pos.add(col);
                fichaActual.add(pos);
                pos.clear();pos.add(fil+1); pos.add(col+1);
                fichaActual.add(pos);
                break;
            case 6: //I
                pos.clear();pos.add(fil); pos.add(col);
                fichaActual.add(pos);
                pos.clear();pos.add(fil+1); pos.add(col);
                fichaActual.add(pos);
                pos.clear();pos.add(fil+2); pos.add(col);
                fichaActual.add(pos);
                pos.clear();pos.add(fil+3); pos.add(col);
                fichaActual.add(pos);
                break;
            case 7: // O
                pos.clear();pos.add(fil); pos.add(col);
                fichaActual.add(pos);
                pos.clear();pos.add(fil); pos.add(col+1);
                fichaActual.add(pos);
                pos.clear();pos.add(fil+1); pos.add(col);
                fichaActual.add(pos);
                pos.clear();pos.add(fil+1); pos.add(col+1);
                fichaActual.add(pos);
                break;
            default:
                break;
        }
    }

    void inicializarMatriz(){
        matriz = new ArrayList<>();
        ArrayList<Integer> col = new ArrayList<>();
        for(int i=0; i<10; i++){
            col.clear();
            for(int j=0; j<10; j++){
                col.add(0);
            }
            matriz.add(col);
        }
    }

    void leerMatriz(){
        for(int i=0; i<10; i++){
            Log.i("Info", String.format("%s", matriz.get(i).toString()));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fichaActual = new ArrayList<>();

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
        activo = false;
        pausado = false;

        setFichaActual(1,2,2);

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    while(true) {
                        if (pausado) {
                            Thread.sleep(1000);
                            Log.i("Info", "1seg");
                            //bajarFicha();
                            pintarTablero();
                            leerMatriz();
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

    }
}

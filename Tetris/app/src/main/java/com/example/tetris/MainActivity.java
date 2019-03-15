package com.example.tetris;

import android.media.Image;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayout;
import android.util.Log;
import android.widget.GridView;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GridLayout grid = (GridLayout) findViewById(R.id.gridLayout2);

        grid.setColumnCount(10);
        grid.setRowCount(10);

        int colCount = grid.getColumnCount();
        int rowCount = grid.getRowCount();
        for (int i=0; i < colCount*rowCount; i++){
            grid.addView(new ImageView(this));
        }

        int count = grid.getChildCount();

        for (int i=0; i < count; i++){
            ImageView view = (ImageView) grid.getChildAt(i);
            view.setImageResource(R.drawable.cuadro);
        }

        Log.i("Info", String.valueOf(count));

    }
}

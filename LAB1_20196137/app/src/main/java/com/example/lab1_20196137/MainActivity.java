package com.example.lab1_20196137;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.textView3);
        registerForContextMenu(textView);
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.menu_context, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item){

        if (item.getItemId() == R.id.color_blue){
            textView.setTextColor(Color.BLUE);
            return true;
        } else if (item.getItemId() == R.id.color_green){
            textView.setTextColor(Color.GREEN);
            return true;
        } else if (item.getItemId() == R.id.color_red) {
            textView.setTextColor(Color.RED);
            return true;
        } else {
            return super.onContextItemSelected(item);
        }
    }

    public void abrirActivityJugar(View view){
        Intent intent = new Intent(this, MainActivity2.class);
        startActivity(intent);
    }

}
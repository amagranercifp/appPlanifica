package com.example.appplanifica;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class BienvenidaUsuario extends AppCompatActivity {
    TextView tvUsuario;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bienvenida_usuario);

        Intent i = getIntent();

        tvUsuario = findViewById(R.id.tvUsuario);

        tvUsuario.setText(i.getStringExtra("correo"));

        
    }
}
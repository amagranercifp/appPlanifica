package com.example.appplanifica;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;

public class RegistrarUsuario extends AppCompatActivity {

    EditText etNombre, etCorreo, etPassword;
    Button btnRegistrar;

    Spinner spGrupo;

    ArrayList<String> grupos = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_usuario);

        etNombre = findViewById(R.id.etNombre);

        etCorreo = findViewById(R.id.etCorreo);

        etPassword = findViewById(R.id.etPassword);

        btnRegistrar = findViewById(R.id.btnRegistrar);

        spGrupo = findViewById(R.id.spGrupo);

        grupos.add("1DAM");
        grupos.add("2DAM");
        grupos.add("1DAW");
        grupos.add("2DAW");
        grupos.add("1ASI");
        grupos.add("2ASI");
        grupos.add("1SMT");
        grupos.add("2SMT");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,grupos);

        spGrupo.setAdapter(adapter);

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
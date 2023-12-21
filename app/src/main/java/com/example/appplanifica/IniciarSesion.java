package com.example.appplanifica;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class IniciarSesion extends AppCompatActivity {

    EditText etCorreo, etPassword;
    Button btnIniciar;

    private ProgressBar progressbar;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar_sesion);

        mAuth = FirebaseAuth.getInstance();

        etCorreo = findViewById(R.id.etCorreo);

        etPassword = findViewById(R.id.etPassword);

        btnIniciar = findViewById(R.id.btnIniciar);

        progressbar = findViewById(R.id.progressbar);

        btnIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = etCorreo.getText().toString();
                String password = etPassword.getText().toString();

                logIn(email,password);
            }
        });

    }

    private void logIn(String email,String password)
    {

        // Activamos el progress bar
        progressbar.setVisibility(View.VISIBLE);

        // Validamos que los campos no esten vacios
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(),
                            "Please enter email!!",
                            Toast.LENGTH_LONG)
                    .show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(),
                            "Please enter password!!",
                            Toast.LENGTH_LONG)
                    .show();
            return;
        }

        // Método firebase para iniciar sesion
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(
                        new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(
                                    @NonNull Task<AuthResult> task)
                            {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(),
                                                    "Login successful!!",
                                                    Toast.LENGTH_LONG)
                                            .show();

                                    // ocultamos progress bar
                                    progressbar.setVisibility(View.GONE);

                                    // si el login es correcto
                                    // saltamos al Activity Principal
                                    Intent intent
                                            = new Intent(IniciarSesion.this,
                                            BienvenidaUsuario.class);
                                    intent.putExtra("correo",email);
                                    startActivity(intent);
                                }

                                else {

                                    // sign-in failed
                                    Toast.makeText(getApplicationContext(),
                                                    "Algo ha ido mal!! Motivo: "
                                                            + task.getException().getMessage().toString(),
                                                    Toast.LENGTH_LONG)
                                            .show();

                                    // hide the progress bar
                                    progressbar.setVisibility(View.GONE);
                                }
                            }
                        });
    }
}
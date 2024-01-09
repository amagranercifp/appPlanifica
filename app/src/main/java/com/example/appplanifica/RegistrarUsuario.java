package com.example.appplanifica;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.appplanifica.Datos.Estudiante;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RegistrarUsuario extends AppCompatActivity {

    EditText etNombre, etCorreo, etPassword;
    Button btnRegistrar;

    Spinner spGrupo;

    ProgressBar progressbar;

    ArrayList<String> grupos = new ArrayList<>();

    FirebaseFirestore mFirestore;
    FirebaseAuth mAuth;

    String nombre, email, password, grupo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_usuario);

        // Firebase

        mFirestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();



        etNombre = findViewById(R.id.etNombre);

        etCorreo = findViewById(R.id.etCorreo);

        etPassword = findViewById(R.id.etPassword);

        btnRegistrar = findViewById(R.id.btnRegistrar);

        spGrupo = findViewById(R.id.spGrupo);

        progressbar = findViewById(R.id.progressbar);

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

        // Agrega un listener al Spinner para obtener el valor seleccionado
        spGrupo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Obtén el valor seleccionado
                grupo = parentView.getItemAtPosition(position).toString();

                // Puedes hacer lo que necesites con el valor seleccionado
                // Por ejemplo, imprimirlo en el Log

                Toast.makeText(getApplicationContext(),"Grupo seleccionado: "+grupo,Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Manejar el caso en el que no se ha seleccionado nada (opcional)
                grupo = grupos.get(0);

                Toast.makeText(getApplicationContext(),"Grupo NO seleccionado: "+grupo,Toast.LENGTH_LONG).show();
            }
        });

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                nombre = etNombre.getText().toString();
                email = etCorreo.getText().toString();
                password = etPassword.getText().toString();

                registrarNuevoUsuario(email,password);
            }
        });
    }


    private void registrarNuevoUsuario(String email, String password)
    {

        // Activamos el progress bar para simular carga de proceso
        progressbar.setVisibility(View.VISIBLE);

        // Validaciones de al menos campos no vacíos
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

        // Método firebase para crear nuevo usuario a partir de correo y password
        mAuth
                .createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(),
                                    "Registro realizado correctamente!",
                                            Toast.LENGTH_LONG)
                                    .show();

                            // hide the progress bar
                            progressbar.setVisibility(View.GONE);

                            // en este momento, el usuario ya se ha registrado,
                            // ahora podemos guardar toda la info extra en la BD de Firestore.

                            Estudiante e = new Estudiante(nombre, email, password, grupo);

                            añadeDatosExtraUsuario(e);

                            // si el usuario se ha creado volvemos al Activity Principal para que se pueda logear
                            Intent intent
                                    = new Intent(RegistrarUsuario.this,
                                    MainActivity.class);
                            startActivity(intent);
                        }
                        else {

                            Toast.makeText(
                                            getApplicationContext(),
                                            "El registro ha fallado!! Motivo:"
                                                    + task.getException().getMessage().toString(),
                                            Toast.LENGTH_LONG)
                                    .show();


                            // ocultamos finalmente la barra de progresso
                            progressbar.setVisibility(View.GONE);
                        }
                    }
                });
    }


    private void añadeDatosExtraUsuario(Estudiante e){

        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();


        // Crear un nuevo usuario con información adicional
        Map<String, Object> user = new HashMap<>();
        user.put("nombre", e.getNombre());
        user.put("email", e.getEmail());
        user.put("grupo", e.getGrupo());

        // Agregar el usuario a la colección "usuarios" en Firestore
        mFirestore.collection("usuarios").document(userId)
                .set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("REGISTRAR_USUARIO", "Usuario agregado correctamente a Firestore");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("REGISTRAR_USUARIO", "Error al agregar usuario a Firestore", e);
                    }
                });

    }
}
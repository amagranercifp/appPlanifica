package com.example.appplanifica.Datos;

import java.util.ArrayList;

public class Estudiante {

    private String nombre;
    private String email;
    private String password;

    private String grupo;

    private ArrayList<String> modulos;

    public Estudiante(String nombre, String email, String password, String grupo) {
        this.nombre = nombre;
        this.email = email;
        this.password = password;
        this.grupo = grupo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    public ArrayList<String> getModulos() {
        return modulos;
    }

    public void setModulos(ArrayList<String> modulos) {
        this.modulos = modulos;
    }
}

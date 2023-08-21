/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package aamg20230821.entidadesdenegocio;

/**
 *
 * @author Adonis Molina
 */
public class Libro {
    private int id;
    private String titulo;
    private String autor;
    private int añoPublicación;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public int getAñoPublicación() {
        return añoPublicación;
    }

    public void setAñoPublicación(int añoPublicación) {
        this.añoPublicación = añoPublicación;
    }
}

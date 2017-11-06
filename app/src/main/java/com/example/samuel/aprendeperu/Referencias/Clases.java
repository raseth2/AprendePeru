package com.example.samuel.aprendeperu.Referencias;

/**
 * Created by Samuel on 6/11/2017.
 */

public class Clases {
    private String UserId;
    private String Asignatura;
    private String Local;
    private String MaxAlumnos;
    private String Costo;


    public Clases(){
    }
    public Clases(String UserId,String Asignatura,String Local,String MaxAlumnos, String Costo){

        this.UserId = UserId;
        this.Asignatura = Asignatura;
        this.Local = Local;
        this.MaxAlumnos = MaxAlumnos;
        this.Costo = Costo;
       // this.movieRating = movieRating;
    }
    public String getUserId() {
        return UserId;
    }
    public void setUserId(String UserId) {
        this.UserId = UserId;
    }

    public String getAsignatura() {
        return Asignatura;
    }
    public void setAsignatura(String Asignatura) {
        this.Asignatura = Asignatura;
    }

    public String getLocal() {  return Local;}
    public void setLocal(String Local) {this.Local = Local;}

    public String getMaxAlumnos() {  return MaxAlumnos;}
    public void setMaxAlumnos(String MaxAlumnos) {
        this.MaxAlumnos = MaxAlumnos;
    }

    public String getCosto() {  return Costo;}
    public void setCosto(String Costo) {
        this.Costo = Costo;
    }

    /*public float getMovieRating() {
        return movieRating;
    }
    public void setMovieRating(float movieRating) {
        this.movieRating = movieRating;
    }*/
}

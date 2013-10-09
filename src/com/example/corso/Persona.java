package com.example.corso;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by gabrieleciaccia on 16/09/13.
 */
public class Persona implements Parcelable{
    private long idPersona;
    private String nome;
    private String cognome;
    private String dataNascita;
    private String numTelefono;


    public Persona(long idPersona){
        this.idPersona = idPersona;
    }

    public Persona(Parcel source){
        readFromParcel(source);
    }

    //setter e getter nome
    public void setNome(String nome){
        this.nome = nome;
    }

    public String getNome(){
        return nome;
    }

    public String getDataNascita() {
        return dataNascita;
    }

    public void setDataNascita(String dataNascita) {
        this.dataNascita = dataNascita;
    }

    public long getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(long idPersona) {
        this.idPersona = idPersona;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getNumTelefono() {
        return numTelefono;
    }

    public void setNumTelefono(String numTelefono) {
        this.numTelefono = numTelefono;
    }

    @Override
    public int describeContents() {
        return 0;
    }


    public static final Parcelable.Creator<Persona> CREATOR =
            new Parcelable.Creator<Persona>(){

                @Override
                public Persona createFromParcel(Parcel source) {
                    return new Persona(source);
                }

                @Override
                public Persona[] newArray(int size) {
                    return new Persona[size];
                }
            };



    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(idPersona);
        dest.writeString(nome);
        dest.writeString(cognome);
        dest.writeString(dataNascita);
        dest.writeString(numTelefono);

    }

    public void readFromParcel(Parcel source){
        idPersona = source.readLong();
        nome = source.readString();
        cognome = source.readString();
        dataNascita = source.readString();
        numTelefono = source.readString();
    }
}




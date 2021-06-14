package com.example.lab2;

import android.provider.ContactsContract;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

//adnotacja oznacza ze klasa reprezentuje tabele w bazie
@Entity(tableName = "nazwa_tabeli")
public class Element {

    //pierwsza adnotacja oznacza ze pole jest kluczem glownym wartosci klucza beda generowane automatycznie
    //druga adnotacja okresla jaka bedzie nazwa kolumny i tabeli
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long id;

    //pierwsza adnotacja okresla ze pole nie moze byc puste
    //druga adnotacja okresla jaka bedzie nazwa kolumny w tabeli
    @NonNull
    @ColumnInfo(name = "producent")
    private String producent;

    @NonNull
    @ColumnInfo(name = "model")
    private String model;

    @NonNull
    @ColumnInfo(name = "wersja_androida")
    private String wersjaAndroida;

    @NonNull
    @ColumnInfo(name = "strona_www")
    private String stronaWww;

    //konstruktor wykorzystany przez room do tworzenia obiektu
    public Element(@NonNull String producent, @NonNull String model, @NonNull String wersjaAndroida, @NonNull String stronaWww) {
        this.producent = producent;
        this.model = model;
        this.wersjaAndroida = wersjaAndroida;
        this.stronaWww = stronaWww;
    }

    @Ignore
    public Element(long id, @NonNull String producent, @NonNull String model, @NonNull String wersjaAndroida, @NonNull String stronaWww) {
        this.id = id;
        this.producent = producent;
        this.model = model;
        this.wersjaAndroida = wersjaAndroida;
        this.stronaWww = stronaWww;
    }

    //jezeli konieczne sa dodatkowe konstruktory nalezy je poprzedzic adnotacja @Ignore
    //zeby biblioteka room z nich nie korzystala

    //room moze wymagaac rowniez getterow i setterow takze warto je utworzyc


    public long getId() {
        return id;
    }

    public void setId(long id){
        this.id = id;
    }

    @NonNull
    public String getProducent() {
        return producent;
    }

    public void setProducent(@NonNull String producent) {
        this.producent = producent;
    }

    @NonNull
    public String getModel() {
        return model;
    }

    public void setModel(@NonNull String model) {
        this.model = model;
    }

    @NonNull
    public String getWersjaAndroida() {
        return wersjaAndroida;
    }

    public void setWersjaAndroida(@NonNull String wersjaAndroida) {
        this.wersjaAndroida = wersjaAndroida;
    }

    @NonNull
    public String getStronaWww() {
        return stronaWww;
    }

    public void setStronaWww(@NonNull String stronaWww) {
        this.stronaWww = stronaWww;
    }
}

package com.example.lab2;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

//adnotacja okreslajaca ze interfejs opisuje DAO
@Dao
public interface ElementDao {
    //adnotacja okreslajaca ze metoda wstawaia element do bazy
    //w przypadku konfliktu operacja bedzi przerwana
    @Insert(onConflict = OnConflictStrategy.ABORT)
    void insert(Element element);

    @Delete
    void delete(Element element);

    //adnotacja pozwalajaca na wykonanie dowolnego polecenia, np skasowania wszystkich elementow
    @Query("DELETE FROM nazwa_tabeli")
    void deleteAll();

    //metoda zwraca liste elementow opakowania w pojemnik live data pozwalajacy na odbieranie
    //powiadomien o zmianie danych Room wykonuje zapytanie w innym watku
    //live data powiadamia obserwatora w glownym watku aplikacji
    @Query("SELECT * FROM nazwa_tabeli ORDER BY producent ASC")
    LiveData<List<Element>> getAlphabetizedElements();

    @Update(onConflict = OnConflictStrategy.ABORT)
    void update(Element element);
}

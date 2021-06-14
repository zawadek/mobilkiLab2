package com.example.lab2;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.room.RoomDatabase;

import java.util.List;

public class ElementRepository {
    private ElementDao mElementDao;
    private LiveData<List<Element>> mAllElements;

    ElementRepository(Application application){
        ElementRoomDatabase elementRoomDatabase = ElementRoomDatabase.getDatabase(application);
        //repozytorium korzysta z obiektu DAO do odwolan do bazy
        mElementDao = elementRoomDatabase.elementDao();
        //odczytanie wszystkich elementow z DAO
        mAllElements = mElementDao.getAlphabetizedElements();
    }

    LiveData<List<Element>> getAllElements(){
        //metoda zwraca wszystkie elementy
        return mAllElements;
    }

    void deleteAll(){
        ElementRoomDatabase.databaseWriteExecutor.execute(()->{
            //skasowanie wszystkich elementow za pomoca DAO
            mElementDao.deleteAll();
        });
    }

    void insert(Element element){
        //runnable->lambda
        ElementRoomDatabase.databaseWriteExecutor.execute(()->{
            mElementDao.insert(element);
        });
    }

    void delete(Element element){
        //runnable->lambda
        ElementRoomDatabase.databaseWriteExecutor.execute(()->{
            mElementDao.delete(element);
        });
    }

    void update(Element element){
        //runnable->lambda
        ElementRoomDatabase.databaseWriteExecutor.execute(()->{
            mElementDao.update(element);
        });
    }
}

package com.example.lab2;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

//istnieja klasy bazowe ViewModel i AndroidViewModel Roznica polega na tym ze AndroidViewModel posiada
//(czasami potrzebny kontekst aplikacji) Klasa ViewModel nie posiada kontekstu Nie nalezy zapisywac
//referencji do aktywnosci (ktora tez jest kontekstem) w obiektach viewmodel poniewaz aktywnosci
//istnieja krocej niz obiekty view model (nie nalezy rowniez przechowywac referencji
//do fragmentow i innych widokow)
public class ElementViewModel extends AndroidViewModel {

    private final ElementRepository mRepository;
    private final LiveData<List<Element>> mAllElements;


    public ElementViewModel(@NonNull Application application) {
        super(application);
        mRepository = new ElementRepository(application);
        mAllElements = mRepository.getAllElements();
    }

    LiveData<List<Element>> getAllElements(){
        //zwrocenie wszystkich elementow
        return mAllElements;
    }

    public void deleteAll(){
        //skasowanie wszystkich elementow z repozytorium
        mRepository.deleteAll();
    }

    public void insert(Element element){
        mRepository.insert(element);
    }

    public void delete(Element element){
        mRepository.delete(element);
    }

    public void update(Element element){
        mRepository.update(element);
    }
}

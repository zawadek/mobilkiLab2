package com.example.lab2;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//klasa musi byc abstrakcyjna
//adnotacja okresla klasy odpowiadajace tabelom bazy (tablica entities), wersje bazy danych
//(dzieki temu klasa uruchomi odpowiednia migracje danych), exportSchema = false - eliminuje
//ostrzezenie w trakcie budowania
@Database(entities = {Element.class}, version = 2, exportSchema = false)
public abstract class ElementRoomDatabase extends RoomDatabase {
    //abstrakcyjna metoda zwracajaca DAO
    public abstract ElementDao elementDao();

    //implementacja singletona
    private static volatile ElementRoomDatabase INSTANCE;

    static ElementRoomDatabase getDatabase(final Context context){
        //tworzymy nowy obiekt tylko gdy zaden nie istnieje
        if(INSTANCE == null){
            synchronized (ElementRoomDatabase.class){
                if (INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            ElementRoomDatabase.class, "nazwa_bazy")
                            //ustawienie obiektu obslugujacego zdarzenia zwiazane z baza
                            //(kod ponizej)
                            .addCallback(sRoomDatabaseCallback)
                            //najprostsza migracja - skasowanie i utworzenie bazy od nowa
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    //usluga wykonawcza do wykonania zadan w osobnym watku
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    //obiekt obslugujacy wywolania zwrotne (call backs) zwiazane ze zdarzeniami bazy danych
    //np. onCreate(), onOpen()
    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback(){
        //uruchamianie przy tworzeniu bazy (pierwsze uruchomienie aplikacji gdy baza nie istnieje)

        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            //wykonanie operacji w osobnym watku Parametrem metody execute() jest obiekt
            //implementujacy interface Runnable moze byc zastapiony wyrazeniem lambda
            databaseWriteExecutor.execute(()->{
                ElementDao dao = INSTANCE.elementDao();
                //tworzenie elementow (obiektow klasy Element) i dodawanie ich do bazy
                //za pomoca metody insert() z obiektow dao
                Element[] elements = {new Element("Google", "Pixel 4","10","store.google.com"),
                        new Element("Google", "Pixel 5","11","www.strona.com")};
                //tutaj mozemy okreslic poczatkowa zawartosc bazy danych
                for(Element e: elements){
                    dao.insert(e);
                }
            });
        }

        /*@Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            databaseWriteExecutor.execute(() -> {
                ElementDao dao = INSTANCE.elementDao();
                //tworzenie elementow (obiektow klasy Element) i dodawanie ich do bazy
                //za pomoca metody insert() z obiektow dao
                Element[] elements = {new Element("Google", "Pixel 4","10","store.google.com"),
                        new Element("Google", "Pixel 5","11","www.strona.com")};
                //tutaj mozemy okreslic poczatkowa zawartosc bazy danych
                for (Element e : elements) {
                    dao.insert(e);
                }
            });
        }*/
    };
}

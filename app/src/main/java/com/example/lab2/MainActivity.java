package com.example.lab2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements ElementListAdapter.OnItemClickListener{

    public final static int KOD=121;
    public final static int KOD2=221;

    private ElementViewModel mElementViewModel;
    private ElementListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //ustawienie adaptera na liscie, ustawienie layoutu elementow listy
        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        mAdapter = new ElementListAdapter(this);

        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //odczytanie widoku z dostawcy
        mElementViewModel = new ViewModelProvider(this).get(ElementViewModel.class);

        //gdy zmienia sie dane w obiekcie live data w modelu widoku zostanie wywolana
        //metoda ustawiajaca zmieniona liste elementow w adapterze
        mElementViewModel.getAllElements().observe(this, elements -> {
            mAdapter.setElementList(elements);
        });
        //tworzenie obiektu itemtouchhelper
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(
                new  ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT){

                    @Override
                    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                        int adapterPosition = viewHolder.getAdapterPosition();
                        mElementViewModel.delete(mElementViewModel.getAllElements().getValue().get(adapterPosition));
                    }
                });
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.clear_data){
            Toast.makeText(this,"Usuwam dane...",Toast.LENGTH_SHORT).show();
            mElementViewModel.deleteAll();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void addNewRecord(View view) {
        Intent intent = new Intent(MainActivity.this, DodajRekord.class);
        startActivityForResult(intent,KOD);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == KOD && resultCode == RESULT_OK){
            Element element = new Element(data.getStringExtra("producent"),data.getStringExtra("model"),
                    data.getStringExtra("android"),data.getStringExtra("strona"));
            mElementViewModel.insert(element);
        }else if(requestCode == KOD2 && resultCode == RESULT_OK){
            Element element = new Element(data.getLongExtra("id",0),data.getStringExtra("producent"),
                    data.getStringExtra("model"), data.getStringExtra("android"),data.getStringExtra("strona"));
            mElementViewModel.update(element);
        }else
            Log.e("nic","Nieznany wynik");
    }

    @Override
    public void onItemClickListener(Element element) {
        Intent intent = new Intent(MainActivity.this, DodajRekord.class);
        intent.putExtra("producent",element.getProducent());
        intent.putExtra("model",element.getModel());
        intent.putExtra("android",element.getWersjaAndroida());
        intent.putExtra("strona",element.getStronaWww());
        intent.putExtra("id",element.getId());

        startActivityForResult(intent,KOD2);
    }
}
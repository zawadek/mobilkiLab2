package com.example.lab2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class DodajRekord extends AppCompatActivity {

    EditText producent, model, wersjaAndroida, strona;
    public long id = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dodaj_rekord);

        producent = (EditText)findViewById(R.id.editTextProducent);
        model = (EditText)findViewById(R.id.editTextModel);
        wersjaAndroida = (EditText)findViewById(R.id.editTextAndroid);
        strona = (EditText)findViewById(R.id.editTextStrona);
        //pobranie ewentualnych danych i wypelnienie pol tekstowych
        Bundle paczka = getIntent().getExtras();
        if(paczka != null){
            producent.setText(paczka.getString("producent"));
            model.setText(paczka.getString("model"));
            wersjaAndroida.setText(paczka.getString("android"));
            strona.setText(paczka.getString("strona"));
            id = paczka.getLong("id");
        }
    }

    public void zapiszDane(View view) {
        //sprawdzenie czy wypelniono poszczegolne pola
        if(TextUtils.isEmpty(producent.getText().toString())){
            producent.setError(getString(R.string.error));
            return;
        }
        if(TextUtils.isEmpty(model.getText().toString())){
            model.setError(getString(R.string.error));
            return;
        }
        if(TextUtils.isEmpty(wersjaAndroida.getText().toString())){
            wersjaAndroida.setError(getString(R.string.error));
            return;
        }
        if(TextUtils.isEmpty(strona.getText().toString())){
            strona.setError(getString(R.string.error));
            return;
        }
        Intent replyIntent = new Intent();
        replyIntent.putExtra("producent",producent.getText().toString());
        replyIntent.putExtra("model",model.getText().toString());
        replyIntent.putExtra("android",wersjaAndroida.getText().toString());
        replyIntent.putExtra("strona",strona.getText().toString());
        replyIntent.putExtra("id",id);
        setResult(RESULT_OK,replyIntent);
        finish();
    }

    public void anulujOperacje(View view) {
        setResult(RESULT_CANCELED);
        finish();
    }

    public void odpalStrone(View view) {
        if(!"".equals(strona.getText().toString())) {
            String adres = strona.getText().toString();
            if (!adres.startsWith("http://") && !adres.startsWith("https://")) {
                adres = "http://" + adres;
            }
            Intent przegladarka = new Intent(Intent.ACTION_VIEW, Uri.parse(adres));
            startActivity(przegladarka);
        }else{
            Toast.makeText(this,"Adres strony jest pusty",Toast.LENGTH_SHORT).show();
        }
    }
}
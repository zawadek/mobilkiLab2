package com.example.lab2;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ElementListAdapter extends RecyclerView.Adapter<ElementListAdapter.ElementViewHolder> {

    LayoutInflater mLayoutInflater;
    List<Element> mElementList;

    private OnItemClickListener mOnItemClickListener;

    //w odroznieniu od lab1c adapter nie otrzymuje listy elementow jako parametru konstruktora
    //w momencie tworzenia obiektu adaptera lista moze nie byc dostepna
    public ElementListAdapter(Context context){
        mLayoutInflater = LayoutInflater.from(context);
        this.mElementList = null;
        try {
            mOnItemClickListener = (OnItemClickListener) context;
        }
        catch (ClassCastException e){
            Log.e("exception",e.getMessage());
        }
    }

    @NonNull
    @Override
    public ElementViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = mLayoutInflater.inflate(R.layout.wiersz,null);
        ElementViewHolder elementViewHolder = new ElementViewHolder(rootView);
        return elementViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ElementViewHolder holder, int position) {
        holder.bindToHolder(position);
    }

    @Override
    public int getItemCount() {
        //w momencie tworzenia adatera lista moze nie byc dostepna
        if(mElementList != null)
            return mElementList.size();
        return 0;
    }

    //poniewaz dane wyswietlane na liscie beda sie zmienialy ta metoda umozliwia aktualizacje
    //danych w adapterze (i w konsekwencji) wyswietlanych w RecyclerView
    public void setElementList(List<Element> elementList){
        this.mElementList = elementList;
        notifyDataSetChanged();
    }

    class ElementViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView model, producent;
        public ElementViewHolder(@NonNull View itemView) {
            super(itemView);
            model = itemView.findViewById(R.id.modelTelefonu);
            producent = itemView.findViewById(R.id.producent);
            itemView.setOnClickListener(this);
        }

        public void bindToHolder(int position){
            model.setTag(position);
            model.setText(mElementList.get(position).getModel());
            producent.setTag(position);
            producent.setText(mElementList.get(position).getProducent());
        }

        @Override
        public void onClick(View v) {
            int index = (Integer) model.getTag();
            Element element = mElementList.get(index);
            mOnItemClickListener.onItemClickListener(element);
        }
    }

    interface OnItemClickListener{
        void onItemClickListener(Element element);
    }
}

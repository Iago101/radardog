package br.edu.ifsp.arq.ads.dmos5.radardog.adapter;

import android.content.Context;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import br.edu.ifsp.arq.ads.dmos5.radardog.model.Dog;

public class DogAdapter extends ArrayAdapter<Dog> {

    public DogAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }
}
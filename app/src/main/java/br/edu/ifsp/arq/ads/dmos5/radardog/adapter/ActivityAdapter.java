package br.edu.ifsp.arq.ads.dmos5.radardog.adapter;

import android.content.Context;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import br.edu.ifsp.arq.ads.dmos5.radardog.DogDataActivity;
import br.edu.ifsp.arq.ads.dmos5.radardog.model.Dog;

public class ActivityAdapter extends ArrayAdapter<Dog> {

    public ActivityAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }
}
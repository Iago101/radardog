package br.edu.ifsp.arq.ads.dmos5.radardog.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

import br.edu.ifsp.arq.ads.dmos5.radardog.MyPetsListActivity;
import br.edu.ifsp.arq.ads.dmos5.radardog.DogDataActivity;
import br.edu.ifsp.arq.ads.dmos5.radardog.R;
import br.edu.ifsp.arq.ads.dmos5.radardog.model.Dog;


public class DogAdapter extends ArrayAdapter<Dog> {

    private List<Dog> dogs;
    private Context context;

    public DogAdapter(Context context, int textViewResourceId, List<Dog> dogs) {
        super(context, textViewResourceId, dogs);
        this.dogs = dogs;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Dog dog = dogs.get(position);
        ViewHolder holder = null;
        if (convertView == null) {
            Log.d("teste", "View nova => position: " + position);
            convertView = LayoutInflater.from(context).inflate(R.layout.dog_item_layout, null);
            holder = new ViewHolder();
            holder.name = convertView.findViewById(R.id.dog_info);
            holder.breed = convertView.findViewById(R.id.dog_breed_in);
            holder.description = convertView.findViewById(R.id.dog_des_in);
            holder.btnEdit = convertView.findViewById(R.id.btn_edt);
            holder.btnEdit.setOnClickListener(view -> edit(position));
            holder.btnDelete = convertView.findViewById(R.id.btn_del);
            holder.btnDelete.setOnClickListener(view -> delete(position));
            convertView.setTag(holder);
        }else{
            Log.d("teste", "View existente => position: " + position);
            holder = (ViewHolder)convertView.getTag();
        }

        if(dog ==  null){
            return convertView;
        }

        holder.name.setText(dog.getName());
        holder.breed.setText(dog.getBreed());
        holder.description.setText(dog.getDescription());

        return convertView;
    }

    private void edit(int pos){
        Intent intent = new Intent(context, DogDataActivity.class);
        intent.putExtra("dog", dogs.get(pos));
        context.startActivity(intent);
    }

    private void delete(int pos){
        AlertDialog.Builder msgBox = new AlertDialog.Builder(context);
        msgBox.setTitle("Excluindo...");
        msgBox.setIcon(android.R.drawable.ic_menu_delete);
        msgBox.setMessage("Deseja realmente excluir este cadastro?");

        msgBox.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MyPetsListActivity myPetsListActivity = (MyPetsListActivity) context;
                myPetsListActivity.deletePet(pos);
            }
        });

        msgBox.setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {}
        });

        msgBox.show();
    }

    static class ViewHolder{
        TextView name;
        TextView breed;
        TextView description;
        Button btnEdit;
        Button btnDelete;
    }
}
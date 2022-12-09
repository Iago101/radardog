package br.edu.ifsp.arq.ads.dmos5.radardog.repository;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifsp.arq.ads.dmos5.radardog.model.Dog;

public class ActivitiesRepository {

    private FirebaseFirestore firestore;

    public ActivitiesRepository(Application application) {
        firestore = FirebaseFirestore.getInstance();
    }

    public void insert(Dog dog){
        firestore.collection("dog").add(dog)
                .addOnSuccessListener(unused ->{
                    Log.d(this.toString(), "Cadastrado com sucesso.");
                });
    }

    public void update(Dog dog){
        firestore.collection("dog").document(dog.getId())
                .set(dog).addOnSuccessListener(unused -> {
                    Log.d(this.toString(), "Atualizado com sucesso.");
                });
    }

    public void delete(Dog dog){
        firestore.collection("dog").document(dog.getId())
                .delete().addOnSuccessListener(unused -> {
                    Log.d(this.toString(), "Removido com sucesso.");
                });
    }

    public LiveData<List<Dog>> getAllDogs(String dogId) {
        MutableLiveData<List<Dog>> liveData = new MutableLiveData<>();
        List<Dog> dogs = new ArrayList<>();
        // broken dogid, tem q trazer todos
        firestore.collection("dogs").whereEqualTo("dogID", dogId).orderBy("date", Query.Direction.DESCENDING).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot doc: task.getResult()){
                        Dog dog = doc.toObject(Dog.class);
                        dog.setId(doc.getId());
                        dogs.add(dog);
                    }
                }
                liveData.setValue(dogs);
            }
        });

        return liveData;
    }
}
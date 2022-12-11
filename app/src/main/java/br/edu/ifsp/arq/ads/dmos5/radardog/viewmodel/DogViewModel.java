package br.edu.ifsp.arq.ads.dmos5.radardog.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.edu.ifsp.arq.ads.dmos5.radardog.model.Dog;
import br.edu.ifsp.arq.ads.dmos5.radardog.repository.DogsRepository;

public class DogViewModel extends AndroidViewModel {

    private DogsRepository dogsRepository;

    public DogViewModel(@NonNull Application application) {
        super(application);
        dogsRepository = new DogsRepository(application);
    }

    public void createPet(Dog dog){
        dogsRepository.insert(dog);
    }

    public void updateDog(Dog dog){
        dogsRepository.update(dog);
    }

    public void deleteDog(Dog dog){
        dogsRepository.delete(dog);
    }

    public LiveData<List<Dog>> allDogs(String userId){
        return dogsRepository.getAllDogs(userId);
    }


}
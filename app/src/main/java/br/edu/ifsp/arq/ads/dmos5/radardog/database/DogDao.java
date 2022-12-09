package br.edu.ifsp.arq.ads.dmos5.radardog.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import br.edu.ifsp.arq.ads.dmos5.radardog.model.Dog;

@Dao
public interface DogDao {

    @Transaction
    @Query("SELECT * FROM dog WHERE id = :dogId")
    LiveData<Dog> loadDog(String dogId);

    @Insert
    void insert(Dog dog);

    @Update
    void update(Dog dog);
}

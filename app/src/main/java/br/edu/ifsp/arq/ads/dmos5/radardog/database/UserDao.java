package br.edu.ifsp.arq.ads.dmos5.radardog.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import br.edu.ifsp.arq.ads.dmos5.radardog.model.User;

@Dao
public interface UserDao {

    @Query("SELECT * FROM user WHERE email = :email AND password = :password")
    User login(String email, String password);

    @Transaction
    @Query("SELECT * FROM user WHERE id = :userId")
    LiveData<User> loadUser(String userId);

    @Insert
    void insert(User user);

    @Update
    void update(User user);
}

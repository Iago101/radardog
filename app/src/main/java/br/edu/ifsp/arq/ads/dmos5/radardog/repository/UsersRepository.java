package br.edu.ifsp.arq.ads.dmos5.radardog.repository;

import android.app.Application;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.preference.PreferenceManager;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import br.edu.ifsp.arq.ads.dmos5.radardog.database.AppDatabase;
import br.edu.ifsp.arq.ads.dmos5.radardog.database.UserDao;
import br.edu.ifsp.arq.ads.dmos5.radardog.model.User;
import br.edu.ifsp.arq.ads.dmos5.radardog.viewmodel.UserViewModel;

public class UsersRepository {

    private static final String BASE_URL = "https://identitytoolkit.googleapis.com/v1/";
    private static final String SIGNUP = "accounts:signUp"; // cadastre-se
    private static final String SIGNIN = "accounts:signInWithPassword"; // entrar
    private static final String PASSWORD_RESET = "accounts:sendOobCode"; // redefinição de senha
    private static final String KEY = "?key=AIzaSyDubsBgCG5GR5qHmOQnLs-fHZDF0fHSXBY";

    private FirebaseFirestore firestore;

    private RequestQueue queue;

    private SharedPreferences preferences;

    public UsersRepository(Application application){
        firestore = FirebaseFirestore.getInstance();
        queue = Volley.newRequestQueue(application);
        preferences = PreferenceManager.getDefaultSharedPreferences(application);
    }

    public void createUser(User user){
        JSONObject params = new JSONObject();
        try {
            params.put("email", user.getEmail());
            params.put("password", user.getPassword());
            params.put("returnSecureToken", true);
            params.put("Content-Type", "application/json; charset=utf-8");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // enviar a requisição
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                BASE_URL + SIGNUP + KEY,
                params,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            user.setId(response.getString("localId"));
                            user.setPassword(response.getString("idToken"));

                            firestore.collection("user").document(user.getId())
                                    .set(user)
                                    .addOnSuccessListener(unused -> {
                                        Log.d(this.toString(), "Usuário " +
                                                user.getEmail() + " cadastrado com sucesso.");

                                    });
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        NetworkResponse response = error.networkResponse;
                        if (error instanceof ServerError && response != null) {
                            try {
                                String res = new String(response.data,
                                        HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                                JSONObject obj = new JSONObject(res);
                                Log.d(this.toString(), obj.toString());
                            } catch ( UnsupportedEncodingException e1) {
                                e1.printStackTrace();
                            } catch (JSONException e2) {
                                e2.printStackTrace();
                            }
                        }
                    }
                }
        );
        queue.add(request);
    }

    public LiveData<User> login(String email, String password){
        MutableLiveData<User> liveData = new MutableLiveData<>();

        JSONObject params = new JSONObject();
        try {
            params.put("email", email);
            params.put("password", password);
            params.put("returnSecureToken", true);
            params.put("Content-Type", "application/json; charset=utf-8");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                BASE_URL + SIGNIN + KEY,
                params,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String localId = response.getString("localId");
                            String idToken = response.getString("idToken");

                            firestore.collection("user").document(localId)
                                    .get()
                                    .addOnSuccessListener(snapshot -> {
                                        User user = snapshot.toObject(User.class);
                                        user.setId(localId);
                                        user.setPassword(idToken);
                                        liveData.setValue(user);
                                        preferences.edit().putString(UserViewModel.USER_ID, localId)
                                                .apply();
                                    });

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        NetworkResponse response = error.networkResponse;
                        if (error instanceof ServerError && response != null) {
                            try {
                                String res = new String(response.data,
                                        HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                                JSONObject obj = new JSONObject(res);
                                Log.d(this.toString(), obj.toString());
                            } catch ( UnsupportedEncodingException e1) {
                                e1.printStackTrace();
                            } catch (JSONException e2) {
                                e2.printStackTrace();
                            }
                        }
                        liveData.setValue(null);
                    }
                }
        );
        queue.add(request);
        return liveData;
    }

    public LiveData<User> loadUser(String userId){
        MutableLiveData<User> liveData = new MutableLiveData<>();

        DocumentReference userRef = firestore.collection("user").document(userId);
        userRef.get().addOnSuccessListener(snapshot -> {
            User user = snapshot.toObject(User.class);
            user.setId(user.getId());
            liveData.setValue(user);
        });
        return liveData;
    }

    public void update(User user){
        DocumentReference userRef = firestore.collection("user")
                .document(user.getId());
        userRef.set(user).addOnSuccessListener(unused -> {
            Log.d(this.toString(), "Usuário atualizado com sucesso");
        });
    }

    public void resetPassword(String email){
        JSONObject parametros = new JSONObject();
        try {
            parametros.put("email", email);
            parametros.put("requestType", "PASSWORD_RESET");
            parametros.put("Content-Type", "application/json; charset=utf-8");
        } catch (JSONException e) {
            e.printStackTrace();
        }


        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, BASE_URL + PASSWORD_RESET + KEY, parametros,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(this.toString(), response.keys().toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        NetworkResponse response = error.networkResponse;
                        if (error instanceof ServerError && response != null) {
                            try {
                                String res = new String(response.data,
                                        HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                                JSONObject obj = new JSONObject(res);
                                Log.d(this.toString(), obj.toString());
                            } catch ( UnsupportedEncodingException e1) {
                                e1.printStackTrace();
                            } catch (JSONException e2) {
                                e2.printStackTrace();
                            }
                        }
                    }
                });

        queue.add(request);
    }
}
package com.afi.latihan.manualparsingjson;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {
    // Url Endpoint for get data JSON
    public static String BASE_URL = "https://reqres.in/api/users?page=1";

    // get data array from model
    private ArrayList<User> list = new ArrayList<>();

    // Variable
    RecyclerView rvUser;
    ProgressBar pbLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Lookup the recyclerview in activity layout
        rvUser = findViewById(R.id.rv_user);
        // if true, to optimize size of recyclerview
        rvUser.setHasFixedSize(true);

        pbLoading = findViewById(R.id.pb_loading);

        // get data json from AsyncHttpClint
        getUser();
    }

    // Manual Parsing json
    private void parseJson(String response){
        try {
            // untuk masuk ke JSONObject dengan tanda {} (kurung kurawal)
            JSONObject jsonObject  = new JSONObject(response);
            // untuk masuk ke JSONArray dengan tanda [] (kurung siku) dengan key "data" (key nya tergantung dengan json yang kalian pakai)
            JSONArray dataArray = jsonObject.getJSONArray("data");
            // lalu melakukan looping(pengulangan) dari data array tersebut dari awal sampai akhir data
            for (int i = 0; i < dataArray.length(); i++){
                // JSON object looping
                JSONObject dataObject = dataArray.getJSONObject(i);
                // get data with key harus sama persis dengan json
                int id = dataObject.getInt("id");
                String firstName = dataObject.getString("first_name");
                String email = dataObject.getString("email");
                String avatar = dataObject.getString("avatar");

                // set data to model
                User user = new User();
                user.setId(id);
                user.setFirstName(firstName);
                user.setEmail(email);
                user.setAvatar(avatar);

                // initialize User
                list.add(user);
            }
            // get LayoutManager
            showRecyclerList();
        }catch (JSONException e){
            Log.e("json", "unexpected JSON exception", e);
        }
    }

    // LoopJ
    // Asyncronus melakukannya dibalik layar(background) kalo sudah mendapatkan nya baru ditampilkan.
    private void getUser(){
        pbLoading.setVisibility(View.VISIBLE);
        // AsyncHttpClient, berarti kita akan menggunakan client yang bertanggung jawab untuk koneksi data dan sifatnya adalah asynchronous.
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(BASE_URL, new AsyncHttpResponseHandler() {
            // Callback onSuccess() dipanggil ketika response memberikan kode status 200, yang artinya koneksi berhasil.
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                // Jika koneksi berhasil
                pbLoading.setVisibility(View.INVISIBLE);
                String response = new String(responseBody);
                parseJson(response);
            }

            // Callback onFailure() dipanggil ketika response memberikan kode status 4xx (seperti 401, 403, 404, dll), yang artinya koneksi gagal.
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                // Jika koneksi gagal
                pbLoading.setVisibility(View.INVISIBLE);
                Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        });
    }

    public void showRecyclerList(){
        // Set layout manager to position the items
        rvUser.setLayoutManager(new LinearLayoutManager(this));
        // Create adapter passing in the sample user data
        ListUserAdapter listUserAdapter = new ListUserAdapter(list);
        // Attach the adapter to the recyclerview to populate items
        rvUser.setAdapter(listUserAdapter);
        pbLoading.setVisibility(View.GONE);
    }

}

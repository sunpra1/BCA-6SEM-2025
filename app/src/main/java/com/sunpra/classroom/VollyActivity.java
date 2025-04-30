package com.sunpra.classroom;

import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingDeque;

public class VollyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_volly);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        receivingContentFromServer();
    }

    private void receivingContentFromServer() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = "https://jsonplaceholder.typicode.com/posts";

        StringRequest request = new StringRequest(
                url,
                response -> {
                    Log.d("SUCCESS RESPONSE", response);
                    try {
                        ArrayList<Post> arrayList = new ArrayList<>();
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            Post post = getPostFromJsonObject(jsonObject);
                            if(post != null){
                                arrayList.add(post);
                            }
                        }
                        // TODO show on list View
                        // Follow previous class codelab to display in ListView
                    } catch (JSONException e) {

                    }
                },
                error -> {
                    Log.e("FAILED RESPONSE", error.networkResponse.toString() );
                }
        );

        requestQueue.add(request);
    }

    private Post getPostFromJsonObject(JSONObject jsonObject){
        try {
            int userId = jsonObject.getInt("userId");
            int id = jsonObject.getInt("id");
            String title = jsonObject.getString("title");
            String body = jsonObject.getString("body");
            return new Post(
                    userId,
                    id,
                    title,
                    body
            );
        }catch (Exception e){
            return null;
        }
    }

}

class Post {
    private int userId;
    private int id;
    private String title;
    private String body;


    public Post(int userId, int id, String title, String body) {
        this.userId = userId;
        this.id = id;
        this.title = title;
        this.body = body;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
package com.practice.testapiclient;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FirstURLActivity extends AppCompatActivity {
  RecyclerView recyclerView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_first_url);
    recyclerView = findViewById(R.id.recyclerView);
    recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    getDataFromFirstURL();
  }

  void getDataFromFirstURL() {
    JsonArrayRequest request = new JsonArrayRequest(Constants.firstURL, new Response.Listener<JSONArray>() {
      @Override
      public void onResponse(JSONArray data) {
        try {
          List<ModelFirstURL> models = new ArrayList<>();
          for (int i = 0; i < data.length(); i++) {
            JSONObject object = data.getJSONObject(i);
            ModelFirstURL model = new ModelFirstURL();
            model.setId(object.getInt("id"));
            model.setUserId(object.getInt("userId"));
            model.setTitle(object.getString("title"));
            model.setBody(object.getString("body"));
            models.add(model);
          }

          FirstURLAdapter adapter = new FirstURLAdapter(models);
          recyclerView.setAdapter(adapter);

        } catch (
          JSONException e) {
          e.printStackTrace();
          Toast.makeText(FirstURLActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
        }
      }
    }, new Response.ErrorListener() {
      @Override
      public void onErrorResponse(VolleyError error) {

      }
    });
    AppController.getInstance().getRequestQueue().getCache().clear();
    AppController.getInstance().addToRequestQueue(request);
  }


  class ModelFirstURL {
    private int userId;
    private int id;
    private String title;
    private String body;

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

  class FirstURLAdapter extends RecyclerView.Adapter<FirstURLAdapter.ViewHolder> {

    List<ModelFirstURL> models;

    public FirstURLAdapter(List<ModelFirstURL> models) {
      this.models = models;
    }

    @NonNull
    @Override
    public FirstURLAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_first_url_list_item, parent, false);
      FirstURLAdapter.ViewHolder viewHolder = new FirstURLAdapter.ViewHolder(v);
      return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FirstURLAdapter.ViewHolder holder, int position) {
      holder.tvID.setText("ID : " + models.get(position).getId());
      holder.tvUserID.setText("USER ID : " + models.get(position).getUserId());
      holder.tvTitle.setText(models.get(position).getTitle());
      holder.tvBody.setText(models.get(position).getBody());
    }

    @Override
    public int getItemCount() {
      return models.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
      TextView tvID, tvUserID, tvTitle, tvBody;

      public ViewHolder(@NonNull View itemView) {
        super(itemView);
        tvID = itemView.findViewById(R.id.tvID);
        tvUserID = itemView.findViewById(R.id.tvUserID);
        tvTitle = itemView.findViewById(R.id.tvTitle);
        tvBody = itemView.findViewById(R.id.tvBody);
      }
    }
  }
}

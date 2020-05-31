package com.example.thousand_it_company.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Service;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.thousand_it_company.model.Movie;
import com.example.thousand_it_company.R;
import com.example.thousand_it_company.data.MovieAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class MainActivity extends AppCompatActivity{

    RecyclerView recyclerView;
    private MovieAdapter movieAdapter;
    private ArrayList<Movie> movies;
    private RequestQueue requestQueue;
    SwipeRefreshLayout swipeRefreshLayout;
    ImageView fav;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT>=19){
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(movieAdapter);

        movies = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(this);

        getMovies();
        swipeRefreshLayout = findViewById(R.id.swipe);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                movieAdapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
                shuffleItems();
            }
        });
    }


    public void shuffleItems() {
        Collections.shuffle(movies, new Random(System.currentTimeMillis()));
        MovieAdapter customAdapter = new MovieAdapter(MainActivity.this, movies);
        recyclerView.setAdapter(customAdapter);
    }

    private void getMovies() {
        String url="https://api.themoviedb.org/3/movie/popular?api_key=93008dcf46b83ee86df7dc148ace7c70&page=";
        JsonObjectRequest request;
        for (int i=1; i<80; i++){
            String pageStr = Integer.toString(i);
            request = new JsonObjectRequest(Request.Method.GET, url+pageStr, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        JSONArray jsonArray = response.getJSONArray("results");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String title = jsonObject.getString("title");
                            String date = jsonObject.getString("release_date");
                            String posterUrl = jsonObject.getString("poster_path");
                            String score = jsonObject.getString("vote_average");
                            String overview = jsonObject.getString("overview");
                            String popularity = jsonObject.getString("popularity");
                            String vote = jsonObject.getString("vote_count");
                            String language = jsonObject.getString("original_language");
                            String original = jsonObject.getString("original_title");
                            String backUrl = jsonObject.getString("backdrop_path");

                            Movie movie = new Movie();
                            movie.setTitle(title);
                            movie.setDate(date);
                            movie.setPosterUrl(posterUrl);
                            movie.setScore(score);
                            movie.setOverview(overview);
                            movie.setPopularity(popularity);
                            movie.setVote(vote);
                            movie.setLanguage(language);
                            movie.setOriginal(original);
                            movie.setBackUrl(backUrl);

                            movies.add(movie);
                        }

                        movieAdapter = new MovieAdapter(MainActivity.this, movies);
                        recyclerView.setAdapter(movieAdapter);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                }
            }
        );
        requestQueue.add(request);
    }

}}
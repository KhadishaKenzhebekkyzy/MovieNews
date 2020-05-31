package com.example.thousand_it_company.data;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thousand_it_company.activities.MainActivity;
import com.example.thousand_it_company.activities.MovieInfo;
import com.example.thousand_it_company.model.Movie;
import com.example.thousand_it_company.R;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private Context context;
    private ArrayList<Movie> movies;
    private boolean isFav = false;

    public MovieAdapter(Context context, ArrayList<Movie> movies){
        this.context = context;
        this.movies = movies;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.movie_item, viewGroup, false);
        return new MovieViewHolder(view);
    }

    public void onBindViewHolder(@NonNull MovieViewHolder movieViewHolder, int i) {
        Movie currentMovie = movies.get(i);
        String title = currentMovie.getTitle();
        String date = currentMovie.getDate();
        String posterUrl = currentMovie.getPosterUrl();
        String score = currentMovie.getScore();
        movieViewHolder.titleTextView.setText(title);
        movieViewHolder.dateTextView.setText(date);
        movieViewHolder.scoreTextView.setText(score);
        Picasso.get().load(posterUrl).fit().centerInside().into(movieViewHolder.posterImageView);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder{

        ImageView posterImageView;
        TextView titleTextView;
        TextView dateTextView;
        TextView scoreTextView;
        ImageView favoriteImageView;


        public MovieViewHolder(@NonNull final View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String title = titleTextView.getText().toString();
                    String date = dateTextView.getText().toString();
                    String score = scoreTextView.getText().toString();
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION){
                        Intent intent;
                        intent = new Intent(view.getContext(), MovieInfo.class);
                        intent.putExtra("title", title);
                        intent.putExtra("date", date);
                        intent.putExtra("score", score);
                        intent.putExtra("overview", movies.get(pos).getOverview());
                        intent.putExtra("popularity", movies.get(pos).getPopularity());
                        intent.putExtra("vote", movies.get(pos).getVote());
                        intent.putExtra("language", movies.get(pos).getLanguage());
                        intent.putExtra("original", movies.get(pos).getOriginal());

                        Bitmap bitmap = loadBitmapFromUrl(movies.get(pos).getPosterUrl());
                        posterImageView.setImageBitmap(bitmap);
                        BitmapHelper.getInstance().setBitmap(bitmap);

                        intent.putExtra("back", movies.get(pos).getBackUrl());

                        view.getContext().startActivity(intent);
                    }
                }
            });

            posterImageView = itemView.findViewById(R.id.posterImageView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            dateTextView = itemView.findViewById(R.id.dateTextView);
            scoreTextView = itemView.findViewById(R.id.scoreTextView);
            favoriteImageView = itemView.findViewById(R.id.fav);
            final String imageName = String.valueOf(favoriteImageView.getTag());
            favoriteImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!isFav) {
                        favoriteImageView.setImageResource(R.drawable.fav);
                        isFav = true;
                        Toast.makeText(itemView.getContext(), "Added to 'Favorite'", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        favoriteImageView.setImageResource(R.drawable.not_fav);
                        isFav = false;
                        Toast.makeText(itemView.getContext(), "Removed from 'Favorite'", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        private Bitmap loadBitmapFromUrl(String posterUrl) {
            try {
                URL url = new URL(posterUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream inputStream = connection.getInputStream();
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                return bitmap;
            }catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

    }
}

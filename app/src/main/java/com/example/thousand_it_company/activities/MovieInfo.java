package com.example.thousand_it_company.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.thousand_it_company.R;
import com.example.thousand_it_company.data.BitmapHelper;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.Objects;

public class MovieInfo extends AppCompatActivity implements Serializable {
    TextView titleTextView;
    TextView dateTextView;
    ImageView posterImageView;
    TextView scoreTextView;
    TextView overviewTextView;
    TextView popularityTextView;
    TextView voteTextView;
    TextView languageTextView;
    TextView originalTextView;
    ImageView backImageView;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_info);


        Intent i = getIntent();
        String title = i.getStringExtra("title");
        String date = i.getStringExtra("date");
        String score = i.getStringExtra("score");
        String overview = i.getStringExtra("overview");
        String popularity = i.getStringExtra("popularity");
        String vote = i.getStringExtra("vote");
        String language = i.getStringExtra("language");
        String original = i.getStringExtra("original");
        String backUrl = i.getStringExtra("back");

        titleTextView = findViewById(R.id.title);
        dateTextView = findViewById(R.id.date);
        scoreTextView = findViewById(R.id.score);
        posterImageView = findViewById(R.id.poster);
        overviewTextView = findViewById(R.id.overviewTextView);
        popularityTextView = findViewById(R.id.popularityTextView);
        voteTextView = findViewById(R.id.voteTextView);
        languageTextView = findViewById(R.id.languageTextView);
        originalTextView = findViewById(R.id.originalTextView);
        backImageView = findViewById(R.id.backImageView);

        if(backUrl != null && !backUrl.isEmpty()){
            Picasso.get().load(backUrl).fit().centerInside().into(backImageView);
        }

        titleTextView.setText(title);
        dateTextView.setText(date);
        scoreTextView.setText(score);
        overviewTextView.setText(overview);
        popularityTextView.setText(popularity);
        voteTextView.setText(vote);
        languageTextView.setText(language);
        originalTextView.setText(original);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        posterImageView.setImageBitmap(BitmapHelper.getInstance().getBitmap());
        backImageView.setImageBitmap(BitmapHelper.getInstance().getBitmap());
    }

}



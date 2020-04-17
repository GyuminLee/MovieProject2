package com.gyumin.udacitymovieappaac.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.gyumin.udacitymovieappaac.R;
import com.gyumin.udacitymovieappaac.adapter.ReviewAdapter;
import com.gyumin.udacitymovieappaac.adapter.VideoAdapter;
import com.gyumin.udacitymovieappaac.data.FavoriteMovie;
import com.gyumin.udacitymovieappaac.data.FavoriteMovieDatabase;
import com.gyumin.udacitymovieappaac.data.Movie;
import com.gyumin.udacitymovieappaac.data.Review;
import com.gyumin.udacitymovieappaac.data.ReviewResponse;
import com.gyumin.udacitymovieappaac.data.Video;
import com.gyumin.udacitymovieappaac.data.VideoResponse;
import com.gyumin.udacitymovieappaac.utils.Constants;
import com.gyumin.udacitymovieappaac.viewmodel.DetailViewModel;
import com.squareup.picasso.Picasso;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class DetailActivity extends AppCompatActivity implements VideoAdapter.VideoClickListener{

    private static final String TAG = DetailActivity.class.getSimpleName();

    TextView titleTv;
    ImageView thumbIv;
    TextView releaseDateTv;
    TextView rateTv;
    TextView overviewTv;
    CheckBox favoriteCb;

    private int movieId;
    private FavoriteMovieDatabase favoriteMovieDatabase;

    private DetailViewModel detailViewModel;
    private Movie movieData = new Movie();

    private ReviewAdapter reviewAdapter;
    private VideoAdapter videoAdapter;

    private RecyclerView reviewRecyclerView;
    private RecyclerView videoRecyclerView;

    private ArrayList<Review> reviewArrayList = new ArrayList<Review>();
    private ArrayList<Video> videoArrayList = new ArrayList<Video>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        movieId = getIntent().getIntExtra(Constants.MOVIE_ID, 419704);
        setUpView();
        favoriteMovieDatabase = FavoriteMovieDatabase.getInstance(getApplicationContext());
        setupViewModel();
        setMovieData();
        setupRecyclerView();

    }

    private void setupViewModel() {
        detailViewModel = new ViewModelProvider(this).get(DetailViewModel.class);
        detailViewModel.getReviews(movieId);
        detailViewModel.getReviewRepository().observe(this, new Observer<ReviewResponse>() {
            @Override
            public void onChanged(ReviewResponse reviewResponse) {
                ArrayList<Review> reviewList = reviewResponse.getResults();
                reviewArrayList.addAll(reviewList);
                reviewRecyclerView.setAdapter(reviewAdapter);
            }
        });
        detailViewModel.getVideos(movieId);
        detailViewModel.getVideoRepository().observe(this, new Observer<VideoResponse>() {
            @Override
            public void onChanged(VideoResponse videoResponse) {
                ArrayList<Video> videoList = videoResponse.getResults();
                videoArrayList.addAll(videoList);
                videoRecyclerView.setAdapter(videoAdapter);
            }
        });

    }

    private void setupRecyclerView() {
      reviewRecyclerView.setLayoutManager(new LinearLayoutManager(this));
      reviewAdapter = new ReviewAdapter(this, reviewArrayList);
      reviewRecyclerView.setAdapter(reviewAdapter);

      videoRecyclerView.setLayoutManager(new LinearLayoutManager(this));
      videoAdapter = new VideoAdapter(this, videoArrayList);
      videoAdapter.setVideoClickListener(this);
      videoRecyclerView.setAdapter(videoAdapter);
    }

    private void setUpView() {
        titleTv = findViewById(R.id.tv_title_detail);
        thumbIv = findViewById(R.id.iv_thumb_detail);
        releaseDateTv = findViewById(R.id.tv_release_date_detail);
        rateTv = findViewById(R.id.tv_rate_detail);
        overviewTv = findViewById(R.id.tv_overview);
        favoriteCb = findViewById(R.id.cb_favorite);

        reviewRecyclerView = findViewById(R.id.rv_review);
        videoRecyclerView = findViewById(R.id.rv_video);
    }

    private void setMovieData() {
        detailViewModel.getDetail(movieId);
        detailViewModel.getMovieDetailRepository().observe(this, new Observer<Movie>() {
            @Override
            public void onChanged(Movie movie) {
                titleTv.setText(movie.getTitle());
                Picasso.get().load(Constants.IMAGE_BASE_URL + movie.getImage())
                        .placeholder(R.drawable.placeholder)
                        .error(R.drawable.error)
                        .into(thumbIv);
                releaseDateTv.setText(movie.getReleaseDate());
                String rating = movie.getVoteAverage() + "/10";
                rateTv.setText(rating);
                overviewTv.setText(movie.getOverview());
                movieData = movie;
            }
        });
        detailViewModel.getFavoriteMovieById(movieId, favoriteMovieDatabase.favoriteMovieDao());
        detailViewModel.getFavoriteRepository().observe(this, new Observer<FavoriteMovie>() {
            @Override
            public void onChanged(FavoriteMovie favoriteMovie) {
                if(favoriteMovie == null) {
                    favoriteCb.setChecked(false);
                } else {
                    favoriteCb.setChecked(true);
                }
            }
        });
        favoriteCb.setOnClickListener(new CheckBox.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("isClicked" + favoriteCb.isChecked());

                if(!favoriteCb.isChecked()) {
                    //if it is favorite, need to delete from database.
                    Executor myExecutor = Executors.newSingleThreadExecutor();

                    myExecutor.execute(new Runnable() {
                        @Override
                        public void run() {
                            favoriteMovieDatabase.favoriteMovieDao().deleteMovie(new FavoriteMovie(movieData.getId(), movieData.getTitle(), movieData.getImage()));
                        }
                    });
                } else {
                    Executor myExecutor = Executors.newSingleThreadExecutor();

                    //if it is not favorite, need to add to database
                    myExecutor.execute(new Runnable() {
                        @Override
                        public void run() {
                            if(movieData.getTitle() != null) {
                                favoriteMovieDatabase.favoriteMovieDao().insertMovie(new FavoriteMovie(movieData.getId(), movieData.getTitle(), movieData.getImage()));
                            }

                        }
                    });
                }
            }
        });
    }


    @Override
    public void onVideoClick(View view, int position) {
        String videoId = videoArrayList.get(position).getId();
        sendIntentToYouTube(this, videoId);
    }

    public void sendIntentToYouTube(Context context, String id) {
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(Constants.YOUTUBE_BASE_URI + id));
        Intent webIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse(Constants.YOUTUBE_BASE_URL + id));
        try {
            context.startActivity(appIntent);
        } catch (ActivityNotFoundException ex) {
            System.out.println(ex.toString());
            context.startActivity(webIntent);
        }
    }
}

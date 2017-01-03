package com.moviebasket.android.client.movie_detail;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import com.moviebasket.android.client.R;
import com.moviebasket.android.client.search.MovieDetail;

public class MovieDetailDialog extends Dialog {

    TextView storybord;
    MovieDetail detail;

    public MovieDetailDialog(Context context) {
        super(context);
    }

    public MovieDetailDialog(Context context, MovieDetail detail) {
        super(context);
        this.detail = detail;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_movie_detail);
        storybord= (TextView)findViewById(R.id.storybord);
        storybord.setMovementMethod(ScrollingMovementMethod.getInstance());
    }

    /*
    *
    String title;//
    String link;//
    String image;
    String pubDate;
    String director;
    String actor;
    String userRating;
    String summary;
*/
}

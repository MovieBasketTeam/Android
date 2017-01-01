package com.moviebasket.android.client.movie_detail;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import com.moviebasket.android.client.R;

public class MovieDetailDialog extends Dialog {

    public MovieDetailDialog(Context context) {
        super(context);
    }

    TextView storybord = (TextView)findViewById(R.id.storybord);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_movie_detail);
        storybord.setMovementMethod(ScrollingMovementMethod.getInstance());
    }
}

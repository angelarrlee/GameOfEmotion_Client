/*
 * Author:       Qian Tang & Ching Man Lee
 * Application : Game of Emotion
 *
 * */

package com.example.tangq.bodymapsofemotions;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;
import android.view.View;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class YouTubeActivity extends YouTubeBaseActivity/* implements YouTubePlayer.OnInitializedListener */{

    // YouTube player view
    private YouTubePlayerView youTubeView;

    private final static String API_KEY = "AIzaSyDpUKX2o0g1lFbMg1AvhhxpipoFMholmUg";
    private final static String CIRCUMPLEX_VIDEO = "TNdEb8VeBF4";
    private final static String BODYMAP_VIDEO = "obYJRmgrqOU";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        VariablePool.setContext(YouTubeActivity.this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube);

        //find views by ID
        youTubeView = (YouTubePlayerView) findViewById(R.id.youtube_view);

        // Initializing video player with developer key
        youTubeView.initialize(API_KEY, onInitialization());


    }

    public void onRating(View view){
        EmotionRating.showSeekBarDialog(VariablePool.getContext(), getWindow().getDecorView());
    }

    private YouTubePlayer.OnInitializedListener onInitialization() {
        return new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean wasRestored) {
                if (!wasRestored) {

                    System.out.println(VariablePool.getModel_num());
                    youTubePlayer.setFullscreen(true);

                    //auto play video
                    if(VariablePool.getModel_num() == 0){
                        youTubePlayer.loadVideo(CIRCUMPLEX_VIDEO);
                    }else{
                        youTubePlayer.loadVideo(BODYMAP_VIDEO);
                    }

                    // Showing player controls with default mode
                    youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT);
                }

            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                Toast.makeText(getApplicationContext(),
                        "onInitializationFailure()",
                        Toast.LENGTH_SHORT).show();
            }
        };
    }

    //when user press the back button, pop-up window appears
    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage("Do you want to Exit?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if user pressed "yes", then he is allowed to exit from application
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if user select "No", just cancel this dialog and continue with app
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

}

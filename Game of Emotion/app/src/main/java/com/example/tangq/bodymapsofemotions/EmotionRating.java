/*
 * Author:       Qian Tang & Ching Man Lee
 * Application : Game of Emotion
 *
 * */

package com.example.tangq.bodymapsofemotions;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.xw.repo.BubbleSeekBar;

public class EmotionRating {

    //display a seek bar dialog to ask for user's emotion rating
    public static void showSeekBarDialog(final Context context, View view){

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.custom_seekbar, (ViewGroup)view.findViewById(R.id.seekBarRoot));

        AlertDialog.Builder builder = new AlertDialog.Builder(VariablePool.getContext());

        final BubbleSeekBar bubbleSeekBar = (BubbleSeekBar)layout.findViewById(R.id.seekBar);
        final ImageView happinessImageView = (ImageView)layout.findViewById(R.id.happinessImageView);
        happinessImageView.setImageResource(R.drawable.happiness_1_trans);
        Button ratingButton = (Button)layout.findViewById(R.id.ratingButton);
        builder.setView(layout);

        final AlertDialog dialog = builder.create();
        bubbleSeekBar.setOnProgressChangedListener(new BubbleSeekBar.OnProgressChangedListener() {
               @Override
               public void onProgressChanged(int progress, float progressFloat) {
                   VariablePool.setEmotionRating(progress);
                   if(progress >= 0 && progress < 20){
                       happinessImageView.setImageResource(R.drawable.happiness_1_trans);
                   }else if(progress < 40){
                       happinessImageView.setImageResource(R.drawable.happiness_2_trans);
                   }else if(progress < 60){
                       happinessImageView.setImageResource(R.drawable.happiness_3_trans);
                   }else if(progress < 80){
                       happinessImageView.setImageResource(R.drawable.happiness_4_trans);
                   }else{
                       happinessImageView.setImageResource(R.drawable.happiness_5_trans);
                   }
               }

               @Override
               public void getProgressOnActionUp(int progress, float progressFloat) {

               }

               @Override
               public void getProgressOnFinally(int progress, float progressFloat) {

               }
           }

        );

        ratingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();

                AsyncTaskCallWS.param_EmotionRating();
                emotionRatingWS task_emotionRating = new emotionRatingWS();
                task_emotionRating.execute();

            }
        });

        dialog.setCanceledOnTouchOutside(false);
        dialog.show();


    }


    //call AsyncTask to request for the web service
    public static class emotionRatingWS extends AsyncTaskCallWS{

        protected void onPreExecute(){
            super.onPreExecute();
        }

        protected void onPostExecute(Void result) {

            if(VariablePool.getErrNum() == 0){
                Toast.makeText(VariablePool.getContext(), "Submitted Successfully.", Toast.LENGTH_LONG).show();

            }else{
                ErrorHandling.dialog_error(VariablePool.getContext(), VariablePool.getErrNum(), VariablePool.getErrMsg());
            }

        }
    }


}

/*
* Author:       Qian Tang & Ching Man Lee
* Application : Game of Emotion
*
* */

package com.example.tangq.bodymapsofemotions;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class AffectiveCircumplexModel extends AppCompatActivity {
    AffectiveCircumplexView affectiveCircumplexView;
    TextView tvTouchToMove;
    Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        VariablePool.setContext(AffectiveCircumplexModel.this);
        EmotionRating.showSeekBarDialog(VariablePool.getContext(), getWindow().getDecorView());
        setContentView(R.layout.activity_affective_circumplex_model);
        init();
    }

    private void init(){
        affectiveCircumplexView = (AffectiveCircumplexView) findViewById(R.id.affectiveView);
        tvTouchToMove = (TextView) findViewById(R.id.tvTouchToMove);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);
    }


    //when user click the submit button
    public void submitClick(View view) {
        CircumplexData circumplexData = affectiveCircumplexView.getCirumplexData();//get the data

        //check if the user has move the spot
        if(circumplexData.getCurrentX()==0.0 && circumplexData.getCurrentY() == 0.0){

            Toast.makeText(getApplicationContext(), "Please choose a position before submitting. ", Toast.LENGTH_LONG).show();

        }
        else{

            JSONObject circumplexDataJson = new JSONObject();
            try {
                circumplexDataJson.put("posX", circumplexData.getCurrentX());
                circumplexDataJson.put("posY", circumplexData.getCurrentY());
                circumplexDataJson.put("radius", circumplexData.getCircleRadius());

                VariablePool.setCircumplexData(circumplexDataJson);

                System.out.println(circumplexDataJson.toString());
                AsyncTaskCallWS.param_Submit_Circumplex_Data();
                circumplexWS task_Circumplex = new circumplexWS();
                task_Circumplex.execute();

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

    }

    public class circumplexWS extends AsyncTaskCallWS{

        protected void onPreExecute(){
            super.onPreExecute();
        }

        protected void onPostExecute(Void result) {

            //if no error
            if(VariablePool.getErrNum() == 0){
                System.out.println(VariablePool.getResponse().toString());
                Toast.makeText(getApplicationContext(), "Submitted successfully.", Toast.LENGTH_LONG).show();

                //To display video ...
                Intent i = new Intent(getApplicationContext(), YouTubeActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);



            }else{
                ErrorHandling.dialog_error(AffectiveCircumplexModel.this, VariablePool.getErrNum(), VariablePool.getErrMsg());
            }

        }
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

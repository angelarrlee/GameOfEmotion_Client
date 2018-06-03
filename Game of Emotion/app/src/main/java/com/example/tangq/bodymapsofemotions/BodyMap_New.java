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
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class BodyMap_New extends AppCompatActivity implements View.OnClickListener,View.OnTouchListener,View.OnLongClickListener{
    private Button btnSharp,btnPulsing,btnTight,btnWarm,btnChill;
    private Button btnPlus,btnMinus,btnReset,btnSubmit,btnHeavy,btnSink;
    private ViewGroup _root,body_layout;
    private int _xDelta;
    private int _yDelta;
    private Button _copy;
    private int count_copy = 0;
    private ArrayList<Button> copy_btn_list;
    private ImageView body_front;
    private TextView tv_size;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        VariablePool.setContext(BodyMap_New.this);
        EmotionRating.showSeekBarDialog(VariablePool.getContext(), getWindow().getDecorView());
        setContentView(R.layout.activity_body_map__new);
        init();
    }


    private void init(){
        btnMinus = (Button) findViewById(R.id.btnMinus);
        btnPlus = (Button) findViewById(R.id.btnPlus);
        btnReset = (Button)findViewById(R.id.btnReset);
        btnSubmit = (Button)findViewById(R.id.btnSubmit);

        btnSharp = (Button)findViewById(R.id.btnSharp);
        btnPulsing = (Button) findViewById(R.id.btnPulsing);
        btnTight = (Button) findViewById(R.id.btnTight);
        btnWarm = (Button) findViewById(R.id.btnWarm);
        btnChill = (Button) findViewById(R.id.btnChill);
        btnHeavy = (Button) findViewById(R.id.btnHeavy);
        btnSink = (Button) findViewById(R.id.btnSink);

        _root = (RelativeLayout) findViewById(R.id.root);
        body_layout = (LinearLayout) findViewById(R.id.body_layout);

        body_front = (ImageView) findViewById(R.id.body_front);

        tv_size = (TextView) findViewById(R.id.tv_size);
        setOrigSizeText();

        btnMinus.setOnClickListener(this);
        btnPlus.setOnClickListener(this);
        btnReset.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
        btnHeavy.setOnClickListener(this);
        btnSink.setOnClickListener(this);

        btnChill.setOnClickListener(this);
        btnWarm.setOnClickListener(this);
        btnTight.setOnClickListener(this);
        btnSharp.setOnClickListener(this);
        btnHeavy.setOnClickListener(this);
        btnSink.setOnClickListener(this);

        btnSharp.setOnLongClickListener(this);
        btnPulsing.setOnLongClickListener(this);
        btnTight.setOnLongClickListener(this);
        btnWarm.setOnLongClickListener(this);
        btnChill.setOnLongClickListener(this);
        btnHeavy.setOnLongClickListener(this);
        btnSink.setOnLongClickListener(this);
        copy_btn_list = new ArrayList<>();

    }

    @Override
    public void onClick(View view) {

        switch(view.getId()){
            case R.id.btnPlus:
                zoomIcon(view);
                break;
            case R.id.btnMinus:
                shrinkIcon(view);
                break;
            case R.id.btnReset:
                reset();
                break;
            case R.id.btnChill:
            case R.id.btnSharp:
            case R.id.btnWarm:
            case R.id.btnTight:
            case R.id.btnHeavy:
            case R.id.btnSink:
                Toast.makeText(getApplicationContext(), "Long click to get icon",Toast.LENGTH_SHORT).show();
                break;
            case R.id.btnSubmit:
                ArrayList<IconData> bodymapData = getBodyMapData();

                if(bodymapData.size() == 0){// Means user doesn't draw anything
                    ErrorHandling.dialog_error(VariablePool.getContext(), 3, "Please draw something before submission. ");
                    break;
                }

                IconData iconData;

                JSONObject bodyMapDataJson = new JSONObject();
                JSONObject iconDataJson;
                try {

                    for(int i = 0; i<bodymapData.size(); i++){
                        iconData = bodymapData.get(i);

                        //testing.....
                        System.out.println("icon name : " + iconData.getIconName());
                        System.out.println("X : " + iconData.getX());
                        System.out.println("Y : " + iconData.getY());


                        iconDataJson = new JSONObject();
                        iconDataJson.put("icon_type", iconData.getIconName());
                        iconDataJson.put("posX", iconData.getX());
                        iconDataJson.put("posY", iconData.getY());

                        bodyMapDataJson.put(String.valueOf(i+1), iconDataJson);

                    }

                    VariablePool.setBodyMapData(bodyMapDataJson);

                    System.out.println(bodyMapDataJson.toString());
                    AsyncTaskCallWS.param_Submit_BodyMap_Data();
                    bodyMapWS task_BodyMap = new bodyMapWS();
                    task_BodyMap.execute();

                } catch (JSONException e) {
                    e.printStackTrace();
                }



                break;
            default:
                break;

        }

    }

    private void setSizeText(){
        if(_copy!=null){
            RelativeLayout.LayoutParams textSizePara;
            textSizePara = (RelativeLayout.LayoutParams) _copy.getLayoutParams();
            tv_size.setText(textSizePara.width+","+textSizePara.height);
        }
        else{
            setOrigSizeText();
        }
    }
    private void setOrigSizeText(){
        LinearLayout.LayoutParams origTextSizePara;
        origTextSizePara = (LinearLayout.LayoutParams) btnSharp.getLayoutParams();
        tv_size.setText(""+ origTextSizePara.width+","+origTextSizePara.height);

    }

    private ArrayList<IconData> getBodyMapData(){
        ArrayList<IconData> bodymapDatas = new ArrayList<IconData>();
        for(int i = 0;i<copy_btn_list.size();i++){
            IconData iconData = new IconData(copy_btn_list.get(i));
            iconData.setIconName((String) copy_btn_list.get(i).getTag());
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) copy_btn_list.get(i).getLayoutParams();

            //Calculate Y position based on the linear layout which contais the body image
            iconData.setY(params.topMargin - body_layout.getTop());
            iconData.setX(params.leftMargin);

            // if the icon is put front
            iconData.setPutFront(putFront(iconData.getX()));
            iconData.setPutBack(!(putFront(iconData.getX())));
            Log.i("submit","width and height" + iconData.getView().getX() + ", " + iconData.getView().getY());
            bodymapDatas.add(iconData);
        }
        return bodymapDatas;
    }

    private boolean putFront(int x){
        int divide_x = body_front.getLeft()+body_front.getWidth();
        if(x>divide_x){
            return false;
        }
        else{
            return true;
        }
    }
    private void reset(){
        Log.i("arraylist","length of arrraylist of button " + copy_btn_list.size());
        for(int i=0;i <copy_btn_list.size();i++){
            _root.removeView(copy_btn_list.get(i));
        }
        count_copy = 0;
        copy_btn_list.clear();
        Toast.makeText(this, "Reset successfully",Toast.LENGTH_LONG).show();
    }


    private void zoomIcon(View view) {
        if (copy_btn_list.size()!=0) {
            RelativeLayout.LayoutParams p = (RelativeLayout.LayoutParams) _copy.getLayoutParams();
            int i = 30;
            int new_h = i + p.height;
            int new_w = i + p.width;
            p.height = new_h;
            p.width = new_w;
            _copy.setLayoutParams(p);
            setSizeText();
        }
        else{
            Toast.makeText(this, "make an icon to zoom",Toast.LENGTH_SHORT).show();
        }
    }

    private void shrinkIcon(View view){
        if(copy_btn_list.size()!=0){
            RelativeLayout.LayoutParams p = (RelativeLayout.LayoutParams) _copy.getLayoutParams();
            int i = 30;
            int new_h = p.height-i;
            int new_w = p.width-i;

            if(new_h > 0 && new_w > 0){
                p.height = new_h;
                p.width = new_w;
                _copy.setLayoutParams(p);
            }

            setSizeText();
        }
        else{
            Toast.makeText(this, "make an icon to shrink",Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public boolean onTouch(View view, MotionEvent event) {
        final int X = (int) event.getRawX();
        final int Y = (int) event.getRawY();
        Log.i("touch start","X, Y " + X +", "+Y);
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                _copy = (Button) view;
                Log.i("touch down","change copy button to _copy，tag is "+view.getTag());
                RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
                _xDelta = X - lParams.leftMargin;
                _yDelta = Y - lParams.topMargin;
                Log.i("touch down","_xDelta, _yDelta " + _xDelta +", "+_yDelta+
                        "\n leftmar right mar" + lParams.leftMargin+","+lParams.topMargin);
                break;
            case MotionEvent.ACTION_UP:
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                break;
            case MotionEvent.ACTION_POINTER_UP:
                break;
            case MotionEvent.ACTION_MOVE:
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
                layoutParams.leftMargin = X - _xDelta;
                layoutParams.topMargin = Y - _yDelta;
                layoutParams.rightMargin = -550;
                layoutParams.bottomMargin = -550;
                view.setLayoutParams(layoutParams);
                Log.i("touch move",
                        "leftmar top right bottom" + layoutParams.leftMargin+","+
                                layoutParams.topMargin+","+ layoutParams.rightMargin+","+layoutParams.bottomMargin);
                break;
        }
        _root.invalidate();
        return true;
    }

    @Override
    public boolean onLongClick(View view) {
        count_copy +=1;
        if(count_copy<11){
            if(view.getId()==R.id.btnSharp){
                int resid = R.drawable.sharp;
                copy_btn(btnSharp,resid);
            }
            if(view.getId()==R.id.btnPulsing){
                int resid = R.drawable.bulb;
                copy_btn(btnPulsing,resid);
            }
            if(view.getId()==R.id.btnTight){
                int resid = R.drawable.knot;
                copy_btn(btnTight,resid);
            }
            if(view.getId()==R.id.btnWarm){
                int resid = R.drawable.heart;
                copy_btn(btnWarm,resid);
            }
            if(view.getId()==R.id.btnChill){
                int resid = R.drawable.snowflake;
                copy_btn(btnChill,resid);
            }
            if(view.getId()==R.id.btnHeavy){
                int resid = R.drawable.weight;
                copy_btn(btnChill,resid);
            }
            if(view.getId()==R.id.btnSink){
                int resid = R.drawable.anchor;
                copy_btn(btnChill,resid);
            }
            else{
                //Toast.makeText(this, "At most 10 icons allowed",Toast.LENGTH_LONG).show();
            }
        }
        return false;
    }

    private void copy_btn(Button btn,int resid){
        Button copy = new Button(this);
        copy.setBackgroundResource(resid);
        copy.setId(Integer.valueOf(count_copy));
        copy.setTag(btn.getTag());
        LinearLayout.LayoutParams btnMoveParams = (LinearLayout.LayoutParams) btn.getLayoutParams();
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(btnMoveParams.width, btnMoveParams.height);
        layoutParams.leftMargin = 200;
        layoutParams.topMargin = 200;
        layoutParams.bottomMargin = -250;
        layoutParams.rightMargin = -250;
        copy.setLayoutParams(layoutParams);
        copy.setOnTouchListener(this);
        _root.addView(copy);
        copy_btn_list.add(copy);
        _copy = copy;
        setOrigSizeText();
    }

    public class bodyMapWS extends AsyncTaskCallWS{

        protected void onPreExecute(){
            super.onPreExecute();
        }

        protected void onPostExecute(Void result) {

            if(VariablePool.getErrNum() == 0){
                System.out.println(VariablePool.getResponse().toString());
                Toast.makeText(getApplicationContext(), "Submitted successfully.", Toast.LENGTH_LONG).show();

                //To display video ...
                Intent i = new Intent(getApplicationContext(), YouTubeActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);

            }else{
                ErrorHandling.dialog_error(BodyMap_New.this, VariablePool.getErrNum(), VariablePool.getErrMsg());
            }

        }
    }

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

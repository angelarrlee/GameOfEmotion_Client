/*
 * Author:       Qian Tang & Ching Man Lee
 * Application : Game of Emotion
 *
 * */

package com.example.tangq.bodymapsofemotions;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class Register extends AppCompatActivity {

    private EditText etUsername,etPassword;
    private String name = null;
    private String pwd = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        VariablePool.setContext(Register.this);
        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);

    }

    //when user click the register button
    public void onReg(View view){
        //check if user has input value for username and password
        if(!etUsername.getText().toString().isEmpty() && !etPassword.getText().toString().isEmpty()){
            name = etUsername.getText().toString();
            pwd = etPassword.getText().toString();
            VariablePool.setUsername(name);
            VariablePool.setPassword(pwd);

            AsyncTaskCallWS.param_Register();
            Register.registerWS task_register = new Register.registerWS();
            task_register.execute();
        }else{
            ErrorHandling.dialog_error(Register.this, 10, "Please input username and password. ");
        }
    }

    //when user click the button "Go to Login page"
    public void onGotoLogin(View view){
        Intent i = new Intent(getApplicationContext(), Login.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }

    //call AsyncTask to request web service
    public class registerWS extends AsyncTaskCallWS{

        protected void onPreExecute(){
            super.onPreExecute();
        }

        protected void onPostExecute(Void result) {

            //check if the call is requested successfully
            if(VariablePool.getErrNum() == 0){
                Toast.makeText(getApplicationContext(), VariablePool.getResponse().optString(VariablePool.TAG_MESSAGE) + " : " + VariablePool.getUsername(), Toast.LENGTH_LONG).show();
                Intent i = new Intent(getApplicationContext(), Login.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
            }else{
                ErrorHandling.dialog_error(Register.this, VariablePool.getErrNum(), VariablePool.getErrMsg());
                etUsername.setText("");
                etPassword.setText("");
            }

        }
    }

}

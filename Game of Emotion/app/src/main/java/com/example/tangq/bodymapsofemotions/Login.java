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

public class Login extends AppCompatActivity {

    private EditText etUsername, etPassword;
    private String name = null;
    private String pwd = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        VariablePool.setContext(Login.this);
        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);
    }


    //when user click the login button
    public void onLogin(View view){
        if(!etUsername.getText().toString().isEmpty() && !etPassword.getText().toString().isEmpty()){
            name = etUsername.getText().toString();
            pwd = etPassword.getText().toString();
            VariablePool.setUsername(name);
            VariablePool.setPassword(pwd);

            AsyncTaskCallWS.param_Login();
            loginWS task_Login = new loginWS();
            task_Login.execute();
        }else{
            ErrorHandling.dialog_error(Login.this, 10, "Please input username and password. ");
        }
    }

    //when user click the button to go to the register page
    public void openReg(View view){
        Intent i = new Intent(getApplicationContext(), Register.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }

    //call the asyncTask to call the web service
    public class loginWS extends AsyncTaskCallWS{

        protected void onPreExecute(){
            super.onPreExecute();
        }

        protected void onPostExecute(Void result) {

            //check if the call is requested successfully
            if(VariablePool.getErrNum() == 0){
                Toast.makeText(getApplicationContext(), "Hi. "+VariablePool.getUsername(), Toast.LENGTH_LONG).show();

                //open and go to new activity
                Intent i;

                switch(VariablePool.getModel_num()){
                    case 0:
                        i = new Intent(getApplicationContext(), AffectiveCircumplexModel.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);
                        break;
                    case 1:
                        i = new Intent(getApplicationContext(), BodyMap_New.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);
                        break;
                    default:
                        break;
                }
            }else{
                ErrorHandling.dialog_error(Login.this, VariablePool.getErrNum(), VariablePool.getErrMsg());
                etUsername.setText("");
                etPassword.setText("");
            }

        }
    }

}//end of main activity

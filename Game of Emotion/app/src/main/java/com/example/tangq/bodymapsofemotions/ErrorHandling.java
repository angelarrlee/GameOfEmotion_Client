/*
 * Author:       Qian Tang & Ching Man Lee
 * Application : Game of Emotion
 *
 * */

package com.example.tangq.bodymapsofemotions;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

public class ErrorHandling {

    private static String errTitle = null;
    private static String errMessage = null;

    //display alert dialog or toast make for errors with different error number
    public static void dialog_error(final Context context, int errNum, String errMsg){

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        switch(errNum){
            case 1: //registration error
                errTitle = "Registration Error";
                errMessage = errMsg;
                builder.setMessage(errMessage).setTitle(errTitle);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int id) {
                        dialogInterface.dismiss();
                        VariablePool.setResponse(null);
                        VariablePool.setUsername(null);
                    }
                });
                builder.show();
                break;
            case 2: //login error
                errTitle = "Login Error";
                errMessage = errMsg;
                builder.setMessage(errMessage).setTitle(errTitle);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int id) {
                        dialogInterface.dismiss();
                        VariablePool.setResponse(null);
                        VariablePool.setUsername(null);
                    }
                });
                builder.show();
                break;
            case 3://user doesn't draw anything
                errTitle = "Submission Fail";
                errMessage = errMsg;
                builder.setMessage(errMessage).setTitle(errTitle);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int id) {
                        dialogInterface.dismiss();
                    }
                });
                builder.show();
                break;
            case 4:

                break;
            case 9:
            case 10:
                Toast.makeText(context, errMsg, Toast.LENGTH_LONG).show();
                break;
            case 11: //when there is connection error
                errTitle = "Connection Fail";
                errMessage = errMsg;
                builder.setMessage(errMessage).setTitle(errTitle);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int id) {
                        dialogInterface.dismiss();
                    }
                });
                builder.show();
                break;
            default:
                break;
        }

    }

}

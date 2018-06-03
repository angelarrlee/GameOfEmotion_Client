/*
 * Author:       Qian Tang & Ching Man Lee
 * Application : Game of Emotion
 *
 * */

package com.example.tangq.bodymapsofemotions;

import android.os.AsyncTask;

import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;

import javax.net.ssl.HttpsURLConnection;

public class AsyncTaskCallWS extends AsyncTask<Void, Void, Void> {

    private final static String BASE_URL = "http://192.168.1.6:8081/BodyMapOfEmotion/";

    //private final static String BASE_URL = "http://10.12.9.247:8081/BodyMapOfEmotion/";//把它改成http://14.201.251.208:8081/BodyMapOfEmotion/；
    //private static boolean connected = false;
    //private static HTTPURLConnection service;
    private static JSONObject postDataParams;

    protected void onPreExecute(){

    }

    protected Void doInBackground(Void... arg0){

        if(!HTTPURLConnection.checkNetworkExist(VariablePool.getContext())){
            VariablePool.setErrNum(9);
            VariablePool.setErrMsg("Please check your network connection. [CodeIdx:901]");
        }else if(!HTTPURLConnection.ServerData(VariablePool.getUrl(), VariablePool.getParams())){
            VariablePool.setErrNum(11);
            VariablePool.setErrMsg("Cannot request service! Please try again. [CodeIdx:1101]");

        }

        return null;
    }

    protected void onPostExecute(Void result){

    }

    public static void param_Login(){

        try {
            VariablePool.setUrl(BASE_URL + "login");
            System.out.println(VariablePool.getUrl());
            postDataParams = new JSONObject();
            postDataParams.put("user_name", VariablePool.getUsername());
            postDataParams.put("password", VariablePool.getPassword());
            VariablePool.setParams(postDataParams);

        }catch (Exception e){
            ErrorHandling.dialog_error(VariablePool.getContext(), 10, "System error: "+ e.getMessage() + " [CodeIdx:1001]");
        }

    }

    public  static void param_Register(){

        try {
            VariablePool.setUrl(BASE_URL + "register");
            postDataParams = new JSONObject();
            postDataParams.put("user_name", VariablePool.getUsername());
            postDataParams.put("password", VariablePool.getPassword());
            VariablePool.setParams(postDataParams);

        }catch(Exception e){
            ErrorHandling.dialog_error(VariablePool.getContext(), 10, "System error: "+ e.getMessage() + " [CodeIdx:1002]");
        }

    }

    public  static void param_EmotionRating(){

        try {
            VariablePool.setUrl(BASE_URL + "emotionRating");
            postDataParams = new JSONObject();
            postDataParams.put("user_name", VariablePool.getUsername());
            postDataParams.put("rating", VariablePool.getEmotionRating());
            VariablePool.setParams(postDataParams);

        }catch(Exception e){
            ErrorHandling.dialog_error(VariablePool.getContext(), 10, "System error: "+ e.getMessage() + " [CodeIdx:1003]");
        }

    }

    public  static void param_Submit_Circumplex_Data(){


        try {
            VariablePool.setUrl(BASE_URL + "affectiveCircumplex");
            postDataParams = new JSONObject();
            postDataParams.put("user_name", VariablePool.getUsername());
            postDataParams.put("circumplex_data", VariablePool.getCircumplex_data());
            VariablePool.setParams(postDataParams);

            System.out.println(VariablePool.getParams().toString());
        }catch(Exception e){
            ErrorHandling.dialog_error(VariablePool.getContext(), 10, "System error: "+ e.getMessage() + " [CodeIdx:1004]");
        }

    }


    public  static void param_Submit_BodyMap_Data(){

        try {
            VariablePool.setUrl(BASE_URL + "bodyMap");
            postDataParams = new JSONObject();
            postDataParams.put("user_name", VariablePool.getUsername());
            postDataParams.put("bodyMap_data", VariablePool.getBodyMapData());
            VariablePool.setParams(postDataParams);

            System.out.println(VariablePool.getParams().toString());
        }catch(Exception e){
            ErrorHandling.dialog_error(VariablePool.getContext(), 10, "System error: "+ e.getMessage() + " [CodeIdx:1005]");
        }

    }

}

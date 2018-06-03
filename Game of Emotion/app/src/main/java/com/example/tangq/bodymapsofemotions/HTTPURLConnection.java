/*
 * Author:       Qian Tang & Ching Man Lee
 * Application : Game of Emotion
 *
 * */

package com.example.tangq.bodymapsofemotions;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HTTPURLConnection {
    private static JSONObject response;
    private static String respStr = null;
    private static URL url;

    //try to establish httpUrl connection and send the request to the web service
    //return true if connected with success
    public static boolean ServerData(String path,JSONObject param) {
        try {
            url = new URL(path);
            respStr = null;
            String paramStr = param.toString();
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setUseCaches(false);
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            connection.setRequestProperty("Charset", "UTF-8");
            connection.setRequestProperty("Content-Length", String.valueOf(paramStr.length()));
            connection.setRequestProperty("Content-Type", "application/json");
            connection.connect();

            DataOutputStream out = new DataOutputStream(connection.getOutputStream());
            // write the request params
            out.writeBytes(paramStr);
            out.flush();
            out.close();

            // read the response
            InputStream in = connection.getInputStream();
            respStr = getResponse(in);

            //check if the return is in valid Json object format
            if(!respStr.isEmpty() && JsonHandling.isJSONValid(respStr)){
                System.out.println(respStr);
                response = new JSONObject(respStr);
                VariablePool.setErrNum(response.getInt(VariablePool.TAG_ERRNUM));
                VariablePool.setErrMsg(response.optString(VariablePool.TAG_ERRMSG));
                if(response.has(VariablePool.TAG_MSGBODY)){
                    VariablePool.setResponse(response.optJSONObject(VariablePool.TAG_MSGBODY));
                }
                if(VariablePool.getResponse().has(VariablePool.TAG_MODEL_NUM)){
                    VariablePool.setModel_num(VariablePool.getResponse().optInt(VariablePool.TAG_MODEL_NUM));
                }
                System.out.println(VariablePool.getErrNum());
                System.out.println(VariablePool.getErrMsg());
                System.out.println(VariablePool.getResponse());
                System.out.println(VariablePool.getModel_num());
            }
            connection.disconnect();
            return true;

        } catch (Exception e) {
            return false;
        }
    }

    //check if there exists network
    public static boolean checkNetworkExist(Context context){

        ConnectivityManager connectivity = (ConnectivityManager)context.getSystemService(context.CONNECTIVITY_SERVICE);
        if(connectivity == null){
            return false;
        }else{
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if(info != null){
                for(int i = 0; i < info.length; i++){
                    if(info[i].getState() == NetworkInfo.State.CONNECTED){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    //function to get the string response from inputstream
    private static String getResponse(InputStream in) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        StringBuilder builder = new StringBuilder();
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return builder.toString();
    }
}
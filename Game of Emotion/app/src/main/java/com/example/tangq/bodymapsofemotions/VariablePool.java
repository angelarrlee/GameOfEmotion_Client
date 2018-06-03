/*
 * Author:       Qian Tang & Ching Man Lee
 * Application : Game of Emotion
 *
 * */

package com.example.tangq.bodymapsofemotions;

import android.content.Context;

import org.json.JSONObject;

public class VariablePool {

    // Google Console APIs developer key
    public static final String DEVELOPER_KEY = "AIzaSyDpUKX2o0g1lFbMg1AvhhxpipoFMholmUg";

    public static final String TAG_MODEL_NUM = "MODEL_NUM";
    public static final String TAG_ERRNUM = "ERRNUM";
    public static final String TAG_ERRMSG = "ERRMSG";
    public static final String TAG_MSGBODY = "MSGBODY";
    public static final String TAG_MESSAGE = "MESSAGE";

    private static String username = null;
    private static String password = null;
    private static int model_num = -1;
    private static String url = null;
    private static Context context;
    private static JSONObject params = new JSONObject();
    private static JSONObject response = new JSONObject();
    private static String errMsg = null;
    private static int errNum = 0;
    private static int emotionRating = 0;

    private static JSONObject circumplex_data = new JSONObject();
    private static JSONObject body_map_data = new JSONObject();

    public static void setUsername(String username){
        VariablePool.username = username;
    }

    public static String getUsername(){
        return username;
    }

    public static void setPassword(String password){
        VariablePool.password = password;
    }

    public static String getPassword(){
        return password;
    }

    public static void setModel_num(int model_num){
        VariablePool.model_num = model_num;
    }

    public static int getModel_num(){
        return model_num;
    }

    public static void setUrl(String url){
        VariablePool.url = url;
    }

    public static String getUrl(){
        return url;
    }

    public static void setContext(Context context){
        VariablePool.context = context;
    }

    public static Context getContext(){
        return context;
    }

    public static void setParams(JSONObject params){
        VariablePool.params = params;
    }

    public static JSONObject getParams(){
        return params;
    }

    public static void setResponse(JSONObject response){
        VariablePool.response = response;
    }

    public static JSONObject getResponse(){
        return response;
    }

    public static void setErrMsg(String errMsg) {
        VariablePool.errMsg = errMsg;
    }

    public static String getErrMsg() {
        return errMsg;
    }

    public static void setErrNum(int errNum){
        VariablePool.errNum = errNum;
    }

    public static int getErrNum() {
        return errNum;
    }

    public static void setEmotionRating(int emotionRating){
        VariablePool.emotionRating = emotionRating;
    }

    public static int getEmotionRating() {
        return emotionRating;
    }


    public static void setCircumplexData(JSONObject circumple_data){
        VariablePool.circumplex_data = circumple_data;
    }

    public static JSONObject getCircumplex_data(){
        return circumplex_data;
    }

    public static void setBodyMapData(JSONObject body_map_data){
        VariablePool.body_map_data = body_map_data;
    }

    public static JSONObject getBodyMapData(){
        return body_map_data;
    }
}

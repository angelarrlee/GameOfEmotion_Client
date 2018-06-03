/*
 * Author:       Qian Tang & Ching Man Lee
 * Application : Game of Emotion
 *
 * */

package com.example.tangq.bodymapsofemotions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonHandling {

    //check if the String is in Json object format
    public static boolean isJSONValid(String test){
        try{
            new JSONObject(test);
        }
        catch(JSONException ex){
            try{
                new JSONArray(test);
            }
            catch(JSONException e){
                return false;
            }
        }
        return true;
    }
}

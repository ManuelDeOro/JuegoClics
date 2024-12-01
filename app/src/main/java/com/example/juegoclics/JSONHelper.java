package com.example.juegoclics;

import android.content.Context;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class JSONHelper {

    private static final String FILE_NAME = "ranking.json";

    public static boolean saveScore(Context context, String username, int level) {
        try {
            JSONArray usersArray = loadRanking(context);
            JSONObject user = findUser(usersArray, username);
            if (user == null) {
                user = new JSONObject();
                user.put("username", username);
                user.put("maxLevel", level);
                usersArray.put(user);
            } else {
                int currentMaxLevel = user.getInt("maxLevel");
                if (level > currentMaxLevel) {
                    user.put("maxLevel", level);
                }
            }

            FileOutputStream fos = context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
            fos.write(usersArray.toString().getBytes());
            fos.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static JSONArray loadRanking(Context context) {
        try {
            String json = new String(context.openFileInput(FILE_NAME).readAllBytes());
            return new JSONArray(json);
        } catch (IOException | JSONException e) {
            return new JSONArray();
        }
    }

    private static JSONObject findUser(JSONArray users, String username) throws JSONException {
        for (int i = 0; i < users.length(); i++) {
            JSONObject user = users.getJSONObject(i);
            if (user.getString("username").equals(username)) {
                return user;
            }
        }
        return null;
    }
}

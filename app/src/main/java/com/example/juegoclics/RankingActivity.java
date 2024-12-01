package com.example.juegoclics;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class RankingActivity extends AppCompatActivity {

    private ListView rankingListView;
    private Button buttonBackToMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        rankingListView = findViewById(R.id.rankingListView);
        buttonBackToMenu = findViewById(R.id.buttonBackToMenu);

        buttonBackToMenu.setOnClickListener(v -> {
            Intent intent = new Intent(RankingActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        loadRanking();
    }

    private void loadRanking() {
        ArrayList<String> rankingList = new ArrayList<>();
        JSONArray users = JSONHelper.loadRanking(this);
        try {
            for (int i = 0; i < users.length(); i++) {
                JSONObject user = users.getJSONObject(i);
                String username = user.getString("username");
                int maxLevel = user.getInt("maxLevel");
                rankingList.add(username + " - Nivel máximo: " + maxLevel);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, rankingList);
        rankingListView.setAdapter(adapter);
    }
}


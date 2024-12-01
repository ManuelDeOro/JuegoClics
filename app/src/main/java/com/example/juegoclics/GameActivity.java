package com.example.juegoclics;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class GameActivity extends AppCompatActivity {

    private TextView scoreText, timerText, levelText;
    private Button tapButton, buttonStartGame, buttonBackToMenu;
    private int score = 0;
    private int level = 1;
    private CountDownTimer gameTimer;
    private long remainingTime = 30;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        username = getIntent().getStringExtra("username");

        scoreText = findViewById(R.id.scoreText);
        timerText = findViewById(R.id.timerText);
        levelText = findViewById(R.id.levelText);
        tapButton = findViewById(R.id.tapButton);
        buttonStartGame = findViewById(R.id.buttonStartGame);
        buttonBackToMenu = findViewById(R.id.buttonBackToMenu);

        buttonBackToMenu.setOnClickListener(v -> {
            Intent intent = new Intent(GameActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        buttonStartGame.setOnClickListener(v -> startGame());

        tapButton.setOnClickListener(v -> {
            score++;
            scoreText.setText("Clics: " + score);

            // Subir de nivel al llegar a 50 clics
            if (score % 100 == 0) {
                level++;
                remainingTime += 15; // Aumentar el tiempo en 15 segundos por cada nivel alcanzado
                levelText.setText("Nivel: " + level);
                Toast.makeText(this, "¡Nivel " + level + " alcanzado!", Toast.LENGTH_SHORT).show();

                // Reiniciar el temporizador con el nuevo tiempo
                resetTimer();
            }
        });
    }

    private void startGame() {
        score = 0;
        level = 1;
        remainingTime = 30;

        scoreText.setText("Clics: 0");
        timerText.setText("Tiempo: 30s");
        levelText.setText("Nivel: 1");

        tapButton.setVisibility(View.VISIBLE);
        buttonStartGame.setVisibility(View.GONE);

        // Iniciar el temporizador
        gameTimer = new CountDownTimer(remainingTime * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                remainingTime = millisUntilFinished / 1000;
                timerText.setText("Tiempo: " + remainingTime + "s");
            }

            @Override
            public void onFinish() {
                timerText.setText("Tiempo: 0s");
                tapButton.setVisibility(View.GONE);
                buttonStartGame.setVisibility(View.VISIBLE);
                saveScore();
            }
        };

        gameTimer.start();
    }

    // Método para reiniciar el temporizador con el tiempo actualizado
    private void resetTimer() {
        if (gameTimer != null) {
            gameTimer.cancel(); // Cancelar el temporizador actual
        }

        // Reiniciar el temporizador con el nuevo tiempo restante
        gameTimer = new CountDownTimer(remainingTime * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                remainingTime = millisUntilFinished / 1000;
                timerText.setText("Tiempo: " + remainingTime + "s");
            }

            @Override
            public void onFinish() {
                timerText.setText("Tiempo: 0s");
                tapButton.setVisibility(View.GONE);
                buttonStartGame.setVisibility(View.VISIBLE);
                saveScore();
            }
        };

        gameTimer.start(); // Iniciar el nuevo temporizador
    }

    private void saveScore() {
        boolean saved = JSONHelper.saveScore(this, username, level);
        if (saved) {
            Toast.makeText(this, "Puntuación guardada", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Error al guardar puntuación", Toast.LENGTH_SHORT).show();
        }
    }
}




package com.example.juegoclics;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ContactActivity extends AppCompatActivity {

    private EditText editTextName, editTextMessage;
    private Button buttonSend, buttonBackToMenu;
    private TextView contactText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        // Inicializar vistas
        editTextName = findViewById(R.id.editTextName);
        editTextMessage = findViewById(R.id.editTextMessage);
        buttonSend = findViewById(R.id.buttonSend);
        buttonBackToMenu = findViewById(R.id.buttonBackToMenu);
        contactText = findViewById(R.id.contactText);

        // Mostrar la información del contacto
        contactText.setText("Desarrollador: Manudl De Oro López\nEmail: manuel.deorolopez24@campusfp.es\nTel: +34 123 456 789");

        // Configurar el botón de enviar
        buttonSend.setOnClickListener(v -> {
            String name = editTextName.getText().toString();
            String message = editTextMessage.getText().toString();

            if (name.isEmpty() || message.isEmpty()) {
                Toast.makeText(ContactActivity.this, "Por favor, ingresa tu nombre y mensaje.", Toast.LENGTH_SHORT).show();
            } else {
                // Aquí se puede implementar la lógica para enviar el mensaje, como enviarlo por email o guardar en la base de datos
                // Por ejemplo, simula el envío:
                Toast.makeText(ContactActivity.this, "Mensaje enviado correctamente.", Toast.LENGTH_SHORT).show();
                clearFields();
            }
        });

        // Configurar el botón de volver al menú
        buttonBackToMenu.setOnClickListener(v -> {
            Intent intent = new Intent(ContactActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }

    // Limpiar los campos después de enviar el mensaje
    private void clearFields() {
        editTextName.setText("");
        editTextMessage.setText("");
    }
}


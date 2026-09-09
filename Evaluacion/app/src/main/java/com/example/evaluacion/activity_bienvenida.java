package com.example.evaluacion;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class activity_bienvenida extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_bienvenida);

        // Solución al error del ID "main" usando android.R.id.content
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button btnIrATareas = findViewById(R.id.btnIrATareas);

        // Solución a la advertencia reemplazando por una función Lambda
        btnIrATareas.setOnClickListener(v -> {
            Intent intent = new Intent(activity_bienvenida.this, SegundaActivity.class);
            if (getIntent().getExtras() != null) {
                intent.putExtras(getIntent().getExtras());
            }
            startActivity(intent);
            finish();
        });
    }
}
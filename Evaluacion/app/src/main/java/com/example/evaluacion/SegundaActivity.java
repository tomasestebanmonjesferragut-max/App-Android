package com.example.evaluacion;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

public class SegundaActivity extends AppCompatActivity {

    // Declaración de variables
    private TextView tvBienvenida;
    private Spinner spCategoria;
    private CheckBox cbUrgente;
    private RadioGroup rgEstado;
    private RatingBar rbDificultad;
    private ProgressBar pbProgreso;
    private Button btnGuardarTarea;
    private RecyclerView rvTareas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_segunda);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.tvBienvenida), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // 1. Enlazar variables con XML (findViewById)
        tvBienvenida = findViewById(R.id.tvBienvenida);
        spCategoria = findViewById(R.id.spCategoria);
        cbUrgente = findViewById(R.id.cbUrgente);
        rgEstado = findViewById(R.id.rgEstado);
        rbDificultad = findViewById(R.id.rbDificultad);
        pbProgreso = findViewById(R.id.pbProgreso);
        btnGuardarTarea = findViewById(R.id.btnGuardarTarea);
        rvTareas = findViewById(R.id.rvTareas);

        // 2. Recibir el correo del Intent
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String correoRecibido = extras.getString("DATO_CORREO");
            tvBienvenida.setText("Bienvenido:\n" + correoRecibido);
        }

        // 3. Configurar el Spinner (desplegable)
        String[] categorias = {"Estudios", "Trabajo", "Personal", "Hogar"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, categorias);
        spCategoria.setAdapter(adapter);

        // 4. Lógica del botón Guardar
        btnGuardarTarea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Simular que el progreso sube al guardar una tarea
                int progresoActual = pbProgreso.getProgress();
                if(progresoActual < 100){
                    pbProgreso.setProgress(progresoActual + 20);
                }

                String categoria = spCategoria.getSelectedItem().toString();
                boolean esUrgente = cbUrgente.isChecked();
                float dificultad = rbDificultad.getRating();

                Toast.makeText(SegundaActivity.this, "Tarea guardada: " + categoria + " | Dificultad: " + dificultad, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
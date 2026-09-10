package com.example.evaluacion;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class SegundaActivity extends AppCompatActivity {

    private TextView tvBienvenida;
    private EditText etTituloTarea;
    private Spinner spCategoria;
    private CheckBox cbUrgente;
    private RadioGroup rgEstado;
    private RatingBar rbDificultad;
    private ProgressBar pbProgreso;
    private Button btnGuardarTarea;
    private RecyclerView rvTareas;

    private ArrayList<String> listaTareas;
    private TareaAdapter tareaAdapter;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_segunda);

        // Configuración de los márgenes del sistema
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // 1. Enlazar variables con XML (findViewById)
        tvBienvenida = findViewById(R.id.tvBienvenida);
        etTituloTarea = findViewById(R.id.etTituloTarea);
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
            if (correoRecibido != null && !correoRecibido.isEmpty()) {
                tvBienvenida.setText("Bienvenido:\n" + correoRecibido);
            }
        }

        // 3. Configurar el Spinner (desplegable)
        String[] categorias = {"Estudios", "Trabajo", "Personal", "Hogar"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, categorias);
        spCategoria.setAdapter(adapter);

        // 4. Inicializar SharedPreferences y cargar tareas guardadas (sin datos por defecto)
        sharedPreferences = getSharedPreferences("MisTareasPrefs", MODE_PRIVATE);
        Set<String> tareasGuardadas = sharedPreferences.getStringSet("tareas_key", new LinkedHashSet<>());
        listaTareas = new ArrayList<>(tareasGuardadas);

        // 5. Configurar el RecyclerView
        rvTareas.setLayoutManager(new LinearLayoutManager(this));
        tareaAdapter = new TareaAdapter(listaTareas);
        rvTareas.setAdapter(tareaAdapter);

        // 6. Lógica del botón Guardar
        btnGuardarTarea.setOnClickListener(v -> {
            String titulo = etTituloTarea.getText().toString().trim();
            if (titulo.isEmpty()) {
                Toast.makeText(SegundaActivity.this, "Por favor ingresa el título de la tarea", Toast.LENGTH_SHORT).show();
                return;
            }

            String categoria = spCategoria.getSelectedItem().toString();
            boolean urgente = cbUrgente.isChecked();
            float dificultad = rbDificultad.getRating();

            String tareaInfo = titulo + " (" + categoria + ")" + (urgente ? " [URGENTE]" : "") + " - Dif: " + (int)dificultad + "★";

            listaTareas.add(tareaInfo);

            // Guardar en SharedPreferences
            Set<String> setParaGuardar = new LinkedHashSet<>(listaTareas);
            sharedPreferences.edit().putStringSet("tareas_key", setParaGuardar).apply();

            tareaAdapter.actualizarLista(listaTareas);
            etTituloTarea.setText("");
            cbUrgente.setChecked(false);
            rbDificultad.setRating(0);

            int progresoActual = pbProgreso.getProgress();
            if (progresoActual < 100) {
                pbProgreso.setProgress(progresoActual + 20);
            }

            Toast.makeText(SegundaActivity.this, "¡Tarea guardada con éxito!", Toast.LENGTH_SHORT).show();
        });
    }

    // Adaptador clásico para el RecyclerView
    private class TareaAdapter extends RecyclerView.Adapter<TareaAdapter.TareaViewHolder> {
        private List<String> datos;

        public TareaAdapter(List<String> datos) {
            this.datos = datos;
        }

        public void actualizarLista(List<String> nuevosDatos) {
            this.datos = nuevosDatos;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public TareaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout, parent, false);
            return new TareaViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull TareaViewHolder holder, int position) {
            holder.tvTitulo.setText(datos.get(position));
        }

        @Override
        public int getItemCount() {
            return datos.size();
        }

        class TareaViewHolder extends RecyclerView.ViewHolder {
            TextView tvTitulo;
            public TareaViewHolder(@NonNull View itemView) {
                super(itemView);
                tvTitulo = itemView.findViewById(R.id.tvTituloItem);
            }
        }
    }
}

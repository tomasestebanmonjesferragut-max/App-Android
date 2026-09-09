package com.example.evaluacion;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
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

public class SegundaActivity extends AppCompatActivity {

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

        // Configuración de los márgenes del sistema
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content), (v, insets) -> {
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

        // 4. Configurar el RecyclerView
        rvTareas.setLayoutManager(new LinearLayoutManager(this));
        String[] tareasDePrueba = {"Terminar el laboratorio", "Estudiar Java", "Comprar pan"};
        rvTareas.setAdapter(new TareaAdapter(tareasDePrueba));

        // 5. Lógica del botón Guardar
        btnGuardarTarea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int progresoActual = pbProgreso.getProgress();
                if(progresoActual < 100){
                    pbProgreso.setProgress(progresoActual + 20);
                }

                String categoria = spCategoria.getSelectedItem().toString();
                float dificultad = rbDificultad.getRating();
                Toast.makeText(SegundaActivity.this, "Tarea guardada: " + categoria + " | Dificultad: " + dificultad, Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Adaptador clásico para el RecyclerView
    private class TareaAdapter extends RecyclerView.Adapter<TareaAdapter.TareaViewHolder> {
        private String[] datos;

        public TareaAdapter(String[] datos) {
            this.datos = datos;
        }

        @NonNull
        @Override
        public TareaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout, parent, false);
            return new TareaViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull TareaViewHolder holder, int position) {
            holder.tvTitulo.setText(datos[position]);
        }

        @Override
        public int getItemCount() {
            return datos.length;
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

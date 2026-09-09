package com.example.evaluacion;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class RegistroActivity extends AppCompatActivity {

    private EditText etRut, etNombre, etApellidoPaterno, etApellidoMaterno, etEdad, etTelefono, etDireccion, etCorreo;
    private RadioGroup rgGenero;
    private Button btnRegistrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registro);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Vincular componentes con el XML
        etRut = findViewById(R.id.etRut);
        etNombre = findViewById(R.id.etNombre);
        etApellidoPaterno = findViewById(R.id.etApellidoPaterno);
        etApellidoMaterno = findViewById(R.id.etApellidoMaterno);
        etEdad = findViewById(R.id.etEdad);
        rgGenero = findViewById(R.id.rgGenero);
        etTelefono = findViewById(R.id.etTelefono);
        etDireccion = findViewById(R.id.etDireccion);
        etCorreo = findViewById(R.id.etCorreo);
        btnRegistrar = findViewById(R.id.btnRegistrar);

        // Lógica del botón de registro
        btnRegistrar.setOnClickListener(v -> {
            String rut = etRut.getText().toString().trim();
            String nombre = etNombre.getText().toString().trim();
            String apellidoP = etApellidoPaterno.getText().toString().trim();
            String correo = etCorreo.getText().toString().trim();

            if (rut.isEmpty() || nombre.isEmpty() || apellidoP.isEmpty() || correo.isEmpty()) {
                Toast.makeText(RegistroActivity.this, "Por favor completa los campos obligatorios", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(RegistroActivity.this, "¡Usuario registrado con éxito!", Toast.LENGTH_SHORT).show();
                finish(); // Cierra el registro y vuelve al Login
            }
        });
    }
}
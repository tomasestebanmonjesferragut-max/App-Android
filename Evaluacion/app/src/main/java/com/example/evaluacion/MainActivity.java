package com.example.evaluacion;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private EditText etCorreoInput;
    private EditText etClaveInput;
    private Button btnIngresar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        etCorreoInput = findViewById(R.id.etCorreoInput);
        etClaveInput = findViewById(R.id.etClaveInput);
        btnIngresar = findViewById(R.id.btnIngresar);

        Button btnRegistrarse = findViewById(R.id.btnRegistrarse);
        Button btnSalir = findViewById(R.id.btnSalir);
        ProgressBar pbLogin = findViewById(R.id.pbLogin);

        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String correo = etCorreoInput.getText().toString().trim().toLowerCase();
                String clave = etClaveInput.getText().toString();

                if(correo.isEmpty() || clave.isEmpty()){
                    Toast.makeText(MainActivity.this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
                }
                else if (correo.endsWith("@alumnos.santotomas.cl") && clave.equals("123456")) {
                    pbLogin.setVisibility(View.VISIBLE);

                    new android.os.Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            pbLogin.setVisibility(View.GONE);
                            // Ahora envía a la Bienvenida
                            Intent intent = new Intent(MainActivity.this, activity_bienvenida.class);
                            intent.putExtra("DATO_CORREO", correo);
                            startActivity(intent);
                        }
                    }, 1500);
                }
                else {
                    Toast.makeText(MainActivity.this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegistroActivity.class);
                startActivity(intent);
            }
        });

        btnSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAffinity();
            }
        });
    }
}
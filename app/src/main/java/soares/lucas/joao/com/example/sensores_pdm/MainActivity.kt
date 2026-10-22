package soares.lucas.joao.com.example.sensores_pdm

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnAcelerometro = findViewById<Button>(R.id.btnAcelerometro)
        val btnSensorLuz = findViewById<Button>(R.id.btnSensorLuz)
        val btnSensorProximidade = findViewById<Button>(R.id.btnSensorProximidade)

        // Navegação explícita para a tela do acelerômetro
        btnAcelerometro.setOnClickListener {
            val intent = Intent(this, AccelerometerActivity::class.java)
            startActivity(intent)
        }

        // Navegação explícita para a tela do sensor de luz
        btnSensorLuz.setOnClickListener {
            val intent = Intent(this, LightSensorActivity::class.java)
            startActivity(intent)
        }

        // Navegação explícita para a tela do sensor de proximidade
        btnSensorProximidade.setOnClickListener {
            val intent = Intent(this, ProximitySensorActivity::class.java)
            startActivity(intent)
        }
    }
}

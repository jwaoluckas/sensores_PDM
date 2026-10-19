package soares.lucas.joao.com.example.sensores_pdm

import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.ComponentActivity

class LightSensorActivity : ComponentActivity(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private var lightSensor: Sensor? = null

    private lateinit var rootLayout: View
    private lateinit var tvLux: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_light_sensor)
        title = getString(R.string.titulo_sensor_luz)

        rootLayout = findViewById(R.id.rootLightSensor)
        tvLux = findViewById(R.id.tvLux)

        // Obtém o serviço de sensores do sistema e o sensor de luz ambiente
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)

        // Nem todo aparelho (ou emulador) possui sensor de luz
        if (lightSensor == null) {
            tvLux.textSize = 24f
            tvLux.text = getString(R.string.sensor_luz_indisponivel)
        }
    }

    override fun onResume() {
        super.onResume()
        // Começa a escutar o sensor somente quando a tela está visível
        lightSensor?.let { sensor ->
            sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    override fun onPause() {
        super.onPause()
        // Para de escutar o sensor ao sair da tela, economizando bateria
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent) {
        if (event.sensor.type != Sensor.TYPE_LIGHT) return

        // Para TYPE_LIGHT, values[0] contém a luminosidade em lux
        val lux = event.values[0]
        tvLux.text = getString(R.string.valor_lux, lux)

        // Ambiente escuro (< 40 lux): fundo preto e texto branco; caso contrário, o inverso
        if (lux < LIMITE_ESCURO_LUX) {
            rootLayout.setBackgroundColor(Color.BLACK)
            tvLux.setTextColor(Color.WHITE)
        } else {
            rootLayout.setBackgroundColor(Color.WHITE)
            tvLux.setTextColor(Color.BLACK)
        }
    }

    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
        // Não utilizado neste exemplo
    }

    companion object {
        private const val LIMITE_ESCURO_LUX = 40f
    }
}

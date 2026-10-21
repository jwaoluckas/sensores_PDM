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

class ProximitySensorActivity : ComponentActivity(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private var proximitySensor: Sensor? = null

    private lateinit var rootLayout: View
    private lateinit var tvProximityStatus: TextView
    private lateinit var tvProximityValue: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_proximity_sensor)
        title = getString(R.string.titulo_sensor_proximidade)

        rootLayout = findViewById(R.id.rootProximitySensor)
        tvProximityStatus = findViewById(R.id.tvProximityStatus)
        tvProximityValue = findViewById(R.id.tvProximityValue)

        // Obtém o serviço de sensores do sistema e o sensor de proximidade
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY)

        // Nem todo aparelho (ou emulador) possui sensor de proximidade
        if (proximitySensor == null) {
            tvProximityStatus.textSize = 24f
            tvProximityStatus.text = getString(R.string.sensor_proximidade_indisponivel)
            tvProximityValue.visibility = View.GONE
        }
    }

    override fun onResume() {
        super.onResume()
        // Começa a escutar o sensor somente quando a tela está visível
        proximitySensor?.let { sensor ->
            sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    override fun onPause() {
        super.onPause()
        // Para de escutar o sensor ao sair da tela, economizando bateria
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent) {
        if (event.sensor.type != Sensor.TYPE_PROXIMITY) return

        // Para TYPE_PROXIMITY, values[0] contém a distância em centímetros
        val distance = event.values[0]
        tvProximityValue.text = getString(R.string.valor_proximidade, distance)

        // Muitos aparelhos informam apenas dois estados: próximo ou distante
        val isNear = distance < event.sensor.maximumRange
        if (isNear) {
            tvProximityStatus.text = getString(R.string.objeto_proximo)
            rootLayout.setBackgroundColor(Color.BLACK)
            tvProximityStatus.setTextColor(Color.WHITE)
            tvProximityValue.setTextColor(Color.WHITE)
        } else {
            tvProximityStatus.text = getString(R.string.objeto_distante)
            rootLayout.setBackgroundColor(Color.WHITE)
            tvProximityStatus.setTextColor(Color.BLACK)
            tvProximityValue.setTextColor(Color.BLACK)
        }
    }

    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
        // Não utilizado neste exemplo
    }
}

package soares.lucas.joao.com.example.sensores_pdm

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.ComponentActivity

class AccelerometerActivity : ComponentActivity(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private var accelerometerSensor: Sensor? = null

    private lateinit var rootLayout: View
    private lateinit var tvAccelerometerStatus: TextView
    private lateinit var tvAxisX: TextView
    private lateinit var tvAxisY: TextView
    private lateinit var tvAxisZ: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_accelerometer)
        title = getString(R.string.titulo_acelerometro)

        rootLayout = findViewById(R.id.rootAccelerometer)
        tvAccelerometerStatus = findViewById(R.id.tvAccelerometerStatus)
        tvAxisX = findViewById(R.id.tvAxisX)
        tvAxisY = findViewById(R.id.tvAxisY)
        tvAxisZ = findViewById(R.id.tvAxisZ)

        // Obtém o serviço de sensores do sistema e o acelerômetro
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

        // Nem todo aparelho (ou emulador) possui acelerômetro
        if (accelerometerSensor == null) {
            tvAccelerometerStatus.textSize = 24f
            tvAccelerometerStatus.text = getString(R.string.acelerometro_indisponivel)
            tvAxisX.visibility = View.GONE
            tvAxisY.visibility = View.GONE
            tvAxisZ.visibility = View.GONE
        }
    }

    override fun onResume() {
        super.onResume()
        // Começa a escutar o sensor somente quando a tela está visível
        accelerometerSensor?.let { sensor ->
            sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    override fun onPause() {
        super.onPause()
        // Para de escutar o sensor ao sair da tela, economizando bateria
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent) {
        if (event.sensor.type != Sensor.TYPE_ACCELEROMETER) return

        // Para TYPE_ACCELEROMETER:
        // event.values[0] = força de aceleração no eixo X
        // event.values[1] = força de aceleração no eixo Y
        // event.values[2] = força de aceleração no eixo Z
        val x = event.values[0]
        val y = event.values[1]
        val z = event.values[2]

        tvAxisX.text = getString(R.string.valor_eixo_x, x)
        tvAxisY.text = getString(R.string.valor_eixo_y, y)
        tvAxisZ.text = getString(R.string.valor_eixo_z, z)
    }

    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
        // Não utilizado neste exemplo
    }
}

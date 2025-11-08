// MarbleGame — SensorRepository.kt
// Purpose: Exposes gravity (or accelerometer fallback) readings as a Flow,
//          applying a low-pass filter when gravity sensor is unavailable.
// Lifecycle: Registers a listener on collection and unregisters on close.
package com.example.marblegame.data

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class SensorRepository(context: Context) {

    private val sensorManager =
        context.getSystemService(Context.SENSOR_SERVICE) as SensorManager

    private val gravitySensor: Sensor? =
        sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY)

    private val accelSensor: Sensor? =
        sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

    fun gravityFlow(): Flow<GravitySample> = callbackFlow {
        val useGravity = gravitySensor != null
        val sensor = gravitySensor ?: accelSensor
        if (sensor == null) {
            close(IllegalStateException("No Gravity or Accelerometer sensor available"))
            return@callbackFlow
        }

        val lp = LowPassFilter(alpha = if (useGravity) 1.0f else 0.88f)

        val listener = object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent) {
                val tNs = event.timestamp
                val values = event.values

                val (gx, gy, gz) = if (useGravity) {
                    Triple(values[0], values[1], values[2])
                } else {
                    val out = lp.filter(values)
                    Triple(out[0], out[1], out[2])
                }

                trySend(GravitySample(gx, gy, gz, tNs))
            }
            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) = Unit
        }

        sensorManager.registerListener(
            listener,
            sensor,
            SensorManager.SENSOR_DELAY_GAME
        )

        awaitClose { sensorManager.unregisterListener(listener) }
    }
}

data class GravitySample(
    val gx: Float,
    val gy: Float,
    val gz: Float,
    val timestampNs: Long
)

private class LowPassFilter(private val alpha: Float) {
    private var init = false
    private val state = FloatArray(3)

    fun filter(input: FloatArray): FloatArray {
        if (!init) {
            state[0] = input[0]; state[1] = input[1]; state[2] = input[2]
            init = true
            return state.copyOf()
        }
        for (i in 0..2) {
            state[i] = alpha * state[i] + (1f - alpha) * input[i]
        }
        return state.copyOf()
    }
}

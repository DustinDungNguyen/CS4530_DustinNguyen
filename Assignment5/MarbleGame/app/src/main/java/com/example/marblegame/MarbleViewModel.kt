// MarbleGame — MarbleViewModel.kt
// Purpose: MVVM state holder. Integrates gravity to velocity and position,
//          applies friction and wall bounces, and clamps within bounds.
package com.example.marblegame

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.marblegame.data.GravitySample
import com.example.marblegame.data.SensorRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlin.math.exp
import kotlin.math.max

data class MarbleUiState(
    val xPx: Float = 0f,
    val yPx: Float = 0f,
    val vx: Float = 0f,
    val vy: Float = 0f,
    val marbleSizePx: Float = 60f,
    val areaWidthPx: Float = 0f,
    val areaHeightPx: Float = 0f
)

class MarbleViewModel(
    private val repo: SensorRepository
) : ViewModel() {

    private val _state = MutableStateFlow(MarbleUiState())
    val state = _state.asStateFlow()

    private var sensorJob: Job? = null
    private var lastTsNs: Long? = null

    private val accelScale = -30f
    private val frictionPerSecond = 1.4f
    private val wallDamping = 0.35f

    fun startSensors() {
        if (sensorJob != null) return
        sensorJob = viewModelScope.launch {
            repo.gravityFlow().collectLatest { sample -> onGravity(sample) }
        }
    }

    fun stopSensors() {
        sensorJob?.cancel()
        sensorJob = null
        lastTsNs = null
    }

    fun setBounds(areaWidthPx: Float, areaHeightPx: Float, marbleSizePx: Float) {
        _state.value = _state.value.copy(
            areaWidthPx = areaWidthPx,
            areaHeightPx = areaHeightPx,
            marbleSizePx = marbleSizePx
        )
        clampPosition()
    }

    private fun onGravity(sample: GravitySample) {
        val s = _state.value
        val last = lastTsNs
        lastTsNs = sample.timestampNs
        if (last == null) return

        val dt = ((sample.timestampNs - last).coerceAtLeast(1L)) / 1_000_000_000f

        var ax = sample.gx * accelScale
        var ay = sample.gy * accelScale

        var vx = s.vx + ax * dt
        var vy = s.vy + ay * dt

        val frictionFactor = exp(-frictionPerSecond * dt)
        vx *= frictionFactor
        vy *= frictionFactor

        var x = s.xPx + vx * dt
        var y = s.yPx + vy * dt

        val maxX = max(0f, s.areaWidthPx - s.marbleSizePx)
        val maxY = max(0f, s.areaHeightPx - s.marbleSizePx)

        if (x < 0f) { x = 0f; vx = -vx * wallDamping }
        else if (x > maxX) { x = maxX; vx = -vx * wallDamping }

        if (y < 0f) { y = 0f; vy = -vy * wallDamping }
        else if (y > maxY) { y = maxY; vy = -vy * wallDamping }

        _state.value = s.copy(xPx = x, yPx = y, vx = vx, vy = vy)
    }

    private fun clampPosition() {
        val s = _state.value
        val maxX = max(0f, s.areaWidthPx - s.marbleSizePx)
        val maxY = max(0f, s.areaHeightPx - s.marbleSizePx)
        _state.value = s.copy(
            xPx = s.xPx.coerceIn(0f, maxX),
            yPx = s.yPx.coerceIn(0f, maxY)
        )
    }

    companion object {
        fun provide(activity: MainActivity): MarbleViewModel {
            val repo = SensorRepository(activity)
            return ViewModelProvider(
                activity,
                object : ViewModelProvider.Factory {
                    @Suppress("UNCHECKED_CAST")
                    override fun <T : ViewModel> create(modelClass: Class<T>): T {
                        return MarbleViewModel(repo) as T
                    }
                }
            )[MarbleViewModel::class.java]
        }
    }
}

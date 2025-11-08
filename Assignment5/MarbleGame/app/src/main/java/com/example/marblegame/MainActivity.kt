// MarbleGame — MainActivity.kt
// Purpose: Hosts Compose content, wires ViewModel, and ties sensor lifecycle
//          to activity lifecycle (start in onResume, stop in onPause).

package com.example.marblegame

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.marblegame.screen.MarbleScreen
import com.example.marblegame.ui.theme.AppTheme

class MainActivity : ComponentActivity() {

    private lateinit var vm: MarbleViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vm = MarbleViewModel.provide(this)

        setContent {
            AppTheme {
                MarbleScreen(vm = vm)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        vm.startSensors()
    }

    override fun onPause() {
        super.onPause()
        vm.stopSensors()
    }
}



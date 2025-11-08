// MarbleGame — MarbleScreen.kt
// Purpose: Compose UI that renders the playfield and marble, reads bounds,
//          and offsets the marble according to ViewModel state.
package com.example.marblegame.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset // ✅ correct offset import
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.marblegame.MarbleViewModel
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.layout.layout



@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun MarbleScreen(vm: MarbleViewModel) {
    val state by vm.state.collectAsStateWithLifecycle()
    val density = LocalDensity.current

    BoxWithConstraints(
        modifier = Modifier.fillMaxSize()
    ) {
        val areaWidthPx = with(density) { maxWidth.toPx() }
        val areaHeightPx = with(density) { maxHeight.toPx() }

        val marbleSizeDp = 40.dp
        val marbleSizePx = with(density) { marbleSizeDp.toPx() }

        LaunchedEffect(areaWidthPx, areaHeightPx) {
            vm.setBounds(areaWidthPx, areaHeightPx, marbleSizePx)
        }

        val xDp = with(density) { state.xPx.toDp() }
        val yDp = with(density) { state.yPx.toDp() }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surfaceVariant)
        )

        Box(
            modifier = Modifier
                .offset(x = xDp, y = yDp)
                .size(marbleSizeDp)
                .background(
                    color = MaterialTheme.colorScheme.primary,
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {}
    }
}

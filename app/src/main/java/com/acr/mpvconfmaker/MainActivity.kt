package com.acr.mpvconfmaker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MpvConfMakerApp()
        }
    }
}

@Composable
private fun MpvConfMakerApp() {
    MaterialTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background,
        ) {
            HomeScreen()
        }
    }
}

@Composable
private fun HomeScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = "MPV.conf-Maker",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
        )
        Text(
            modifier = Modifier.padding(top = 12.dp, bottom = 32.dp),
            text = "Generador visual de mpv.conf para Android",
            style = MaterialTheme.typography.bodyLarge,
        )
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {},
        ) {
            Text(text = "Crear configuración")
        }
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp),
            onClick = {},
        ) {
            Text(text = "Ver preview")
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() {
    MpvConfMakerApp()
}

package com.example.visiograph

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.visiograph.ui.theme.VisiographTheme
import com.example.visiograph.uicontroller.VisiographApp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            VisiographTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    VisiographApp(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

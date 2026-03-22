package com.mixmaster.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.mixmaster.app.ui.navigation.NavGraph
import com.mixmaster.app.ui.theme.MixMasterTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MixMasterTheme {
                NavGraph()
            }
        }
    }
}

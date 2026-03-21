package com.mixmaster.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.mixmaster.app.ui.navigation.NavGraph
import com.mixmaster.app.ui.theme.MixMasterTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MixMasterTheme {
                val navController = rememberNavController()
                NavGraph(navController = navController)
            }
        }
    }
}

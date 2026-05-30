package com.paoloesan.pc01movilesguzman24100383valenzuela22101808

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.paoloesan.pc01movilesguzman24100383valenzuela22101808.presentation.navigation.AppNavGraph
import com.paoloesan.pc01movilesguzman24100383valenzuela22101808.ui.theme.PC01MOVILESGUZMAN24100383VALENZUELA22101808Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PC01MOVILESGUZMAN24100383VALENZUELA22101808Theme {
                AppNavGraph()
            }
        }
    }
}

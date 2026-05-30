package com.paoloesan.pc01movilesguzman24100383valenzuela22101808

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.paoloesan.pc01movilesguzman24100383valenzuela22101808.presentation.permissions.LocationPermissionScreen
import com.paoloesan.pc01movilesguzman24100383valenzuela22101808.ui.theme.PC01MOVILESGUZMAN24100383VALENZUELA22101808Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PC01MOVILESGUZMAN24100383VALENZUELA22101808Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    LocationPermissionScreen()
                }
            }
        }
    }
}

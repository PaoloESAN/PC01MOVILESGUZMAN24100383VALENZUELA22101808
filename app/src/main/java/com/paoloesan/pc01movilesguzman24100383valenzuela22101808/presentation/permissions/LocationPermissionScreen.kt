package com.paoloesan.pc01movilesguzman24100383valenzuela22101808.presentation.permissions

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import androidx.core.content.ContextCompat
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationPermissionScreen(navController: NavController) {
    val context = LocalContext.current
    var permissionStatus by remember { mutableStateOf("") }
    var isPermanentlyDenied by remember { mutableStateOf(false) }

    val permission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        Manifest.permission.ACCESS_FINE_LOCATION
    } else {
        Manifest.permission.ACCESS_COARSE_LOCATION
    }

    val isAlreadyGranted = ContextCompat.checkSelfPermission(context, permission) ==
            PackageManager.PERMISSION_GRANTED

    val locationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            permissionStatus = "Permiso concedido"
            isPermanentlyDenied = false
        } else {
            permissionStatus = "Permiso denegado"
            isPermanentlyDenied = !shouldShowRequestPermissionRationale(
                context as android.app.Activity, permission
            )
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Permiso de Ubicación") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Permiso de Ubicación",
                style = MaterialTheme.typography.headlineSmall
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Para asistencia de viaje, necesitamos acceder a tu ubicación.",
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(modifier = Modifier.height(24.dp))

            if (isAlreadyGranted) {
                Text(
                    text = "Permiso concedido",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.primary
                )
            } else if (isPermanentlyDenied) {
                Text(
                    text = "El permiso fue denegado permanentemente. Debes habilitarlo desde Ajustes.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.error
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = {
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                        data = Uri.fromParts("package", context.packageName, null)
                    }
                    context.startActivity(intent)
                }) {
                    Text("Abrir Ajustes")
                }
            } else {
                Button(onClick = {
                    locationPermissionLauncher.launch(permission)
                }) {
                    Text("Solicitar Permiso de Ubicación")
                }
            }

            if (permissionStatus.isNotEmpty()) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = permissionStatus,
                    style = MaterialTheme.typography.titleMedium,
                    color = if (permissionStatus == "Permiso concedido")
                        MaterialTheme.colorScheme.primary
                    else
                        MaterialTheme.colorScheme.error
                )
            }
        }
    }
}

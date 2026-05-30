package com.paoloesan.pc01movilesguzman24100383valenzuela22101808.presentation.equipaje

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EquipmentCalculatorScreen(navController: NavController) {
    var weightText by remember { mutableStateOf("") }
    var flightType by remember { mutableStateOf("Nacional") }
    var resultMessage by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    val maxWeight = if (flightType == "Nacional") 23.0 else 32.0

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Calculadora de Equipaje") },
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
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Calculadora de Equipaje",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = weightText,
                onValueChange = {
                    weightText = it
                    errorMessage = ""
                    resultMessage = ""
                },
                label = { Text("Peso de la maleta (kg)") },
                modifier = Modifier.fillMaxWidth(),
                isError = errorMessage.isNotEmpty(),
                supportingText = if (errorMessage.isNotEmpty()) {
                    { Text(errorMessage, color = MaterialTheme.colorScheme.error) }
                } else null
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Tipo de vuelo",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = flightType == "Nacional",
                    onClick = {
                        flightType = "Nacional"
                        resultMessage = ""
                    }
                )
                Text(
                    text = "Nacional (máx. 23 kg)",
                    modifier = Modifier.padding(start = 4.dp)
                )
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = flightType == "Internacional",
                    onClick = {
                        flightType = "Internacional"
                        resultMessage = ""
                    }
                )
                Text(
                    text = "Internacional (máx. 32 kg)",
                    modifier = Modifier.padding(start = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    val weight = weightText.trim()

                    if (weight.isEmpty()) {
                        errorMessage = "Campo obligatorio"
                        resultMessage = ""
                        return@Button
                    }

                    val weightValue = weight.toDoubleOrNull()
                    if (weightValue == null) {
                        errorMessage = "Debe ingresar un valor numérico"
                        resultMessage = ""
                        return@Button
                    }

                    if (weightValue <= 0) {
                        errorMessage = "El peso debe ser mayor a cero"
                        resultMessage = ""
                        return@Button
                    }

                    errorMessage = ""
                    val exceeded = weightValue - maxWeight
                    resultMessage = if (exceeded <= 0) {
                        "Cumple con el límite permitido ($maxWeight kg)"
                    } else {
                        "Excede el límite permitido en ${"%.2f".format(exceeded)} kg"
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Calcular")
            }

            Spacer(modifier = Modifier.height(24.dp))

            if (resultMessage.isNotEmpty()) {
                Text(
                    text = resultMessage,
                    style = MaterialTheme.typography.titleMedium,
                    color = if (resultMessage.startsWith("Cumple"))
                        MaterialTheme.colorScheme.primary
                    else
                        MaterialTheme.colorScheme.error,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

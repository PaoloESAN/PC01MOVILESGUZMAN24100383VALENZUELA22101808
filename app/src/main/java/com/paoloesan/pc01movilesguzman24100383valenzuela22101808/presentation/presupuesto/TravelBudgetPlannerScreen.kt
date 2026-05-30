package com.paoloesan.pc01movilesguzman24100383valenzuela22101808.presentation.presupuesto

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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

data class AccommodationType(val name: String, val factor: Double)

private val accommodationTypes = listOf(
    AccommodationType("Económico", 0.8),
    AccommodationType("Estándar", 1.0),
    AccommodationType("Premium", 1.5)
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TravelBudgetPlannerScreen(navController: NavController) {
    var daysText by remember { mutableStateOf("") }
    var dailyBudgetText by remember { mutableStateOf("") }
    var selectedAccommodation by remember { mutableStateOf(accommodationTypes[0]) }
    var dropdownExpanded by remember { mutableStateOf(false) }
    var resultText by remember { mutableStateOf("") }
    var daysError by remember { mutableStateOf("") }
    var budgetError by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Planificador de Presupuesto de Viaje") },
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
                text = "Planificador de Presupuesto",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = daysText,
                onValueChange = {
                    daysText = it
                    daysError = ""
                    resultText = ""
                },
                label = { Text("Cantidad de días") },
                modifier = Modifier.fillMaxWidth(),
                isError = daysError.isNotEmpty(),
                supportingText = if (daysError.isNotEmpty()) {
                    { Text(daysError, color = MaterialTheme.colorScheme.error) }
                } else null
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = dailyBudgetText,
                onValueChange = {
                    dailyBudgetText = it
                    budgetError = ""
                    resultText = ""
                },
                label = { Text("Presupuesto diario") },
                modifier = Modifier.fillMaxWidth(),
                isError = budgetError.isNotEmpty(),
                supportingText = if (budgetError.isNotEmpty()) {
                    { Text(budgetError, color = MaterialTheme.colorScheme.error) }
                } else null
            )

            Spacer(modifier = Modifier.height(16.dp))

            ExposedDropdownMenuBox(
                expanded = dropdownExpanded,
                onExpandedChange = { dropdownExpanded = it }
            ) {
                OutlinedTextField(
                    value = selectedAccommodation.name,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Tipo de alojamiento") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = dropdownExpanded) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor()
                )
                ExposedDropdownMenu(
                    expanded = dropdownExpanded,
                    onDismissRequest = { dropdownExpanded = false }
                ) {
                    accommodationTypes.forEach { type ->
                        DropdownMenuItem(
                            text = { Text("${type.name} (x${type.factor})") },
                            onClick = {
                                selectedAccommodation = type
                                dropdownExpanded = false
                                resultText = ""
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    val daysStr = daysText.trim()
                    val budgetStr = dailyBudgetText.trim()

                    daysError = ""
                    budgetError = ""
                    resultText = ""

                    if (daysStr.isEmpty()) {
                        daysError = "Campo obligatorio"
                    }
                    if (budgetStr.isEmpty()) {
                        budgetError = "Campo obligatorio"
                    }
                    if (daysStr.isEmpty() || budgetStr.isEmpty()) return@Button

                    val daysValue = daysStr.toIntOrNull()
                    if (daysValue == null) {
                        daysError = "Debe ingresar un valor numérico"
                        return@Button
                    }
                    if (daysValue <= 0) {
                        daysError = "Los días deben ser mayores a cero"
                        return@Button
                    }

                    val budgetValue = budgetStr.toDoubleOrNull()
                    if (budgetValue == null) {
                        budgetError = "Debe ingresar un valor numérico"
                        return@Button
                    }
                    if (budgetValue <= 0) {
                        budgetError = "El presupuesto debe ser mayor a cero"
                        return@Button
                    }

                    val total = daysValue * budgetValue * selectedAccommodation.factor
                    resultText = "Presupuesto total: S/ ${"%.2f".format(total)}\n" +
                            "($daysValue días × S/ ${"%.2f".format(budgetValue)} diario × ${selectedAccommodation.factor})"
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Calcular Presupuesto")
            }

            Spacer(modifier = Modifier.height(24.dp))

            if (resultText.isNotEmpty()) {
                Text(
                    text = resultText,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

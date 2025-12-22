package org.example.mycartcalculator.view.dialogs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun SaveCartDialog(
    onConfirm: (String) -> Unit,
    onCancel: () -> Unit
) {
    var cartName by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onCancel,
        icon = {
            Icon(Icons.Filled.Save, contentDescription = null, modifier = Modifier.size(25.dp))
        },
        title = {
            Text("¿Guardar carrito?")
        },
        text = {
            Column {
                Text(
                    "Esto guardará el ticket actual en el historial y comenzará un nuevo carrito."
                )
                Spacer(Modifier.height(12.dp))
                OutlinedTextField(
                    value = cartName,
                    onValueChange = { cartName = it },
                    label = { Text("Nombre del carrito") },
                    placeholder = { Text("Ej: Walmart Octubre") },
                    singleLine = true
                )
            }
        },
        confirmButton = {
            Button(
                enabled = cartName.isNotBlank(),
                onClick = { onConfirm(cartName.trim()) }
            ) {
                Text("Guardar carrito", color = Color.White)
            }
        },
        dismissButton = {
            OutlinedButton(onClick = onCancel) {
                Text("Cancelar")
            }
        }
    )
}

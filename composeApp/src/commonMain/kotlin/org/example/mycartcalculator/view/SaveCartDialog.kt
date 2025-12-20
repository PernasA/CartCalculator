package org.example.mycartcalculator.view

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun SaveCartDialog(
    onConfirm: () -> Unit,
    onCancel: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onCancel,
        icon = {
            Icon(Icons.Default.Save, contentDescription = null)
        },
        title = {
            Text("¿Guardar carrito?")
        },
        text = {
            Text(
                "Esto guardará el ticket actual en el historial y comenzará un nuevo carrito."
            )
        },
        confirmButton = {
            Button(onClick = onConfirm) {
                Text("Guardar carrito")
            }
        },
        dismissButton = {
            OutlinedButton(onClick = onCancel) {
                Text("Cancelar")
            }
        }
    )
}

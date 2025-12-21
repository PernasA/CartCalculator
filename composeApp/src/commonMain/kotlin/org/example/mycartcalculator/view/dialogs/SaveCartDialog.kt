package org.example.mycartcalculator.view.dialogs

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun SaveCartDialog(
    onConfirm: () -> Unit,
    onCancel: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onCancel,
        icon = {
            Icon(Icons.Filled.Save, contentDescription = null, modifier = Modifier.size(25.dp))
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

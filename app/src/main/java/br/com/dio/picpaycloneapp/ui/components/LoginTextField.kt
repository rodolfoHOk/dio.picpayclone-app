package br.com.dio.picpaycloneapp.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun StyledTextField(
    label: String,
    value: String,
    onValueChange: (text: String) -> Unit,
    keyboardType: KeyboardType? = KeyboardType.Text
) {
    TextField(
        keyboardOptions = if (keyboardType != null)
            KeyboardOptions(keyboardType = keyboardType)
        else KeyboardOptions(
            keyboardType = KeyboardType.Text
        ),
        visualTransformation = if (keyboardType == KeyboardType.Password)
            PasswordVisualTransformation()
        else VisualTransformation.None,
        label = {
            Text(label)
        },
        value = value,
        onValueChange = onValueChange,
        maxLines = 1,
        textStyle = TextStyle(fontWeight = FontWeight.Bold),
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.background,
            unfocusedContainerColor = MaterialTheme.colorScheme.background,
            focusedIndicatorColor = MaterialTheme.colorScheme.tertiary,
            unfocusedIndicatorColor = MaterialTheme.colorScheme.onSurface,
            focusedLabelColor = MaterialTheme.colorScheme.primary,
            unfocusedLabelColor = MaterialTheme.colorScheme.onSurface,
            cursorColor = MaterialTheme.colorScheme.tertiary,
        )
    )
}

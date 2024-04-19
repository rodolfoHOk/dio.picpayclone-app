package br.com.dio.picpaycloneapp.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import br.com.dio.picpaycloneapp.R

@Composable
fun LoginTextField(
    label: String,
    value: String,
    onValueChange: (text: String) -> Unit,
    keyboardType: KeyboardType? = KeyboardType.Text,
    isError: Boolean = false,
    errorMessage: String = ""
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        textStyle = TextStyle(fontWeight = FontWeight.Bold),
        label = {
            Text(label)
        },
        trailingIcon = {
            if (isError)
                Icon(
                    painter = painterResource(id = R.drawable.ic_error),
                    contentDescription = "Erro!",
                    tint = MaterialTheme.colorScheme.error
                )
        },
        supportingText = {
            ErrorSupportText(text = errorMessage)
        },
        isError = isError,
        visualTransformation = if (keyboardType == KeyboardType.Password)
            PasswordVisualTransformation() else VisualTransformation.None,
        keyboardOptions = if (keyboardType != null) KeyboardOptions(keyboardType = keyboardType)
        else KeyboardOptions(keyboardType = KeyboardType.Text),
        maxLines = 1,
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

@Composable
fun ErrorSupportText(text: String) {
    Text(
        text = text,
        modifier = Modifier.fillMaxWidth(),
        style = TextStyle(color = MaterialTheme.colorScheme.error)
    )
}

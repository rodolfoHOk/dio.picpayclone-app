package br.com.dio.picpaycloneapp.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import br.com.dio.picpaycloneapp.R

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
            Text(
                label, style = TextStyle(color = colorResource(id = R.color.colorPrimaryDark))
            )
        },
        value = value,
        onValueChange = onValueChange,
        maxLines = 1,
        textStyle = TextStyle(fontWeight = FontWeight.Bold),
        modifier = Modifier.padding(16.dp).fillMaxWidth(),
        shape = RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp),
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.White,
            focusedIndicatorColor = colorResource(id = R.color.colorPrimaryDark),
            unfocusedIndicatorColor = colorResource(id = R.color.gray),
            placeholderColor = colorResource(id = R.color.colorPrimaryDark),
            cursorColor = colorResource(id = R.color.colorPrimaryDark),
        )
    )
}

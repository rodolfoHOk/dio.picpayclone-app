package br.com.dio.picpaycloneapp.ui.utils

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

class CreditCardMaskTransformation : VisualTransformation {

    override fun filter(text: AnnotatedString): TransformedText {
        val digitsOnly = text.text.filter { char ->
            char.isDigit()
        }
        val maskedString = digitsOnly.mapIndexed { index, char ->
            if (index == 3 || index == 7 || index == 11) {
                "$char "
            } else {
                char.toString()
            }
        }.joinToString("")

        val offsetMapping = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                return when {
                    offset <= 4 -> offset
                    offset <= 9 -> offset + 1
                    offset <= 14 -> offset + 2
                    else -> offset + 3
                }
            }

            override fun transformedToOriginal(offset: Int): Int {
                return when {
                    offset <= 4 -> offset
                    offset <= 9 -> offset - 1
                    offset <= 14 -> offset - 2
                    else -> offset - 3
                }
            }
        }

        return TransformedText(
            text = AnnotatedString(maskedString.trim()),
            offsetMapping = offsetMapping
        )
    }

}

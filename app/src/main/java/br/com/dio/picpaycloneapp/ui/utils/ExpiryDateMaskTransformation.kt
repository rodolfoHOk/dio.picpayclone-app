package br.com.dio.picpaycloneapp.ui.utils

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

class ExpiryDateMaskTransformation : VisualTransformation {

    override fun filter(text: AnnotatedString): TransformedText {
        val digitsOnly = text.text.filter { it.isDigit() }
        val maskedString = digitsOnly.mapIndexed { index, char ->
            if (index == 1) {
                "$char/"
            } else {
                char.toString()
            }
        }.joinToString("")

        val offsetMapping = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                return if (offset <= 2) {
                    offset
                } else {
                    offset + 1
                }
            }

            override fun transformedToOriginal(offset: Int): Int {
                return if (offset <= 2) {
                    offset
                } else {
                    offset - 1
                }
            }
        }

        return TransformedText(
            text = AnnotatedString(maskedString.trim()),
            offsetMapping = offsetMapping
        )
    }

}

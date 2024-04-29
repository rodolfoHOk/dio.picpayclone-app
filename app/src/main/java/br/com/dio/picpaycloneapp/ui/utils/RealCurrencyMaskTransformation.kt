package br.com.dio.picpaycloneapp.ui.utils

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

class RealCurrencyMaskTransformation : VisualTransformation {

    override fun filter(text: AnnotatedString): TransformedText {
        val originalText = text.text.trim()
        if (originalText.isEmpty()) {
            return TransformedText(text, OffsetMapping.Identity)
        }

        val digitsOnly = originalText.filter { char -> char.isDigit() }

        val amountString = if (digitsOnly.isNotEmpty()) {
            val amount = digitsOnly.toDouble() / 100
            "%.2f".format(amount)
        } else {
            "0.00"
        }

        val formattedText = "R$ ${amountString.replace(".", ",")}"

        return TransformedText(AnnotatedString(formattedText),
            offsetMapping = CurrencyOffsetMapping(originalText, formattedText))
    }

    inner class CurrencyOffsetMapping(originalText: String, formattedText: String) : OffsetMapping {
        private val originalLength: Int = originalText.length
        private val indexes = findDigitIndexes(originalText, formattedText)

        override fun originalToTransformed(offset: Int): Int {
            if (offset >= originalLength) {
                return indexes.last() + 1
            }
            return indexes[offset]
        }

        override fun transformedToOriginal(offset: Int): Int {
            return indexes.indexOfFirst { index ->
                index >= offset
            }.takeIf { index ->
                index != -1
            } ?: originalLength
        }

        private fun findDigitIndexes(firstString: String, secondString: String): List<Int> {
            val digitIndexes = mutableListOf<Int>()
            var currentIndex = 0
            for (digit in firstString) {
                val index = secondString.indexOf(digit, currentIndex)
                if (index != -1) {
                    digitIndexes.add(index)
                    currentIndex = index + 1
                } else {
                    return emptyList()
                }
            }
            return digitIndexes
        }
    }

}

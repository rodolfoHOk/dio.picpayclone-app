package br.com.dio.picpaycloneapp.ui.utils

import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

var dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy 'as' HH:mm")

fun formatDateTime(dateTime: String) : String {
    return dateFormatter.format(
        OffsetDateTime
            .parse(dateTime)
            .withOffsetSameInstant(ZoneOffset.of("-03:00"))
            .toLocalDateTime()
    )
}

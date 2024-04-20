package br.com.dio.picpaycloneapp.domain.models

data class PageTransaction(
    val content: List<Transaction> = listOf(),
    val number: Int = 0,
    val size: Int = 0,
    val totalPages: Int = 0,
    val totalElements: Int = 0
)

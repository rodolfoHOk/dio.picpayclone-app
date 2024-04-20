package br.com.dio.picpaycloneapp.models

data class PageTransaction(
    val content: List<Transaction> = listOf(),
    val empty: Boolean = true,
    val first: Boolean = true,
    val last: Boolean = true,
    val number: Int = 0,
    val numberOfElements: Int = 0,
    val pageable: Pageable = Pageable(),
    val size: Int = 0,
    val sort: Sort = Sort(),
    val totalElements: Int = 0,
    val totalPages: Int = 0
)

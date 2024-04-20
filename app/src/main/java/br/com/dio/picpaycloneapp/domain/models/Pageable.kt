package br.com.dio.picpaycloneapp.domain.models

data class Pageable(
    val offset: Int = 0,
    val pageNumber: Int = 0,
    val pageSize: Int = 10,
    val paged: Boolean = true,
    val sort: Sort = Sort(),
    val unpaged: Boolean = true
)

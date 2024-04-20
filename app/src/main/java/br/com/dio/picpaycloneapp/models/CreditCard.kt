package br.com.dio.picpaycloneapp.models

data class CreditCard(
    val banner: BannerCard = BannerCard.VISA,
    val number: String = "",
    val holderName: String = "",
    val expirationDate: String = "",
    val securityCode: String = "123",
    val tokenNumber: String = "",
    val user: User = User(),
    val isSaved: Boolean = false
)

package br.com.dio.picpaycloneapp.data.local.mappers

import br.com.dio.picpaycloneapp.data.local.entities.UserEntity
import br.com.dio.picpaycloneapp.domain.models.User

fun User.toEntity() : UserEntity = UserEntity(
    login = login,
    email = email,
    completeName = completeName,
    cpf = cpf,
    birthday = birthday,
    phoneNumber = phoneNumber,
    balance = balance
)

fun UserEntity.toModel() : User = User(
    login = login,
    email = email,
    completeName = completeName,
    cpf = cpf,
    birthday = birthday,
    phoneNumber = phoneNumber,
    balance = balance
)

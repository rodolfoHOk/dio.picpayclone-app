package br.com.dio.picpaycloneapp.data.mappers

import br.com.dio.picpaycloneapp.data.entities.UserEntity
import br.com.dio.picpaycloneapp.models.User

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

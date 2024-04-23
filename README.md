# PicPay Clone App

## Tecnologias

- Kotlin
- Android
- JetPack Compose
- ViewModel
- Hilt (Dependency Injection)
- Retrofit (Http Requests)
- JWT Authentication
- Room (Local Database)

### Bibliotecas adicionais

- androidx.navigation:navigation-compose
- androidx.compose.material:material
- com.google.dagger:hilt-android
- androidx.hilt:hilt-navigation-compose
- com.google.code.gson:gson
- com.squareup.retrofit2:retrofit
- com.squareup.retrofit2:converter-gson
- com.squareup.okhttp3:logging-interceptor
- androidx.paging:paging-compose
- androidx.room:room-runtime
- androidx.room:room-ktx
- androidx.room:room-paging

## Melhorias ou mudanças em relação ao desafio proposto

- Uso do Jetpack Compose ao invés de XML
- Uso do Navigation Compose
- Uso do Hilt ao invés do Koin
- Uso do Hilt com Navigation Compose
- Uso do Paging com Compose para paginação dos dados de transações
- Uso do Room com Paging para cache dos dados de transações vindos da API
- Separação um camadas data, domain e ui

## Telas

![Telas](/files/aplicativo-telas.png)

## Rodar

### Requisitos

- Backend rodando com dados iniciais carregados
- Android Studio
- Dispositivo Android Virtual

### Comandos

- Android Studio / Menu / Run / Run 'app'

## Backend

- [Repositório Github](https://github.com/rodolfoHOk/dio.picpayclone-backend)

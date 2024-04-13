package br.com.dio.picpaycloneapp.ui.screens.transaction

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import br.com.dio.picpaycloneapp.R
import br.com.dio.picpaycloneapp.data.User
import br.com.dio.picpaycloneapp.ui.LocalSnackbarHostState
import br.com.dio.picpaycloneapp.ui.bottom_nav.BottomNavScreen
import br.com.dio.picpaycloneapp.ui.components.TransactionTextField
import java.text.DecimalFormat

@Composable
fun TransactionScreen(
    navController: NavController,
    destinationUser: User?,
    transactionViewModel: TransactionViewModel
) {
    destinationUser?.let {
        val transactionUiState = transactionViewModel.state.collectAsState()

        val decimalFormat = DecimalFormat("#,###.00")

        val snackbarHostState = LocalSnackbarHostState.current
        LaunchedEffect(Unit) {
            transactionViewModel.action.collect { action ->
                when (action) {
                    is TransactionUiAction.TransactionSuccess -> {
                        snackbarHostState.showSnackbar(action.message)
                        navController.navigate(BottomNavScreen.Payment.route)
                    }
                    is TransactionUiAction.TransactionError -> {
                        snackbarHostState.showSnackbar(action.message)
                    }
                }
            }
        }

        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row {
                        Image(
                            painter = painterResource(id = R.drawable.ic_contact),
                            contentDescription = "Avatar do contato"
                        )

                        Column {
                            Text(
                                text = destinationUser.login,
                                style = TextStyle(fontWeight = FontWeight.Bold),
                                modifier = Modifier.padding(top = 18.dp, start = 8.dp)
                            )

                            Text(
                                text = destinationUser.completeName,
                                modifier = Modifier.padding(top = 4.dp, start = 8.dp)
                            )

                            TextField(
                                value = transactionUiState.value.amount.toString(),
                                onValueChange = { transactionViewModel.updateAmount(it) },
                                textStyle = TextStyle(
                                    fontSize = 30.sp,
                                    fontWeight = FontWeight.Bold
                                ),
                                placeholder = {
                                    Text(
                                        "R$ 0,00",
                                        style = TextStyle(
                                            fontSize = 30.sp,
                                            fontWeight = FontWeight.SemiBold
                                        )
                                    )
                                },
                                maxLines = 1,
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                colors = TextFieldDefaults.colors(
                                    focusedContainerColor = Color.Transparent,
                                    unfocusedContainerColor = Color.Transparent,
                                    focusedIndicatorColor = MaterialTheme.colorScheme.tertiary,
                                    unfocusedIndicatorColor = MaterialTheme.colorScheme.surface,
                                    focusedLabelColor = MaterialTheme.colorScheme.primary,
                                    unfocusedLabelColor = MaterialTheme.colorScheme.onSurface,
                                    cursorColor = MaterialTheme.colorScheme.tertiary,
                                )
                            )
                        }
                    }

                    Row(
                        modifier = Modifier.padding(start = 4.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = transactionUiState.value.paymentType ==
                                        PaymentType.BALANCE,
                                onClick = {
                                    transactionViewModel.updatePaymentType(PaymentType.BALANCE)
                                },
                                colors = RadioButtonDefaults.colors(
                                    selectedColor = MaterialTheme.colorScheme.primary
                                )
                            )

                            Text(text = PaymentType.BALANCE.description)
                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(start = 20.dp)
                        ) {
                            RadioButton(
                                selected = transactionUiState.value.paymentType ==
                                        PaymentType.CREDIT_CARD,
                                onClick = {
                                    transactionViewModel.updatePaymentType(PaymentType.CREDIT_CARD)
                                },
                                colors = RadioButtonDefaults.colors(
                                    selectedColor = MaterialTheme.colorScheme.primary
                                )
                            )

                            Text(text = PaymentType.CREDIT_CARD.description)
                        }
                    }

                    if (transactionUiState.value.paymentType == PaymentType.CREDIT_CARD) {
                        Column {
                            TransactionTextField(
                                label = "Número do Cartão",
                                keyboardType = KeyboardType.Number,
                                value = transactionUiState.value.cardNumber,
                                onValueChange = {
                                    transactionViewModel.updateCardNumber(it)
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 16.dp, end = 16.dp, top = 16.dp)
                            )

                            TransactionTextField(
                                label = "Nome do Títular",
                                value = transactionUiState.value.holderName,
                                onValueChange = {
                                    transactionViewModel.updateHolderName(it)
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 16.dp, end = 16.dp, top = 16.dp)
                            )

                            Row {
                                TransactionTextField(
                                    label = "Vencimento",
                                    keyboardType = KeyboardType.Number,
                                    value = transactionUiState.value.expirationDate,
                                    onValueChange = {
                                        transactionViewModel.updateExpirationDate(it)
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth(0.5f)
                                        .padding(start = 16.dp, end = 8.dp, top = 16.dp)
                                )

                                TransactionTextField(
                                    label = "CVC",
                                    keyboardType = KeyboardType.Number,
                                    value = transactionUiState.value.securityCode,
                                    onValueChange = {
                                        transactionViewModel.updateSecurityCode(it)
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(start = 8.dp, end = 16.dp, top = 16.dp)
                                )
                            }
                        }
                    } else {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Saldo atual:",
                                style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp)
                            )

                            Text(
                                text = "R$ ${
                                    decimalFormat
                                        .format(transactionUiState.value.balance.balance)
                                }",
                                modifier = Modifier.padding(start = 16.dp),
                                style = TextStyle(
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 28.sp
                                )
                            )
                        }
                    }
                }

                Button(
                    onClick = { transactionViewModel.transfer(destinationUser) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp, start = 16.dp, end = 16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.tertiary,
                        contentColor = MaterialTheme.colorScheme.background
                    ),
                    shape = RoundedCornerShape(4.dp)
                ) {
                    Text(
                        text = "Transferir".uppercase(),
                        style = TextStyle(fontWeight = FontWeight.SemiBold)
                    )
                }
            }
        }
    } ?: navController.navigate(BottomNavScreen.Payment.route)
}

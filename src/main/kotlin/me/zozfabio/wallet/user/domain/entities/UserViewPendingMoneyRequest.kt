package me.zozfabio.wallet.user.domain.entities

import java.math.BigDecimal

data class UserViewPendingMoneyRequest(
    val transactionId: String,
    val fromUserName: String,
    val fromUserEmail: String,
    val value: BigDecimal
)

package me.zozfabio.wallet.user.domain.events

import java.math.BigDecimal
import java.time.Instant

data class MoneyRequestAccepted(override val transactionId: String,
                                val fromUserId: String,
                                val toUserId: String,
                                val value: BigDecimal,
                                override val occuredAt: Instant) : TransactionEvent

package me.zozfabio.wallet.user.domain.events

import java.math.BigDecimal
import java.time.Instant

data class MoneyAdded(override val transactionId: String,
                      val userId: String,
                      val userBalance: BigDecimal,
                      val value: BigDecimal,
                      override val occuredAt: Instant) : TransactionEvent

package me.zozfabio.wallet.transaction.domain.events

import java.math.BigDecimal
import java.time.Instant

data class ContactPaid(val value: BigDecimal,
                  override val occuredAt: Instant) : TransactionEvent

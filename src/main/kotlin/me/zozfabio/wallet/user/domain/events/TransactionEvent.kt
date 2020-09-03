package me.zozfabio.wallet.user.domain.events

import java.time.Instant

interface TransactionEvent {
    val transactionId: String
    val occuredAt: Instant
}

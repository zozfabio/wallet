package me.zozfabio.wallet.user.domain.commands

import java.time.Instant

interface TransactionCommand {
    val occuredAt: Instant
}

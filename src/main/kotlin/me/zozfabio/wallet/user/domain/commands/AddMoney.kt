package me.zozfabio.wallet.user.domain.commands

import java.math.BigDecimal
import java.time.Instant

class AddMoney(val value: BigDecimal) : TransactionCommand {
    override lateinit var occuredAt: Instant
    lateinit var userId: String
}

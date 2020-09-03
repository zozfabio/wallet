package me.zozfabio.wallet.user.domain.commands

import java.math.BigDecimal
import java.time.Instant

class AddMoney(override var occuredAt: Instant,
               var userId: String,
               val value: BigDecimal) : TransactionCommand

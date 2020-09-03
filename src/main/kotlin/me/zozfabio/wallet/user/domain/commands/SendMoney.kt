package me.zozfabio.wallet.user.domain.commands

import java.math.BigDecimal
import java.time.Instant

class SendMoney(override var occuredAt: Instant,
                var fromUserId: String,
                val toUserId: String,
                val value: BigDecimal) : TransactionCommand

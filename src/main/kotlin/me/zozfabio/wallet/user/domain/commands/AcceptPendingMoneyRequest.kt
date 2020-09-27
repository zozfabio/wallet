package me.zozfabio.wallet.user.domain.commands

import java.math.BigDecimal
import java.time.Instant

class AcceptPendingMoneyRequest(val fromUserId: String,
                                val toUserId: String,
                                val transactionId: String,
                                val value: BigDecimal,
                                override val occuredAt: Instant) : TransactionCommand

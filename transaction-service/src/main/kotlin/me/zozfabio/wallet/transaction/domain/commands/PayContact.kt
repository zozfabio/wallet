package me.zozfabio.wallet.transaction.domain.commands

import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.annotation.JsonTypeName
import me.zozfabio.wallet.transaction.domain.PAY_CONTACT_TYPE_NAME
import java.math.BigDecimal
import java.time.Instant

@JsonTypeName(PAY_CONTACT_TYPE_NAME)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, defaultImpl = PayContact::class)
data class PayContact(val userId: String,
                 val contactId: String,
                 val value: BigDecimal) : TransactionCommand {
    override lateinit var occuredAt: Instant
}

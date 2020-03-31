package me.zozfabio.wallet.transaction.domain.commands

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import me.zozfabio.wallet.transaction.domain.PAY_CONTACT_TYPE_NAME
import java.time.Instant

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME)
@JsonSubTypes(value = [
    JsonSubTypes.Type(name = PAY_CONTACT_TYPE_NAME, value = PayContact::class)
])
interface TransactionCommand {
    var occuredAt: Instant
}

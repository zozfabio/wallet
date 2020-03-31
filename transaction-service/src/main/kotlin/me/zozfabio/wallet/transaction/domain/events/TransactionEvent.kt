package me.zozfabio.wallet.transaction.domain.events

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import me.zozfabio.wallet.transaction.domain.CONTACT_PAID_TYPE_NAME
import java.time.Instant

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME)
@JsonSubTypes(value = [
    JsonSubTypes.Type(name = CONTACT_PAID_TYPE_NAME, value = ContactPaid::class)
])
interface TransactionEvent {
    val occuredAt: Instant
}

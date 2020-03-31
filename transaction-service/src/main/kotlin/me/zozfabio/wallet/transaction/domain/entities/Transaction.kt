package me.zozfabio.wallet.transaction.domain.entities

import me.zozfabio.wallet.transaction.domain.events.ContactPaid
import me.zozfabio.wallet.transaction.domain.vo.Operation
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.PersistenceConstructor
import org.springframework.data.annotation.TypeAlias
import org.springframework.data.domain.AbstractAggregateRoot
import org.springframework.data.mongodb.core.mapping.Document
import java.math.BigDecimal
import java.time.Instant
import java.util.UUID.randomUUID

@Document("Transaction")
@TypeAlias("Transaction")
data class Transaction (@Id val id: String) : AbstractAggregateRoot<Transaction>() {

    lateinit var operation: Operation

    lateinit var userId: String

    lateinit var contactId: String

    lateinit var value: BigDecimal

    @PersistenceConstructor
    constructor(id: String, operation: Operation, userId: String, contactId: String, value: BigDecimal): this(id) {
        this.operation = operation
        this.userId = userId
        this.contactId = contactId
        this.value = value
    }

    companion object Factory {
        fun pay(value: BigDecimal): Transaction {
            val id = randomUUID().toString()
            return Transaction(id)
                .contactPaid(ContactPaid(value, Instant.now()))
        }
    }

    private fun contactPaid(e: ContactPaid) = let {
        value = e.value
        andEvent(e)
    }
}

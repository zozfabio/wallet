package me.zozfabio.wallet.user.domain.entities

import org.springframework.data.annotation.Id
import org.springframework.data.annotation.PersistenceConstructor
import org.springframework.data.annotation.TypeAlias
import org.springframework.data.mongodb.core.mapping.Document
import java.math.BigDecimal
import java.math.BigDecimal.ZERO

@Document("UserView")
@TypeAlias("UserView")
data class UserView(@Id var id: String = "") {

    var name: String = ""

    var email: String = ""

    var balance: BigDecimal = ZERO

    val contacts: MutableSet<UserViewContact> = mutableSetOf()

    val pendingMoneyRequests: MutableSet<UserViewPendingMoneyRequest> = mutableSetOf()

    @PersistenceConstructor
    constructor(id: String,
                name: String?,
                email: String?,
                contacts: Iterable<UserViewContact>?,
                pendingMoneyRequests: Iterable<UserViewPendingMoneyRequest>?) : this(id) {
        name?.let { this.name = it }
        email?.let { this.email = it }
        contacts?.let { this.contacts.addAll(it) }
        pendingMoneyRequests?.let { this.pendingMoneyRequests.addAll(it) }
    }

    companion object Factory {
        fun create(id: String, name: String, email: String): UserView {
            return UserView(id, name, email, setOf(), setOf())
        }
    }

    fun update(name: String) {
        this.name = name
    }

    fun addContact(contact: UserViewContact): UserView {
        contacts.add(contact)
        return this
    }

    fun addMoney(value: BigDecimal): UserView {
        balance = balance.add(value)
        return this
    }

    fun removeMoney(value: BigDecimal): UserView {
        balance = balance.minus(value)
        return this
    }

    fun addPendingMoneyRequest(pendingMoneyRequest: UserViewPendingMoneyRequest): UserView {
        pendingMoneyRequests.add(pendingMoneyRequest)
        return this
    }

    fun removePendingMoneyRequest(transactionId: String): UserView {
        pendingMoneyRequests.removeIf { transactionId == it.transactionId }
        return this
    }
}

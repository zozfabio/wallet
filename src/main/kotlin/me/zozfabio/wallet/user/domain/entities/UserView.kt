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

    @PersistenceConstructor
    constructor(id: String,
                name: String,
                email: String,
                contacts: Iterable<UserViewContact>) : this(id) {
        this.name = name
        this.email = email
        this.contacts.addAll(contacts)
    }

    companion object Factory {
        fun create(id: String, name: String, email: String): UserView {
            return UserView(id, name, email, setOf());
        }
    }

    fun update(name: String) {
        this.name = name
    }

    fun addContact(contact: UserViewContact): UserView {
        contacts.add(contact)
        return this
    }

    fun addMoney(money: BigDecimal) {
        balance = balance.add(money)
    }
}

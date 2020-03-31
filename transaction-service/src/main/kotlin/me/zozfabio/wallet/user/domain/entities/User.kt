package me.zozfabio.wallet.user.domain.entities

import me.zozfabio.wallet.user.domain.events.*
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.PersistenceConstructor
import org.springframework.data.annotation.TypeAlias
import org.springframework.data.mongodb.core.mapping.Document
import java.util.Collections.emptyList

@Document("UserView")
@TypeAlias("UserView")
data class User(@Id val id: String) {

    lateinit var name: String

    lateinit var email: String

    private val contacts: MutableList<Contact> = mutableListOf()

    @PersistenceConstructor
    constructor(id: String,
                name: String,
                email: String,
                contacts: Iterable<Contact>? = emptyList()): this(id) {
        this.name = name
        this.email = email
        contacts?.let { this.contacts.addAll(it) }
    }

    companion object Factory {
        fun recreate(id: String, events: Iterable<UserEvent>) =
            events.fold(User(id), { acc, e -> acc.handle(e) })
    }

    fun getContacts() =
        contacts.toList()

    fun handle(e: UserEvent) = when (e) {
        is UserCreated -> created(e)
        is UserUpdated -> updated(e)
        is ContactAdded -> contactAdded(e)
        is ContactUpdated -> contactUpdated(e)
        is ContactRemoved -> contactRemoved(e)
        else -> this
    }

    private fun created(e: UserCreated) = apply {
        name = e.name
        email = e.email
    }

    private fun updated(e: UserUpdated) = apply {
        name = e.name
    }

    private fun contactAdded(e: ContactAdded) = apply {
        contacts.add(Contact(e.contactId, e.name, e.email))
    }

    private fun contactUpdated(e: ContactUpdated) = apply {
        contacts.replaceAll {
            if (it.id == e.contactId) it.apply { name = e.name } else it
        }
    }

    private fun contactRemoved(e: ContactRemoved) = apply {
        contacts.removeIf { it.id == e.contactId }
    }
}

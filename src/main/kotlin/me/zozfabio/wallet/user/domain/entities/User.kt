package me.zozfabio.wallet.user.domain.entities

import me.zozfabio.wallet.user.domain.events.*
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.PersistenceConstructor
import org.springframework.data.annotation.TypeAlias
import org.springframework.data.domain.AbstractAggregateRoot
import org.springframework.data.mongodb.core.mapping.Document
import java.time.Instant
import java.util.UUID.randomUUID

@Document("User")
@TypeAlias("User")
data class User(@Id val id: String) : AbstractAggregateRoot<User>() {

    lateinit var name: String

    lateinit var email: String

    private val myContacts: MutableSet<String> = mutableSetOf()

    private val isContacts: MutableSet<String> = mutableSetOf()

    @PersistenceConstructor
    constructor(id: String, name: String, email: String,
                myContacts: Iterable<String>,
                isContacts: Iterable<String>): this(id) {
        this.name = name
        this.email = email
        this.myContacts.addAll(myContacts)
        this.isContacts.addAll(isContacts)
    }

    companion object Factory {
        fun create(name: String, email: String): User {
            val id = randomUUID().toString()
            return User(id)
                .created(UserCreated(id, name, email, Instant.now()))
        }
    }

    private fun created(e: UserCreated) = let {
        name = e.name
        email = e.email
        andEvent(e)
    }

    fun update(name: String) =
        updated(UserUpdated(id, name, Instant.now()))

    private fun updated(e: UserUpdated) = let {
        it.name = e.name
        it.andEvent(e)
    }

    fun addContact(email: String, name: String) =
        contactAdded(ContactAdded(id, randomUUID().toString(), email, name, Instant.now()))

    private fun contactAdded(e: ContactAdded) = let {
        it.myContacts.add(e.contactId)
        it.andEvent(e)
    }

    fun updateContact(contactId: String, name: String) =
        contactUpdated(ContactUpdated(id, contactId, name, Instant.now()))

    private fun contactUpdated(e: ContactUpdated) =
        if (myContacts.contains(e.contactId)) this.andEvent(e)
        else this

    fun removeContact(contactId: String) =
        contactRemoved(ContactRemoved(id, contactId, Instant.now()))

    private fun contactRemoved(e: ContactRemoved) = let {
        it.myContacts.removeIf { c -> c == e.contactId }
        it.andEvent(e)
    }

    fun getMyContacts() =
        myContacts.toList()

    fun addIsContact(contact: UserContact) =
        if (isContacts.contains(contact.id)) this
        else contactUserCreated(ContactUserCreated(contact.userId, contact.id, id, Instant.now()))

    fun removeIsContact(contactId: String) = let {
        it.isContacts.removeIf { c -> c == contactId }
        it
    }

    private fun contactUserCreated(e: ContactUserCreated) = let {
        isContacts.add(e.contactId)
        it.andEvent(e)
    }

    fun getIsContacts() =
        isContacts.toList()
}

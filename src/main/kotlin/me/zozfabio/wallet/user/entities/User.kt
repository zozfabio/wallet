package me.zozfabio.wallet.user.entities

import me.zozfabio.wallet.user.events.*
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.TypeAlias
import org.springframework.data.domain.AbstractAggregateRoot
import org.springframework.data.mongodb.core.mapping.Document
import java.time.Instant

@Document
@TypeAlias("User")
data class User(@Id val id: ObjectId,
                var name: String,
                var email: String) : AbstractAggregateRoot<User>() {

    private var myContacts: MutableSet<ObjectId> = mutableSetOf()

    private var isContacts: MutableSet<ObjectId> = mutableSetOf()

    companion object Factory {
        fun create(name: String, email: String): User {
            val id = ObjectId.get()
            return User(id, name, email)
                .created(UserCreated(id, name, email, Instant.now()))
        }
    }

    private fun created(e: UserCreated) =
        andEvent(e)

    fun update(name: String) =
        updated(UserUpdated(id, name, Instant.now()))

    private fun updated(e: UserUpdated) = let {
        it.name = e.name
        it.andEvent(e)
    }

    fun addContact(email: String, name: String) =
        contactAdded(ContactAdded(ObjectId.get(), id, ObjectId.get(), email, name, Instant.now()))

    private fun contactAdded(e: ContactAdded) = let {
        it.myContacts.add(e.contactId)
        it.andEvent(e)
    }

    fun updateContact(contactId: ObjectId, name: String) =
        contactUpdated(ContactUpdated(ObjectId.get(), id, contactId, name, Instant.now()))

    private fun contactUpdated(e: ContactUpdated) =
        if (myContacts.contains(e.contactId)) this.andEvent(e)
        else this

    fun removeContact(contactId: ObjectId) =
        contactRemoved(ContactRemoved(ObjectId.get(), id, contactId, Instant.now()))

    private fun contactRemoved(e: ContactRemoved) = let {
        it.myContacts.removeIf { c -> c == e.contactId }
        it.andEvent(e)
    }

    fun getMyContacts() =
        myContacts.toList()

    fun addIsContact(contact: Contact) =
        if (isContacts.contains(contact.id)) this
        else contactUserDefined(ContactUserDefined(ObjectId.get(), contact.userId, contact.id, id, Instant.now()))

    fun addIsContacts(contacts: Iterable<Contact>) = let {
        contacts.forEach { c -> addIsContact(c) }
        it
    }

    fun removeIsContact(contactId: ObjectId) = let {
        it.isContacts.removeIf { c -> c == contactId }
        it
    }

    private fun contactUserDefined(e: ContactUserDefined) = let {
        isContacts.add(e.contactId)
        it.andEvent(e)
    }

    fun getIsContacts() =
        isContacts.toList()
}

package me.zozfabio.wallet.userview.entities

import me.zozfabio.wallet.user.events.*
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.TypeAlias
import org.springframework.data.mongodb.core.mapping.Document

@Document
@TypeAlias("UserView")
data class UserView(@Id val id: ObjectId,
                    var name: String? = null,
                    var email: String? = null,
                    var contacts: MutableList<ContactView> = mutableListOf()) {

    companion object Factory {
        fun recreate(id: ObjectId, events: Iterable<UserEvent>) =
            events.fold(UserView(id), { acc, e -> acc.handle(e) })
    }

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
        contacts.add(ContactView(e.contactId, e.name, e.email))
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

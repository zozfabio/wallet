package me.zozfabio.wallet.user.domain.services

import me.zozfabio.wallet.user.domain.commands.*
import me.zozfabio.wallet.user.domain.entities.UserContact
import me.zozfabio.wallet.user.domain.entities.User
import me.zozfabio.wallet.user.domain.events.*
import me.zozfabio.wallet.user.domain.repositories.ContactRepository
import me.zozfabio.wallet.user.domain.repositories.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation.REQUIRED
import org.springframework.transaction.annotation.Transactional
import org.springframework.transaction.event.TransactionalEventListener

@Service
class UserService(val users: UserRepository,
                  val contacts: ContactRepository) {

    @Transactional(propagation = REQUIRED)
    fun apply(cmd: CreateUser) =
        users.save(User.create(cmd.name, cmd.email))

    @Transactional(propagation = REQUIRED)
    fun apply(cmd: UpdateUser) =
        users.findById(cmd.id).ifPresent {
            it.update(cmd.name)
            users.save(it)
        }

    @Transactional(propagation = REQUIRED)
    fun apply(cmd: AddContact) =
        users.findById(cmd.userId).ifPresent {
            it.addContact(cmd.email, cmd.name)
            users.save(it)
        }

    @Transactional(propagation = REQUIRED)
    fun apply(cmd: UpdateContact) =
        users.findById(cmd.userId).ifPresent {
            it.updateContact(cmd.contactId, cmd.name)
            users.save(it)
        }

    @Transactional(propagation = REQUIRED)
    fun apply(cmd: RemoveContact) =
        users.findById(cmd.userId).ifPresent {
            it.removeContact(cmd.contactId)
            users.save(it)
        }

    private fun handle(e: UserCreated) {
        users.findById(e.userId).ifPresent {
            user ->
            contacts.findAllByEmail(user.email)
                .forEach { c -> user.addIsContact(c) }
            users.save(user)
        }
    }

    private fun handle(e: ContactAdded) =
        contacts.save(UserContact(e.contactId, e.userId, e.email, e.name)).let {
            contact ->
            users.findFirstByEmail(e.email).ifPresent {
                user ->
                user.addIsContact(contact)
                users.save(user)
            }
        }

    private fun handle(e: ContactUpdated) =
        contacts.findById(e.contactId).ifPresent {
            it.update(e.name)
            contacts.save(it)
        }

    private fun handle(e: ContactUserCreated) =
        contacts.findById(e.contactId).ifPresent {
            it.isUser(e.contactUserId)
            contacts.save(it)
        }

    private fun handle(e: ContactRemoved) {
        contacts.deleteById(e.contactId)
        users.findFirstByIsContactsContaining(e.contactId).ifPresent {
            it.removeIsContact(e.contactId)
            users.save(it)
        }
    }

    @TransactionalEventListener
    fun handle(e: UserEvent) {
        when (e) {
            is UserCreated -> handle(e)
            is ContactAdded -> handle(e)
            is ContactUpdated -> handle(e)
            is ContactUserCreated -> handle(e)
            is ContactRemoved -> handle(e)
        }
    }
}

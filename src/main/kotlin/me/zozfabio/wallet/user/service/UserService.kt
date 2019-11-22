package me.zozfabio.wallet.user.service

import me.zozfabio.wallet.user.commands.*
import me.zozfabio.wallet.user.entities.Contact
import me.zozfabio.wallet.user.entities.User
import me.zozfabio.wallet.user.events.*
import me.zozfabio.wallet.user.repository.ContactRepository
import me.zozfabio.wallet.user.repository.UserEventRepository
import me.zozfabio.wallet.user.repository.UserRepository
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation.REQUIRED
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Mono

@Service
class UserService(val events: UserEventRepository,
                  val users: UserRepository,
                  val contacts: ContactRepository) {

    @Transactional(propagation = REQUIRED)
    fun apply(cmd: CreateUser) =
        Mono.defer { Mono.just(User.create(cmd.name, cmd.email)) }
            .flatMap { users.save(it) }
            .then()

    @Transactional(propagation = REQUIRED)
    fun apply(cmd: UpdateUser) =
        users.findById(cmd.id)
            .map { it.update(cmd.name) }
            .flatMap { users.save(it) }
            .then()

    @Transactional(propagation = REQUIRED)
    fun apply(cmd: AddContact) =
        users.findById(cmd.userId)
            .map { it.addContact(cmd.email, cmd.name) }
            .flatMap { users.save(it) }
            .then()

    @Transactional(propagation = REQUIRED)
    fun apply(cmd: UpdateContact) =
        users.findById(cmd.userId)
            .map { it.updateContact(cmd.contactId, cmd.name) }
            .flatMap { users.save(it) }
            .then()

    @Transactional(propagation = REQUIRED)
    fun apply(cmd: RemoveContact) =
        users.findById(cmd.userId)
            .map { it.removeContact(cmd.contactId) }
            .flatMap { users.save(it) }
            .then()

    private fun handle(e: UserCreated) =
        Mono.zip(users.findById(e.userId), contacts.findAllByEmail(e.email).collectList())
            .map { it.t1.addIsContacts(it.t2) }
            .flatMap { users.save(it) }
            .then()

    private fun handle(e: ContactAdded) =
        users.findById(e.userId)
            .map { Contact(e.contactId, it.id, e.email, e.name) }
            .flatMap { contacts.save(it) }
            .flatMap { contact ->
                users.findFirstByEmail(contact.email)
                    .map { it.addIsContact(contact)}
                    .flatMap { users.save(it)}
            }
            .then()

    private fun handle(e: ContactUpdated) =
        contacts.findById(e.contactId)
            .map { it.update(e.name) }
            .flatMap { contacts.save(it) }
            .then()

    private fun handle(e: ContactUserDefined) =
        contacts.findById(e.contactId)
            .map { it.isUser(e.contactUserId) }
            .flatMap { contacts.save(it) }
            .then()

    private fun handle(e: ContactRemoved) =
        contacts.deleteById(e.contactId)
            .then(users.findFirstByIsContactsContaining(e.contactId)
                .map { it.removeIsContact(e.contactId) }
                .flatMap { users.save(it) })
            .then()

    @EventListener
    @Transactional(propagation = REQUIRED)
    fun handle(e: UserEvent) {
        events.save(e)
            .subscribe()
        when (e) {
            is UserCreated -> handle(e)
            is ContactAdded -> handle(e)
            is ContactUpdated -> handle(e)
            is ContactUserDefined -> handle(e)
            is ContactRemoved -> handle(e)
            else -> Mono.empty()
        }.subscribe()
    }
}

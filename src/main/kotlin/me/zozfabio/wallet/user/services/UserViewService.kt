package me.zozfabio.wallet.user.services

import me.zozfabio.wallet.user.domain.entities.UserView
import me.zozfabio.wallet.user.domain.entities.UserViewContact
import me.zozfabio.wallet.user.domain.entities.UserViewPendingMoneyRequest
import me.zozfabio.wallet.user.domain.events.*
import me.zozfabio.wallet.user.domain.exceptions.EntityNotFoundException
import me.zozfabio.wallet.user.domain.repositories.UserViewRepository
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation.MANDATORY
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class UserViewService(val users : UserViewRepository) {

    fun findAll(): Iterable<UserView> =
        users.findAll()

    fun findOne(id: String): Optional<UserView> =
        users.findById(id)

    private fun handle(e: UserCreated) {
        users.save(UserView.create(e.userId, e.name, e.email))
    }

    private fun handle(e: UserUpdated) {
        users.findById(e.userId)
            .ifPresent { it.update(e.name) }
    }

    private fun handle(e: ContactAdded) {
        UserViewContact(e.contactId, e.name, e.email)
            .let { contact -> users.findById(e.userId)
                .ifPresent { users.save(it.addContact(contact)) } }
    }

    @EventListener
    @Transactional(propagation = MANDATORY)
    fun handle(e: UserEvent) {
        when (e) {
            is UserCreated -> handle(e)
            is UserUpdated -> handle(e)
            is ContactAdded -> handle(e)
        }
    }

    private fun handle(e: MoneyAdded) =
        users.findById(e.userId)
            .map { it.addMoney(e.value) }
            .ifPresent { users.save(it) }

    private fun handle(e: MoneySent) =
        users.findById(e.fromUserId)
            .flatMap { from -> users.findById(e.toUserId)
                .map { to -> Pair(from.addMoney(e.value), to.removeMoney(e.value)) } }
            .ifPresent {
                users.save(it.first)
                users.save(it.second)
            }

    private fun handle(e: MoneyRequested) =
        users.findById(e.fromUserId)
            .flatMap { from -> users.findById(e.toUserId)
                .map { to -> Pair(from, to) } }
            .map {
                UserViewPendingMoneyRequest(e.transactionId, it.first.name, it.first.email, e.value)
                    .let { pmr -> it.second.addPendingMoneyRequest(pmr) }
            }
            .ifPresent { users.save(it) }

    private fun handle(e: MoneyRequestAccepted) {
        val fromUser = users.findById(e.fromUserId)
            .orElseThrow { EntityNotFoundException("User not found!") }
        val toUser = users.findById(e.toUserId)
            .orElseThrow { EntityNotFoundException("User not found!") }

        users.save(fromUser.addMoney(e.value))
        users.save(toUser.removeMoney(e.value)
            .removePendingMoneyRequest(e.transactionId))
    }

    @EventListener
    @Transactional(propagation = MANDATORY)
    fun handle(e: TransactionEvent) {
        when(e) {
            is MoneyAdded -> handle(e)
            is MoneySent -> handle(e)
            is MoneyRequested -> handle(e)
            is MoneyRequestAccepted -> handle(e)
        }
    }
}

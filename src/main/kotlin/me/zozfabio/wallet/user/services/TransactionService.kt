package me.zozfabio.wallet.user.services

import me.zozfabio.wallet.user.domain.commands.AcceptPendingMoneyRequest
import me.zozfabio.wallet.user.domain.commands.AddMoney
import me.zozfabio.wallet.user.domain.commands.RequestMoney
import me.zozfabio.wallet.user.domain.commands.SendMoney
import me.zozfabio.wallet.user.domain.entities.Transaction
import me.zozfabio.wallet.user.domain.entities.UserBalance
import me.zozfabio.wallet.user.domain.events.*
import me.zozfabio.wallet.user.domain.exceptions.EntityNotFoundException
import me.zozfabio.wallet.user.domain.repositories.TransactionRepository
import me.zozfabio.wallet.user.domain.repositories.UserBalanceRepository
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation.MANDATORY
import org.springframework.transaction.annotation.Propagation.REQUIRES_NEW
import org.springframework.transaction.annotation.Transactional

@Service
class TransactionService(val users: UserBalanceRepository,
                         val transactions: TransactionRepository) {

    @Transactional(propagation = REQUIRES_NEW)
    fun apply(cmd: AddMoney) =
        users.findById(cmd.userId)
            .map { Transaction.addMoney(it, cmd.value) }
            .ifPresent { transactions.save(it) }

    @Transactional(propagation = REQUIRES_NEW)
    fun apply(cmd: SendMoney) =
        users.findById(cmd.fromUserId)
            .flatMap { from -> users.findById(cmd.toUserId)
                .map { to -> Pair(from, to) } }
            .map { Transaction.sendMoney(it.first, it.second, cmd.value) }
            .ifPresent { transactions.save(it) }

    @Transactional(propagation = REQUIRES_NEW)
    fun apply(cmd: RequestMoney) =
        users.findById(cmd.fromUserId)
            .flatMap { from -> users.findById(cmd.toUserId)
                .map { to -> Pair(from, to) } }
            .map { Transaction.requestMoney(it.first, it.second, cmd.value) }
            .ifPresent { transactions.save(it) }

    @Transactional(propagation = REQUIRES_NEW)
    fun apply(cmd: AcceptPendingMoneyRequest) {
        val transaction = transactions.findById(cmd.transactionId)
            .orElseThrow { EntityNotFoundException("Transaction not found!") }
        val fromUser = users.findById(cmd.fromUserId)
            .orElseThrow { EntityNotFoundException("User not found!") }

        transactions.save(transaction.acceptMoneyRequest(fromUser))
    }

    private fun handle(e: MoneyAdded) =
        users.findById(e.userId)
            .map { it.plus(e.value) }
            .ifPresent { users.save(it) }

    private fun handle(e: MoneySent) =
        users.findById(e.fromUserId)
            .flatMap { from -> users.findById(e.toUserId)
                .map { to -> Pair(from.minus(e.value), to.plus(e.value)) } }
            .ifPresent {
                users.save(it.first)
                users.save(it.second)
            }

    private fun handle(e: MoneyRequestAccepted) {
        val fromUser = users.findById(e.fromUserId)
            .orElseThrow { EntityNotFoundException("User not found!") }
        val toUser = users.findById(e.toUserId)
            .orElseThrow { EntityNotFoundException("User not found!") }

        users.save(toUser.minus(e.value))
        users.save(fromUser.plus(e.value))
    }

    @EventListener
    @Transactional(propagation = MANDATORY)
    fun handle(e: TransactionEvent) {
        when(e) {
            is MoneyAdded -> handle(e)
            is MoneySent -> handle(e)
            is MoneyRequestAccepted -> handle(e)
        }
    }

    private fun handle(e: UserCreated) =
        UserBalance(e.userId)
            .let { users.save(it) }

    @EventListener
    @Transactional(propagation = MANDATORY)
    fun handle(e: UserEvent) {
        when(e) {
            is UserCreated -> handle(e)
        }
    }
}

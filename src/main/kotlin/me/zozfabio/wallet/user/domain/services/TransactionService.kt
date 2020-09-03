package me.zozfabio.wallet.user.domain.services

import me.zozfabio.wallet.user.domain.commands.AddMoney
import me.zozfabio.wallet.user.domain.commands.RequestMoney
import me.zozfabio.wallet.user.domain.commands.SendMoney
import me.zozfabio.wallet.user.domain.entities.Transaction
import me.zozfabio.wallet.user.domain.events.MoneyAdded
import me.zozfabio.wallet.user.domain.events.MoneyRequested
import me.zozfabio.wallet.user.domain.events.MoneySent
import me.zozfabio.wallet.user.domain.events.TransactionEvent
import me.zozfabio.wallet.user.domain.repositories.TransactionRepository
import me.zozfabio.wallet.user.domain.repositories.UserBalanceRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation.REQUIRED
import org.springframework.transaction.annotation.Transactional
import org.springframework.transaction.event.TransactionalEventListener

@Service
class TransactionService(val users: UserBalanceRepository,
                         val transactions: TransactionRepository) {

    @Transactional(propagation = REQUIRED)
    fun apply(cmd: AddMoney) =
        users.findById(cmd.userId)
            .map { Transaction.addMoney(it, cmd.value) }
            .ifPresent { transactions.save(it) }

    @Transactional(propagation = REQUIRED)
    fun apply(cmd: SendMoney) =
        users.findById(cmd.fromUserId)
            .flatMap { from -> users.findById(cmd.toUserId)
                .map { to -> Pair(from, to) } }
            .map { Transaction.sendMoney(it.first, it.second, cmd.value) }
            .ifPresent { transactions.save(it) }

    @Transactional(propagation = REQUIRED)
    fun apply(cmd: RequestMoney) =
        users.findById(cmd.fromUserId)
            .flatMap { from -> users.findById(cmd.toUserId)
                .map { to -> Pair(from, to) } }
            .map { Transaction.requestMoney(it.first, it.second, cmd.value) }
            .ifPresent { transactions.save(it) }

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

    private fun handle(e: MoneyRequested) =
        users.findById(e.toUserId)
            .map { it.addPendingMoneyRequestTransactionId(e.transactionId) }
            .ifPresent { users.save(it) }

    @TransactionalEventListener
    fun handle(e: TransactionEvent) {
        when(e) {
            is MoneyAdded -> handle(e)
            is MoneySent -> handle(e)
            is MoneyRequested -> handle(e)
        }
    }
}

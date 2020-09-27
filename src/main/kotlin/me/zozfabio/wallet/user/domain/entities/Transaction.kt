package me.zozfabio.wallet.user.domain.entities

import me.zozfabio.wallet.user.domain.events.MoneyAdded
import me.zozfabio.wallet.user.domain.events.MoneyRequestAccepted
import me.zozfabio.wallet.user.domain.events.MoneyRequested
import me.zozfabio.wallet.user.domain.events.MoneySent
import me.zozfabio.wallet.user.domain.exceptions.InsufficientFundsException
import me.zozfabio.wallet.user.domain.vo.TransactionAction
import me.zozfabio.wallet.user.domain.vo.TransactionStatus
import me.zozfabio.wallet.user.domain.vo.TransactionType
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.PersistenceConstructor
import org.springframework.data.annotation.TypeAlias
import org.springframework.data.domain.AbstractAggregateRoot
import org.springframework.data.mongodb.core.mapping.Document
import java.math.BigDecimal
import java.time.Instant
import java.util.UUID.randomUUID

@Document("Transaction")
@TypeAlias("Transaction")
data class Transaction(@Id var id: String = "") : AbstractAggregateRoot<Transaction>() {

    lateinit var fromUserId: String
    lateinit var toUserId: String
    lateinit var value: BigDecimal
    lateinit var type: TransactionType
    lateinit var status: TransactionStatus
    lateinit var action: TransactionAction

    @PersistenceConstructor
    constructor(id: String,
                fromUserId: String,
                toUserId: String,
                value: BigDecimal,
                type: TransactionType,
                status: TransactionStatus,
                action: TransactionAction) : this(id) {
        this.fromUserId = fromUserId
        this.toUserId = toUserId
        this.value = value
        this.type = type
        this.status = status
        this.action = action
    }

    companion object Factory {
        fun addMoney(to: UserBalance, value: BigDecimal): Transaction {
            val transaction = Transaction(randomUUID().toString())
            return transaction.moneyAdded(MoneyAdded(
                transaction.id,
                to.userId,
                to.userBalance,
                value,
                Instant.now()
            ))
        }

        fun sendMoney(from: UserBalance, to: UserBalance, value: BigDecimal): Transaction {
            if (value.compareTo(from.userBalance) == 1) {
                throw InsufficientFundsException("Insufficient Funds {}", from.userBalance)
            }
            val transaction = Transaction(randomUUID().toString())
            return transaction.moneySent(MoneySent(
                transaction.id,
                from.userId,
                to.userId,
                value,
                Instant.now()
            ))
        }

        fun requestMoney(from: UserBalance, to: UserBalance, value: BigDecimal): Transaction {
            val transaction = Transaction(randomUUID().toString())
            return transaction.moneyRequested(MoneyRequested(
                transaction.id,
                from.userId,
                to.userId,
                value,
                Instant.now()
            ))
        }
    }

    private fun moneyAdded(e: MoneyAdded): Transaction {
        id = e.transactionId
        toUserId = e.userId
        value = e.value
        type = TransactionType.CREDIT
        status = TransactionStatus.ACCEPTED
        action = TransactionAction.CHARGE
        return andEvent(e)
    }

    private fun moneySent(e: MoneySent): Transaction {
        id = e.transactionId
        fromUserId = e.fromUserId
        toUserId = e.toUserId
        value = e.value
        type = TransactionType.DEBIT
        status = TransactionStatus.ACCEPTED
        action = TransactionAction.PAYMENT
        return andEvent(e)
    }

    private fun moneyRequested(e: MoneyRequested): Transaction {
        fromUserId = e.fromUserId
        toUserId = e.toUserId
        value = e.value
        type = TransactionType.CREDIT
        status = TransactionStatus.PENDING
        action = TransactionAction.RECEIPT
        return andEvent(e)
    }

    fun acceptMoneyRequest(from: UserBalance): Transaction {
        if (value.compareTo(from.userBalance) == 1) {
            throw InsufficientFundsException("Insufficient Funds {}!", from.userBalance)
        }
        return moneyRequestAccepted(MoneyRequestAccepted(
            id,
            fromUserId,
            toUserId,
            value,
            Instant.now()
        ))
    }

    private fun moneyRequestAccepted(e: MoneyRequestAccepted): Transaction {
        status = TransactionStatus.ACCEPTED
        return andEvent(e)
    }
}

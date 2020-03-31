package me.zozfabio.wallet.transaction.service

import me.zozfabio.wallet.Channels
import me.zozfabio.wallet.transaction.domain.commands.PayContact
import me.zozfabio.wallet.transaction.domain.entities.Transaction
import me.zozfabio.wallet.transaction.domain.events.TransactionEvent
import me.zozfabio.wallet.transaction.domain.repositories.TransactionRepository
import org.springframework.messaging.support.MessageBuilder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation.REQUIRED
import org.springframework.transaction.annotation.Transactional
import org.springframework.transaction.event.TransactionalEventListener

@Service
class TransactionService(val transactions: TransactionRepository,
                         val channels: Channels) {

    @Transactional(propagation = REQUIRED)
    fun apply(payContact: PayContact) {
        transactions.save(Transaction.pay(payContact.value))
    }

    @TransactionalEventListener
    fun handle(e: TransactionEvent) {
        channels.output()
            .send(MessageBuilder.withPayload(e).build())
    }
}
